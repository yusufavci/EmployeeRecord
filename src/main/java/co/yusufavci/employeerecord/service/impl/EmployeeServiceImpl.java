package co.yusufavci.employeerecord.service.impl;

import co.yusufavci.employeerecord.dto.EmployeeDto;
import co.yusufavci.employeerecord.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yusuf on 31.07.2021.
 */

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Override
    public String create(EmployeeDto employeeDto) {
        return null;
    }

    @Override
    public EmployeeDto read(String id) {
        return null;
    }

    @Override
    public String update(EmployeeDto employeeDto) {
        return null;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public List<EmployeeDto> listAll() {
        return null;
    }
}
