package lt.bit.java2.spring.mvc.services;

import lt.bit.java2.spring.mvc.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeService extends JpaRepository<Employee, Integer> {

//    @Query("SELECT .... ")
//    List<Employee> find(String firstName);

}

