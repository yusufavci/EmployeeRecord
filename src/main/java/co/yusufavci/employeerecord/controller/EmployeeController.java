package co.yusufavci.employeerecord.controller;

import co.yusufavci.employeerecord.constants.SuccessMessages;
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
@RequestMapping("rest/post")
public class EmployeeController {

    private EmployeeService employeeService;

    @PostMapping("")
    public ResponseEntity<SuccessResponse> create(@RequestBody EmployeeDto employeeDto) {
        employeeService.create(employeeDto);
        return new ResponseEntity<>(new SuccessResponse(SuccessMessages.EMPLOYEE_CREATE_MESSAGE), HttpStatus.OK);
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

    @GetMapping("/list")
    public ResponseEntity<List<EmployeeDto>> list() {
        return new ResponseEntity<>(employeeService.listAll(), HttpStatus.OK);
    }

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
}
