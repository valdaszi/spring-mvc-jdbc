package lt.bit.java2.spring.mvc.services;

import lt.bit.java2.spring.mvc.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeService extends JpaRepository<Employee, Integer> {

//    @Query("SELECT .... ")
//    List<Employee> find(String firstName);

}

