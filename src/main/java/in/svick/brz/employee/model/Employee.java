package in.svick.brz.employee.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "first_name")
    @NotBlank(message = "firstName cannot be Null or Blank")
    private String firstName;

    @Column(name = "last_name")
    @NotBlank(message = "lastName cannot be Null or Blank")
    private String lastName;

    @Column(name = "age")
    private int age = 0;

    @Column(name = "location")
    @NotBlank(message = "location cannot be Null or Blank")
    private String location;

    @Email(message = "Please enter valid email address")
    @Column(name = "email")
    private String email;

    @Column(name = "department")
    @NotBlank(message = "department cannot be Null or Blank")
    private String department;

    @CreationTimestamp
    @Column(name = "createdTimestamp", nullable = false, updatable = false)
    private Date createdTimestamp;

    @UpdateTimestamp
    @Column(name = "lastUpdatedTimestamp")
    private Date lastUpdatedTimestamp;
}
