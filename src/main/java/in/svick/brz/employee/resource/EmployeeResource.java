package in.svick.brz.employee.resource;

import in.svick.brz.employee.model.Employee;
import in.svick.brz.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class EmployeeResource {

    @Autowired
    EmployeeService employeeService;

    @GetMapping(value = "/employees")
    public ResponseEntity<List<Employee>> getEmployees() {
        return new ResponseEntity<>(employeeService.getEmployees(), HttpStatus.OK);
    }

    @GetMapping(value = "/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(employeeService.getEmployee(id), HttpStatus.OK);
    }

    @PostMapping(value = "/employees")
    public ResponseEntity<Employee> addEmployee(@Valid @RequestBody Employee employee) {
        return new ResponseEntity<>(employeeService.saveEmployee(employee), HttpStatus.CREATED);
    }

    @PutMapping(value = "/employees/{id}")
    public ResponseEntity<Employee> updateEmployeeById(@PathVariable(name = "id") Long id,
                                                       @Valid @RequestBody Employee employee) {
        employee.setId(id);
        return new ResponseEntity<>(employeeService.updateEmployee(id, employee), HttpStatus.OK);
    }

    @DeleteMapping(value = "/employees")
    public ResponseEntity<HttpStatus> deleteEmployeeById(@RequestParam(name = "id") Long id) {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/employees/filter/department")
    public ResponseEntity<List<Employee>> getEmployeesByDepartment(@RequestParam(name = "department") String department) {
        return new ResponseEntity<>(employeeService.getEmployeesByDepartment(department), HttpStatus.OK);
    }

    @GetMapping(value = "/employees/filter/location")
    public ResponseEntity<List<Employee>> getEmployeesByLocation(@RequestParam(name = "location") String location) {
        return new ResponseEntity<>(employeeService.getEmployeesByLocation(location), HttpStatus.OK);
    }

    @GetMapping(value = "/employees/filter/name")
    public ResponseEntity<List<Employee>> getEmployeesByNames(@RequestParam(name = "firstName") String firstName,
                                                              @RequestParam(name = "lastName") String lastName) {
        return new ResponseEntity<>(employeeService.getEmployeesByFirstNameAndLastName(firstName, lastName), HttpStatus.OK);
    }

    @GetMapping(value = "/employees/filter/keyword")
    public ResponseEntity<List<Employee>> getEmployeesByKeyword(@RequestParam(name = "firstName") String firstName) {
        return new ResponseEntity<>(employeeService.getEmployeesByKeyword(firstName), HttpStatus.OK);
    }

    @GetMapping(value = "/employees/filter/condition")
    public ResponseEntity<List<Employee>> getEmployeesByCondition(@RequestParam(name = "firstName") String firstName,
                                                            @RequestParam(name = "lastName") String lastName,
                                                            @RequestParam(name = "age") int age,
                                                            @RequestParam(name = "location") String location) {
        return new ResponseEntity<>(employeeService.getEmployeesByCondition(firstName, lastName, age, location), HttpStatus.OK);
    }

    @DeleteMapping(value = "/employees/delete/{firstName}/{lastName}")
    public ResponseEntity<Integer> deleteEmployeesByNames(@PathVariable(name = "firstName") String firstName,
                                                         @PathVariable(name = "lastName") String lastName) {
        return new ResponseEntity<>(employeeService.deleteEmployeesByName(firstName, lastName), HttpStatus.OK);
    }
}
