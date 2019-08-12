package lt.bit.java2.spring.mvc;

import lt.bit.java2.spring.mvc.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class MvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(MvcApplication.class, args);
	}

}

@Controller
@RequestMapping("/")
class IndexController {
	@GetMapping
	String index() {
		return "index";
	}
}

@Controller
@RequestMapping("/employee")
class EmployeeController {

	@Autowired
	EmployeeService employeeService;

	@GetMapping("/{id}")
	String getEmployee(@PathVariable int id, ModelMap map) {
		try {
			Employee employee = employeeService.getOne(id);
			map.addAttribute("employee", employee);
			return "employee";
		} catch (EntityNotFoundException e) {
			map.addAttribute("id", id);
			return "employee-error";
		}
	}

	@GetMapping
	String getEmployeesList(@RequestParam(required = false, defaultValue = "10") int pageSize,
							@RequestParam(required = false, defaultValue = "1") int pageNo,
							@RequestParam(required = false, defaultValue = "empNo") String orderBy,
							ModelMap map) {

		Page<Employee> result = employeeService.findAll(PageRequest.of(pageNo - 1, pageSize, Sort.by(orderBy)));
		map.addAttribute("result", result);
		map.addAttribute("orderBy", orderBy);
		return "employees-list";
	}
}

@RestController
@RequestMapping("/api/employee")
class EmployeeApi {

	final EmployeeService employeeService;

	public EmployeeApi(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@GetMapping("/{id}")
	ResponseEntity<Employee> getEmployee(@PathVariable int id) {
		Optional<Employee> employee = employeeService.findById(id);
		return employee.isPresent() ? ResponseEntity.ok(employee.get()) :
				ResponseEntity.notFound().build();
	}

	@PostMapping()
	ResponseEntity<Employee> create(@RequestBody Employee employee) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(employeeService.save(employee));
	}

	@DeleteMapping("/{id}")
	ResponseEntity delete(@PathVariable int id) {
		Optional<Employee> employee = employeeService.findById(id);
		if (employee.isPresent()) {
			employeeService.delete(employee.get());
			return ResponseEntity.status(HttpStatus.OK).build();
		}
		return ResponseEntity.notFound().build();
	}

}

