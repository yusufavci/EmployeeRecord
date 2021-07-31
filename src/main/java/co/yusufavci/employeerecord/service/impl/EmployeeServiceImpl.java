package co.yusufavci.employeerecord.service.impl;

import co.yusufavci.employeerecord.domain.Department;
import co.yusufavci.employeerecord.domain.Employee;
import co.yusufavci.employeerecord.domain.Employment;
import co.yusufavci.employeerecord.dto.DateAndSalaryRequest;
import co.yusufavci.employeerecord.dto.DepartmentLocationUpdateDto;
import co.yusufavci.employeerecord.dto.EmployeeDto;
import co.yusufavci.employeerecord.exception.EntityNotFoundException;
import co.yusufavci.employeerecord.repository.DepartmentRepository;
import co.yusufavci.employeerecord.repository.EmployeeRepository;
import co.yusufavci.employeerecord.repository.EmploymentRepository;
import co.yusufavci.employeerecord.repository.MonthlyPrizeWinnerRepository;
import co.yusufavci.employeerecord.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by yusuf on 31.07.2021.
 */

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;
    private EmploymentRepository employmentRepository;
    private DepartmentRepository departmentRepository;
    private MonthlyPrizeWinnerRepository monthlyPrizeWinnerRepository;

    @Override
    public String create(EmployeeDto employeeDto) {
        Employee employee = dtoToEntity(employeeDto);
        return employeeRepository.save(employee).getId();
    }

    @Override
    public EmployeeDto read(String id) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if (!employeeOptional.isPresent()) {
            throw new EntityNotFoundException("");
        }
        return entityToDto(employeeOptional.get());
    }

    @Override
    public String update(EmployeeDto employeeDto) {
        return null;
    }

    @Override
    public void delete(String id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public List<EmployeeDto> listAll() {
        List<EmployeeDto> employeeDtoList = new ArrayList<>();
        List<Employee> employeeList = employeeRepository.findAll();

        for (Employee employee : employeeList) {
            employeeDtoList.add(entityToDto(employee));
        }

        return employeeDtoList;
    }

    @Override
    public List<EmployeeDto> listAllByDateAfterAndSalaryIsGreater(DateAndSalaryRequest request) {
        List<EmployeeDto> employeeDtoList = new ArrayList<>();
        List<Employee> employeeList = employeeRepository.findAllByEmployment_StartDateAndEmployment_SalaryGreaterThan(request.getFromDate(), request.getSalary());

        for (Employee employee : employeeList) {
            employeeDtoList.add(entityToDto(employee));
        }

        return employeeDtoList;
    }

    @Override
    public String updateDepartmentLocation(DepartmentLocationUpdateDto locationUpdateDto) {

        return null;
    }

    @Override
    public void drawWinnerOfTheMonth() {

    }

    @Override
    public EmployeeDto getWinnerOfTheMonth() {
        return null;
    }

    private Employee dtoToEntity(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setPhone(employeeDto.getPhone());
        employee.setAddress(employeeDto.getAddress());

        Optional<Department> departmentOptional = departmentRepository.findById(employeeDto.getDepartmentId());
        if (departmentOptional.isPresent()) {
            employee.setDepartment(departmentOptional.get());
        } else {
            throw new EntityNotFoundException("");
        }

        Employment employment = new Employment();
        employment.setStartDate(employeeDto.getEmploymentStartDate());
        employment.setSalary(employeeDto.getSalary());
        employee.setEmployment(employment);
        return employee;
    }

    private EmployeeDto entityToDto(Employee employee) {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employee.getId());
        employeeDto.setFirstName(employee.getFirstName());
        employeeDto.setLastName(employee.getLastName());
        employeeDto.setPhone(employee.getPhone());
        employeeDto.setAddress(employee.getAddress());
        employeeDto.setDepartmentId(employee.getDepartment().getId());

        Employment employment = employee.getEmployment();
        employeeDto.setEmploymentId(employment.getId());
        employeeDto.setSalary(employment.getSalary());
        employeeDto.setEmploymentStartDate(employment.getStartDate());
        return employeeDto;
    }

    @Autowired
    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Autowired
    public void setEmploymentRepository(EmploymentRepository employmentRepository) {
        this.employmentRepository = employmentRepository;
    }

    @Autowired
    public void setDepartmentRepository(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Autowired
    public void setMonthlyPrizeWinnerRepository(MonthlyPrizeWinnerRepository monthlyPrizeWinnerRepository) {
        this.monthlyPrizeWinnerRepository = monthlyPrizeWinnerRepository;
    }
}
