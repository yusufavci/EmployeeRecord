package co.yusufavci.employeerecord.service;

import co.yusufavci.employeerecord.dto.DateAndSalaryRequest;
import co.yusufavci.employeerecord.dto.DepartmentLocationUpdateDto;
import co.yusufavci.employeerecord.dto.EmployeeDto;

import java.util.List;

/**
 * Created by yusuf on 31.07.2021.
 */

public interface EmployeeService {
    String create(EmployeeDto employeeDto);

    EmployeeDto read(String id);

    String update(EmployeeDto employeeDto);

    void delete(String id);

    List<EmployeeDto> listAllByDateAfterAndSalaryIsGreater(DateAndSalaryRequest request);

    String updateDepartmentLocation(DepartmentLocationUpdateDto locationUpdateDto);

    void drawWinnerOfTheMonth();

    EmployeeDto getWinnerOfTheMonth();
}

