package in.svick.brz.employee.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.svick.brz.employee.model.Employee;
import in.svick.brz.employee.repository.EmployeeRepository;
import in.svick.brz.employee.service.EmployeeService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = EmployeeResource.class)
@AutoConfigureMockMvc
public class EmployeeResourceTest {

    @MockBean
    EmployeeService employeeService;

    @MockBean
    EmployeeRepository employeeRepository;

    @Autowired
    private MockMvc mockMvc;

    private static List<Employee> employeeList = new ArrayList<>();

    @BeforeAll
    static void setUp(){
        Employee employee1 = new Employee(1L, "First", "Surname", 25,
                "India", "employee1@email.com", "IT", new Date(), new Date());

        Employee employee2 = new Employee(2L, "Second", "Surname", 30,
                "Denmark", "employee2@email.com", "Admin", new Date(), new Date());

        employeeList.add(employee1);
        employeeList.add(employee2);
    }

    @AfterAll
    static void tearDown(){
        employeeList.clear();
    }

    @Test
    @DisplayName("Should return a List All Employees When making GET request to endpoint - /api/v1/employees")
    void getEmployeesTest() throws Exception {

        Mockito.when(employeeService.getEmployees()).thenReturn(employeeList);
        Mockito.when(employeeRepository.findAll()).thenReturn(employeeList);

        this.mockMvc.perform(get("/employees").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].firstName").value("First"))
                .andExpect(jsonPath("$[0].lastName").value("Surname"))
                .andExpect(jsonPath("$[0].age").value(25))
                .andExpect(jsonPath("$[0].location").value("India"))
                .andExpect(jsonPath("$[0].email").value("employee1@email.com"))
                .andExpect(jsonPath("$[0].department").value("IT"));
    }

