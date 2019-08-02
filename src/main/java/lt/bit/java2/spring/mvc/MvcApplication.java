package lt.bit.java2.spring.mvc;

import lt.bit.java2.spring.mvc.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;

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
							ModelMap map) {

		Page<Employee> result = employeeService.findAll(PageRequest.of(pageNo - 1, pageSize));
		map.addAttribute("result", result);

		return "employees-list";
	}
}

