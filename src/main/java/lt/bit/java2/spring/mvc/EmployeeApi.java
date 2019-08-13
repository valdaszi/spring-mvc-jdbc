package lt.bit.java2.spring.mvc;

import lt.bit.java2.spring.mvc.entities.Employee;
import lt.bit.java2.spring.mvc.entities.Title;
import lt.bit.java2.spring.mvc.services.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Iterator;
import java.util.Optional;

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

	@PostMapping
	ResponseEntity<Employee> create(@RequestBody Employee employee) {
		employee.getTitles().forEach(t -> t.setEmployee(employee));
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

	@PutMapping
	ResponseEntity<Employee> edit(@RequestBody Employee employee) {
		Optional<Employee> emp = employeeService.findById(employee.getEmpNo());
		if (!emp.isPresent()) {
			return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(employee);
		}
		Employee empOrg = emp.get();
		empOrg.setFirstName(employee.getFirstName());
		empOrg.setLastName(employee.getLastName());
		empOrg.setBirthDate(employee.getBirthDate());
		empOrg.setHireDate(employee.getHireDate());
		empOrg.setGender(employee.getGender());

		// 1) trinam
//		Iterator<Title> it = empOrg.getTitles().iterator();
//		while (it.hasNext()) {
//			Title title = it.next();
//
//			boolean exists = employee.getTitles().stream()
//					.anyMatch(t -> t.getTitle().equals(title.getTitle()) &&
//							t.getFromDate().equals(title.getFromDate()));
//
//			if (!exists) it.remove();
//		}
		empOrg.getTitles().removeIf(title ->
				employee.getTitles().stream()
					.noneMatch(t -> t.getTitle().equals(title.getTitle()) &&
							t.getFromDate().equals(title.getFromDate()))
		);

		// 2) modifikuojam
		empOrg.getTitles().forEach(title -> {
			employee.getTitles().stream()
					.filter(t1 -> t1.getFromDate().equals(title.getFromDate()) &&
							t1.getTitle().equals(title.getTitle()) &&
							!t1.getToDate().equals(title.getToDate()))
					.findAny()
					.ifPresent(t2 -> title.setToDate(t2.getToDate()));
		});

		// 3) kuriam
		employee.getTitles().stream()
				.filter(title ->
						empOrg.getTitles().stream()
								.noneMatch(t -> t.getFromDate().equals(title.getFromDate()) &&
										t.getTitle().equals(title.getTitle())))
				.forEach(title -> {
					title.setEmployee(empOrg);
					empOrg.getTitles().add(title);
				});

		return ResponseEntity.ok(employeeService.save(empOrg));
	}

}
