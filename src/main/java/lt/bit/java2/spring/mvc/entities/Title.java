package lt.bit.java2.spring.mvc.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "titles")
@IdClass(TitlePK.class)
@Data
public class Title {

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "emp_no", referencedColumnName = "emp_no")
    @JsonIgnore
    @ToString.Exclude
    private Employee employee;

    @Id
    private String title;

    @Id
    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "to_date")
    private LocalDate toDate;


}
