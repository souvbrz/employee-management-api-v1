package in.svick.brz.employee.service;

import in.svick.brz.employee.exception.EmployeeNotFoundException;
import in.svick.brz.employee.model.Employee;
import in.svick.brz.employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee saveEmployee(Employee e) {
        return employeeRepository.save(e);
    }

    @Override
    public Employee getEmployee(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            return employee.get();
        }
        throw new EmployeeNotFoundException("Employee not found for id : " + id);
    }

    @Override
    public void deleteEmployee(Long id) {
        if (employeeRepository.findById(id).isPresent()) {
            employeeRepository.deleteById(id);
        }
        throw new EmployeeNotFoundException("Employee not found for id : " + id);
    }

    @Override
    public Employee updateEmployee(Employee e) {
        return employeeRepository.save(e);
    }

    @Override
    public List<Employee> getEmployeesByDepartment(String department) {
        return employeeRepository.findByDepartment(department);
    }

    @Override
    public List<Employee> getEmployeesByLocation(String location) {
        return employeeRepository.findByLocation(location);
    }

    @Override
    public List<Employee> getEmployeesByFirstNameAndLastName(String firstName, String lastName) {
        return employeeRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    @Override
    public List<Employee> getEmployeesByKeyword(String firstName) {
        return employeeRepository.findByFirstNameContaining(firstName);
    }

    @Override
    public Employee getEmployeesByCondition(String firstName, String lastName, int age, String location) {
        return employeeRepository.getEmployeesByCondition(firstName, lastName, age, location);
    }

    @Override
    public Integer deleteEmployeesByName(String firstName, String lastName) {
        return employeeRepository.deleteEmployeesByName(firstName, lastName);
    }
}
