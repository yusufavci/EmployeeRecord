package co.yusufavci.employeerecord;

import co.yusufavci.employeerecord.constants.SuccessMessages;
import co.yusufavci.employeerecord.domain.Department;
import co.yusufavci.employeerecord.domain.Location;
import co.yusufavci.employeerecord.dto.DateAndSalaryRequest;
import co.yusufavci.employeerecord.dto.DepartmentLocationUpdateDto;
import co.yusufavci.employeerecord.dto.EmployeeDto;
import co.yusufavci.employeerecord.dto.SuccessResponse;
import co.yusufavci.employeerecord.repository.DepartmentRepository;
import co.yusufavci.employeerecord.repository.LocationRepository;
import co.yusufavci.employeerecord.service.EmployeeService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by yusuf on 1.08.2021.
 */
@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@SpringBootTest(classes = EmployeeRecordApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeServiceIntegrationTest {

    private TestRestTemplate testRestTemplate;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private EmployeeService employeeService;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @Test
    public void testCreate() {
        ResponseEntity<String> responseEntity = createAndGetIdOfEmployee();
        Assertions.assertEquals(200, responseEntity.getStatusCode().value());
    }

    private ResponseEntity<String> createAndGetIdOfEmployee() {
        Location location = createLocation("Ankara, Turkey");
        Department department = createDepartment(location, "IT Department");

        return createEmployee(department, "Yusuf", "Avci", 30000.0, LocalDate.now());
    }

    @Test
    public void testRead() {
        ResponseEntity<String> id = createAndGetIdOfEmployee();
        ResponseEntity<EmployeeDto> responseEntity = this.testRestTemplate.getForEntity(getRootUrl() + "/rest/employees/" + id.getBody(), EmployeeDto.class);

        Assertions.assertEquals(200, responseEntity.getStatusCode().value());
        Assertions.assertEquals("Yusuf", responseEntity.getBody().getFirstName());
    }

    @Test
    public void testUpdate() {
        ResponseEntity<String> id = createAndGetIdOfEmployee();
        ResponseEntity<EmployeeDto> responseEntity = this.testRestTemplate.getForEntity(getRootUrl() + "/rest/employees/" + id.getBody(), EmployeeDto.class);
        EmployeeDto updated = responseEntity.getBody();
        updated.setPhone("9999");
        HttpEntity<EmployeeDto> request = new HttpEntity<>(updated);
        this.testRestTemplate.put(getRootUrl() + "/rest/employees/" + updated.getId(), request, SuccessResponse.class);
        ResponseEntity<EmployeeDto> updatedResponse = this.testRestTemplate.getForEntity(getRootUrl() + "/rest/employees/" + id.getBody(), EmployeeDto.class);

        Assertions.assertEquals("9999", updatedResponse.getBody().getPhone());
    }

    @Test
    public void testDelete() {
        ResponseEntity<String> id = createAndGetIdOfEmployee();

        ResponseEntity<SuccessResponse> successResponseResponseEntity =
                this.testRestTemplate.exchange(getRootUrl() + "/rest/employees/" + id.getBody(), HttpMethod.DELETE, null, SuccessResponse.class);

        Assertions.assertEquals(SuccessMessages.EMPLOYEE_DELETE_MESSAGE, successResponseResponseEntity.getBody().getMessage());
    }

    @Test
    public void testChangeDepartmentLocation() {
        Location location = createLocation("Ankara, Turkey");
        Location location2 = createLocation("London, United Kingdom");
        Department department = createDepartment(location, "IT Department");

        ResponseEntity<String> id = createEmployee(department, "Yusuf", "Avci", 30000.0, LocalDate.now());

        DepartmentLocationUpdateDto updateDto = new DepartmentLocationUpdateDto();
        updateDto.setDepartmentId(department.getId());
        updateDto.setNewLocationId(location2.getId());

        ResponseEntity<SuccessResponse> successResponseResponseEntity =
                this.testRestTemplate.postForEntity(getRootUrl() + "/rest/employees/updateDepartmentLocation", updateDto, SuccessResponse.class);

        Assertions.assertEquals(SuccessMessages.DEPARTMENT_LOCATION_UPDATE_MESSAGE, successResponseResponseEntity.getBody().getMessage());
    }

    @Test
    public void testListAllByDateAfterAndSalaryIsGreater() {
        ResponseEntity<String> id3 = createMultipleEmployeeAndGetLastOnesId();

        ResponseEntity<EmployeeDto> responseEntity = this.testRestTemplate.getForEntity(getRootUrl() + "/rest/employees/" + id3.getBody(), EmployeeDto.class);

        DateAndSalaryRequest request = new DateAndSalaryRequest();
        request.setSalary(20000.0);
        request.setFromDate(responseEntity.getBody().getEmploymentStartDate());

        HttpEntity<DateAndSalaryRequest> requestHttpEntity = new HttpEntity<>(request);
        ResponseEntity<List<EmployeeDto>> response =
                this.testRestTemplate.exchange(getRootUrl() + "/rest/employees/listAllByDateAfterAndSalaryIsGreater",
                        HttpMethod.POST,
                        requestHttpEntity,
                        new ParameterizedTypeReference<List<EmployeeDto>>() {
                        });

        Assertions.assertEquals(1, response.getBody().size());
        Assertions.assertEquals("Jackie", response.getBody().get(0).getFirstName());
    }

    @Test
    public void testDrawAndGetWinnerOfTheMonth() {
        ResponseEntity<String> id = createMultipleEmployeeAndGetLastOnesId();
        employeeService.drawWinnerOfTheMonth();

        ResponseEntity<EmployeeDto> responseEntity = this.testRestTemplate.getForEntity(getRootUrl() + "/rest/employees/getWinnerOfTheMonth", EmployeeDto.class);

        Assertions.assertEquals(200, responseEntity.getStatusCode().value());
        Assertions.assertNotEquals(null, responseEntity.getBody().getId());

    }

    private ResponseEntity<String> createMultipleEmployeeAndGetLastOnesId() {
        Location location = createLocation("Ankara, Turkey");
        Department department = createDepartment(location, "IT Department");

        ResponseEntity<String> id = createEmployee(department, "Yusuf", "Avci", 30000.0, LocalDate.now());
        ResponseEntity<String> id2 = createEmployee(department, "John", "Doe", 40000.0, LocalDate.now().minusDays(1));
        ResponseEntity<String> id3 = createEmployee(department, "Jackie", "Chan", 99999.0, LocalDate.now().plusDays(2));
        return id3;
    }


    private ResponseEntity<String> createEmployee(Department department, String firstName, String lastName, Double salary, LocalDate employmentStartDate) {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName(firstName);
        employeeDto.setLastName(lastName);
        employeeDto.setEmploymentStartDate(employmentStartDate);
        employeeDto.setSalary(salary);
        employeeDto.setAddress("Ankara, Turkey");
        employeeDto.setPhone("+905554443322");
        employeeDto.setDepartmentId(department.getId());
        return this.testRestTemplate
                .postForEntity(getRootUrl() + "/rest/employees", employeeDto, String.class);
    }


    private Department createDepartment(Location location, String name) {
        Department department = new Department();
        department.setLocation(location);
        department.setName(name);
        department = departmentRepository.save(department);
        return department;
    }

    private Location createLocation(String address) {
        Location location = new Location();
        location.setAddress(address);
        location = locationRepository.save(location);
        return location;
    }


    @Autowired
    public void setTestRestTemplate(TestRestTemplate testRestTemplate) {
        this.testRestTemplate = testRestTemplate;
    }
}
