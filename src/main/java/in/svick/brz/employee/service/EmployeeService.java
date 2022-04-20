package in.svick.brz.employee.service;

import in.svick.brz.employee.model.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> getEmployees();

    Employee saveEmployee(Employee e);

    Employee getEmployee(Long id);

    void deleteEmployee(Long id);

    Employee updateEmployee(Employee e);

    List<Employee> getEmployeesByDepartment(String department);

    List<Employee> getEmployeesByLocation(String location);

    List<Employee> getEmployeesByFirstNameAndLastName(String firstName, String lastName);

    List<Employee> getEmployeesByKeyword(String firstName);

    Employee getEmployeesByCondition(String firstName, String lastName, int age, String location);

    Integer deleteEmployeesByName(String firstName, String lastName);
}
