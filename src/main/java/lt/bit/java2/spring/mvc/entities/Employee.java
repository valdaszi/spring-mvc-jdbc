package lt.bit.java2.spring.mvc.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "employees")
@Data
public class Employee {

    @Id
    @Column(name = "emp_no")
    private Integer empNo;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum('M','F')")
    private Gender gender;

    @Column(name = "hire_date")
    private LocalDate hireDate;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @OneToMany(mappedBy = "employee",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Title> titles;

}
