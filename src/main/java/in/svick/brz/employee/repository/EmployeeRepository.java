package in.svick.brz.employee.repository;

import in.svick.brz.employee.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByDepartment(String department);

    List<Employee> findByLocation(String location);

    List<Employee> findByFirstNameAndLastName(String firstName, String lastName);

    List<Employee> findByFirstNameContaining(String firstName);

    @Query("FROM Employee WHERE firstName = :firstName AND lastName = :lastName AND age > :age AND location = :location")
    List<Employee> getEmployeesByCondition(String firstName, String lastName, int age, String location);

    @Transactional
    @Modifying
    @Query("DELETE FROM Employee WHERE firstName = :firstName AND lastName = :lastName")
    Integer deleteEmployeesByName(String firstName, String lastName);

}
