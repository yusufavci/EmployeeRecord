package co.yusufavci.employeerecord.controller;

import co.yusufavci.employeerecord.constants.SuccessMessages;
import co.yusufavci.employeerecord.dto.DateAndSalaryRequest;
import co.yusufavci.employeerecord.dto.DepartmentLocationUpdateDto;
import co.yusufavci.employeerecord.dto.EmployeeDto;
import co.yusufavci.employeerecord.dto.SuccessResponse;
import co.yusufavci.employeerecord.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by yusuf on 31.07.2021.
 */

@RestController
@RequestMapping("/rest/employees")
public class EmployeeController {

    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody EmployeeDto employeeDto) {
        return new ResponseEntity<>(employeeService.create(employeeDto), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> read(@PathVariable String id) {
        return new ResponseEntity<>(employeeService.read(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse> update(@PathVariable String id, @RequestBody EmployeeDto employeeDto) {
        employeeDto.setId(id);
        employeeService.update(employeeDto);
        return new ResponseEntity<>(new SuccessResponse(SuccessMessages.EMPLOYEE_UPDATE_MESSAGE), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse> delete(@PathVariable String id) {
        employeeService.delete(id);
        return new ResponseEntity<>(new SuccessResponse(SuccessMessages.EMPLOYEE_DELETE_MESSAGE), HttpStatus.OK);
    }

    @GetMapping("/getWinnerOfTheMonth")
    public ResponseEntity<EmployeeDto> getWinnerOfTheMonth() {
        return new ResponseEntity<>(employeeService.getWinnerOfTheMonth(), HttpStatus.OK);
    }

    @PostMapping("/updateDepartmentLocation")
    public ResponseEntity<SuccessResponse> updateDepartmentLocation(@RequestBody DepartmentLocationUpdateDto updateDto) {
        employeeService.updateDepartmentLocation(updateDto);
        return new ResponseEntity<>(new SuccessResponse(SuccessMessages.DEPARTMENT_LOCATION_UPDATE_MESSAGE), HttpStatus.OK);
    }

    @PostMapping("/listAllByDateAfterAndSalaryIsGreater")
    public ResponseEntity<List<EmployeeDto>> listAllByDateAfterAndSalaryIsGreater(@RequestBody DateAndSalaryRequest dateAndSalaryRequest) {
        return new ResponseEntity<>(employeeService.listAllByDateAfterAndSalaryIsGreater(dateAndSalaryRequest), HttpStatus.OK);
    }

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
}
