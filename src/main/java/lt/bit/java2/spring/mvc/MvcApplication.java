package lt.bit.java2.spring.mvc;

import lt.bit.java2.spring.mvc.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
		Employee employee = employeeService.getEmployeeById(id);
		map.addAttribute("employee", employee);
		return "employee";
	}

	@GetMapping
	String getEmployeesList(@RequestParam(required = false, defaultValue = "10") int pageSize,
							@RequestParam(required = false, defaultValue = "1") int pageNo,
							ModelMap map) {

		PageResult<Employee> result = employeeService.list(pageSize, pageNo);
		map.addAttribute("result", result);

		return "employees-list";
	}
}

