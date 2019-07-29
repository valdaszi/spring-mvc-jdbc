package lt.bit.java2.spring.mvc.services;

import lt.bit.java2.spring.mvc.Employee;
import lt.bit.java2.spring.mvc.Gender;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class EmployeeRowMapper implements RowMapper<Employee> {
    @Override
    public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
        Employee e = new Employee();
        e.setEmpNo(rs.getInt("emp_no"));
        e.setFirstName(rs.getString("first_name"));
        e.setLastName(rs.getString("last_name"));
        e.setBirthDate(rs.getDate("birth_date").toLocalDate());
        e.setHireDate(rs.getDate("hire_date").toLocalDate());
        e.setGender(Gender.valueOf(rs.getString("gender")));
        return e;    }
}