    @Test
    @DisplayName("Should return an Employee When making GET request to endpoint - /api/v1/employees/2")
    void getEmployeeByIdTest() throws Exception {

        Mockito.when(employeeRepository.findById(any())).thenReturn(Optional.ofNullable(employeeList.get(1)));
        Mockito.when(employeeService.getEmployee(any())).thenReturn(employeeList.get(1));


        this.mockMvc.perform(get("/employees/"+employeeList.get(1).getId()).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.firstName").value("Second"))
                .andExpect(jsonPath("$.lastName").value("Surname"))
                .andExpect(jsonPath("$.age").value(30))
                .andExpect(jsonPath("$.location").value("Denmark"))
                .andExpect(jsonPath("$.email").value("employee2@email.com"))
                .andExpect(jsonPath("$.department").value("Admin"));
    }

    @Test
    @DisplayName("Should Add an Employee When making POST request to endpoint - /api/v1/employees/ with JSON Payload")
    void addEmployeeTest() throws Exception {

        String jsonString = new ObjectMapper().writeValueAsString(employeeList.get(0));

        Mockito.when(employeeService.saveEmployee(any())).thenReturn(employeeList.get(0));
        Mockito.when(employeeRepository.save(any())).thenReturn(employeeList.get(0));

        this.mockMvc.perform(post("/employees").contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("First"))
                .andExpect(jsonPath("$.lastName").value("Surname"))
                .andExpect(jsonPath("$.age").value(25))
                .andExpect(jsonPath("$.location").value("India"))
                .andExpect(jsonPath("$.email").value("employee1@email.com"))
                .andExpect(jsonPath("$.department").value("IT"));

    }

    @Test
    @DisplayName("Should update an Employee When making PUT request to endpoint - /api/v1/employees/1 with JSON payload")
    void updateEmployeeById() throws Exception {

        Employee e = employeeList.get(0);
        e.setLocation("Norway");
        e.setLastUpdatedTimestamp(new Date());

        String jsonString = new ObjectMapper().writeValueAsString(e);

        Mockito.when(employeeService.saveEmployee(any())).thenReturn(e);
        Mockito.when(employeeRepository.findById(any())).thenReturn(Optional.of(e));
        Mockito.when(employeeRepository.save(any())).thenReturn(e);

        this.mockMvc.perform(put("/employees/"+e.getId()).contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should delete an Employee When making DELETE request to endpoint - /api/v1/employees?id=1")
    void deleteEmployeeByIdTest() throws Exception {

        Mockito.when(employeeRepository.findById(any())).thenReturn(Optional.ofNullable(employeeList.get(0)));
        Mockito.doNothing().when(employeeRepository).deleteById(employeeList.get(0).getId());

        this.mockMvc.perform(delete("/employees/").param("id", employeeList.get(0).getId().toString()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Should return List Employees When making GET request to endpoint - /api/v1/employees/filter/department?department=IT")
    void getEmployeesByDepartmentTest() throws Exception{

        Mockito.when(employeeService.getEmployeesByDepartment(any())).thenReturn(employeeList);
        Mockito.when(employeeRepository.findByDepartment(any())).thenReturn(employeeList);

        this.mockMvc.perform(get("/employees/filter/department").param("department", "IT"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].department").value("IT"));
    }

    @Test
    @DisplayName("Should List Employees When making GET request to endpoint - /api/v1/employees/filter/location?location=1")
    void getEmployeesByLocationTest() throws Exception {

        Mockito.when(employeeService.getEmployeesByLocation(any())).thenReturn(employeeList);
        Mockito.when(employeeRepository.findByLocation(any())).thenReturn(employeeList);

        this.mockMvc.perform(get("/employees/filter/location").param("location", "India"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].location").value("India"));
    }

    @Test
    @DisplayName("Should return List Employees When making GET request to endpoint - /api/v1/employees/filter/name?firstName=First&lastName=Surname")
    void getEmployeesByNamesTest() throws Exception {

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("firstName", "First");
        map.add("lastName", "Surname");

        Mockito.when(employeeService.getEmployeesByFirstNameAndLastName(any(), any())).thenReturn(employeeList);
        Mockito.when(employeeRepository.findByFirstNameAndLastName(any(), any())).thenReturn(employeeList);

        this.mockMvc.perform(get("/employees/filter/name").params(map))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].firstName").value("First"))
                .andExpect(jsonPath("$[0].lastName").value("Surname"));
    }

    @Test
    @DisplayName("Should return a blank list When making GET request to endpoint - /api/v1/employees/filter/keyword?firstName=Sou")
    void getEmployeesByKeywordTest() throws Exception {

        Mockito.when(employeeService.getEmployeesByKeyword(any())).thenReturn(new ArrayList<>());
        Mockito.when(employeeRepository.findByFirstNameContaining(any())).thenReturn(new ArrayList<>());

        this.mockMvc.perform(get("/employees/filter/keyword").param("firstName", "Sou"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[]"));
    }

    @Test
    @DisplayName("Should return a blank list When making GET request to endpoint - /api/v1/employees/filter/condition?firstName=Test&lastName=Name&age=40&location=Norway")
    void getEmployeesByConditionTest() throws Exception {

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("firstName", "First");
        map.add("lastName", "Surname");
        map.add("age", "40");
        map.add("location", "Norway");

        Mockito.when(employeeService.getEmployeesByCondition(any(), any(), anyInt(), any())).thenReturn(new ArrayList<>());
        Mockito.when(employeeRepository.getEmployeesByCondition(any(), any(), anyInt(), any())).thenReturn(new ArrayList<>());

        this.mockMvc.perform(get("/employees/filter/condition").params(map))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[]"));
    }

    @Test
    @DisplayName("Should return the number of Employees deleted When making Delete request to endpoint - /api/v1/employees/delete/First/Surname")
    void deleteEmployeesByNamesTest() throws Exception {
        Mockito.when(employeeService.deleteEmployeesByName(any(), any())).thenReturn(1);
        Mockito.when(employeeRepository.deleteEmployeesByName(any(), any())).thenReturn(1);

        this.mockMvc.perform(delete("/employees/delete/"+employeeList.get(0).getFirstName()+"/"+employeeList.get(0).getLastName()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("1"));
    }
}