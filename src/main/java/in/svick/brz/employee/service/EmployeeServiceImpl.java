package in.svick.brz.employee.service;

import in.svick.brz.employee.exception.EmployeeNotFoundException;
import in.svick.brz.employee.model.Employee;
import in.svick.brz.employee.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getEmployees() {
        if(!employeeRepository.findAll().isEmpty()){
            return employeeRepository.findAll();
        }
        throw new EmployeeNotFoundException("No Employee Records Found");
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
        }else{
            throw new EmployeeNotFoundException("No Employee records found for id : " + id);
        }
    }

    @Override
    public Employee updateEmployee(Long id, Employee e) {
        if(employeeRepository.findById(id).isPresent()){
            e.setId(id);
            return employeeRepository.save(e);
        }
        throw new EmployeeNotFoundException("No Employee records found for id : " + id);
    }

    @Override
    public List<Employee> getEmployeesByDepartment(String department) {
        if(!employeeRepository.findByDepartment(department).isEmpty()){
            return employeeRepository.findByDepartment(department);
        }
        throw new EmployeeNotFoundException("No Employee records found for department : "+department);
    }

    @Override
    public List<Employee> getEmployeesByLocation(String location) {
        if(!employeeRepository.findByLocation(location).isEmpty()){
            return employeeRepository.findByLocation(location);
        }
        throw new EmployeeNotFoundException("No Employee records found for location : "+location);
    }

    @Override
    public List<Employee> getEmployeesByFirstNameAndLastName(String firstName, String lastName) {
        if(!employeeRepository.findByFirstNameAndLastName(firstName, lastName).isEmpty()){
            return employeeRepository.findByFirstNameAndLastName(firstName, lastName);
        }
        throw new EmployeeNotFoundException(String.format("No Employee records found for name : %s %s", firstName, lastName));
    }

    @Override
    public List<Employee> getEmployeesByCondition(String firstName, String lastName, int age, String location) {
        if(!employeeRepository.getEmployeesByCondition(firstName, lastName, age, location).isEmpty()){
            return employeeRepository.getEmployeesByCondition(firstName, lastName, age, location);
        }
        throw new EmployeeNotFoundException(String.format("No Employee records found for name : %s %s with age > %d and location = %s", firstName, lastName, age, location));
    }

    @Override
    public Integer deleteEmployeesByName(String firstName, String lastName) {

        int deletedEmpNo = employeeRepository.deleteEmployeesByName(firstName, lastName).intValue();
        if(deletedEmpNo > 0){
            return deletedEmpNo;
        }
        throw new EmployeeNotFoundException(String.format("No Employee records found for name : %s %s", firstName, lastName));
    }
}
