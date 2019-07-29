package lt.bit.java2.spring.mvc.services;

import lt.bit.java2.spring.mvc.Employee;
import lt.bit.java2.spring.mvc.Gender;
import lt.bit.java2.spring.mvc.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    EmployeeRowMapper employeeRowMapper;

    public Employee getEmployeeById(int id) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM employees WHERE emp_no = ?",
                employeeRowMapper,
                id);
    }

    /**
     * Grazina viena puslapi
     * @param pageSize puslapio dydis
     * @param pageNo puslapio numeris (1..)
     * @return
     */
    public PageResult<Employee> list(int pageSize, int pageNo) {
        if (pageNo < 1) pageNo = 1;
        if (pageSize < 1) pageSize = 1;
        else if (pageSize > 100) pageSize = 100;
        List<Employee> employees = jdbcTemplate.query("SELECT * FROM employees LIMIT ? OFFSET ?", employeeRowMapper, pageSize, (pageNo - 1) * pageSize);
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM employees", Integer.class);
        int recordCount = count == null ? 0 : count;

        PageResult<Employee> result = new PageResult<>();
        result.setPageNo(pageNo);
        result.setPageSize(pageSize);
        result.setRecordsCount(recordCount);
        result.setData(employees);
        result.setPagesCount(recordCount / pageSize + (recordCount % pageSize == 0 ? 0 : 1));
        result.setFirst(pageNo == 1);
        result.setLast(pageNo == result.getPagesCount());
        return  result;
    }
}
