package co.yusufavci.employeerecord.service.impl;

import co.yusufavci.employeerecord.domain.*;
import co.yusufavci.employeerecord.dto.DateAndSalaryRequest;
import co.yusufavci.employeerecord.dto.DepartmentLocationUpdateDto;
import co.yusufavci.employeerecord.dto.EmployeeDto;
import co.yusufavci.employeerecord.exception.EntityNotFoundException;
import co.yusufavci.employeerecord.repository.*;
import co.yusufavci.employeerecord.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * Created by yusuf on 31.07.2021.
 */

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;
    private EmploymentRepository employmentRepository;
    private DepartmentRepository departmentRepository;
    private MonthlyPrizeWinnerRepository monthlyPrizeWinnerRepository;
    private LocationRepository locationRepository;

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
        //TODO
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
        Optional<Department> departmentOptional = departmentRepository.findById(locationUpdateDto.getDepartmentId());
        if (!departmentOptional.isPresent()) {
            throw new EntityNotFoundException("");
        }
        Optional<Location> locationOptional = locationRepository.findById(locationUpdateDto.getNewLocationId());
        if (!locationOptional.isPresent()) {
            throw new EntityNotFoundException("");
        }

        Department department = departmentOptional.get();
        department.setLocation(locationOptional.get());

        return departmentRepository.save(department).getId();
    }

    @Override
    public void drawWinnerOfTheMonth() {
        List<Employee> employeeList = employeeRepository.findAll();
        int listSize = employeeList.size();
        Random r = new Random();
        int result = r.nextInt(listSize);
        Employee luckyEmployee = employeeList.get(result);

        MonthlyPrizeWinner monthlyPrizeWinner = new MonthlyPrizeWinner();
        monthlyPrizeWinner.setEmployee(luckyEmployee);
        monthlyPrizeWinner.setDate(LocalDate.now());
        monthlyPrizeWinnerRepository.save(monthlyPrizeWinner);
    }

    @Override
    public EmployeeDto getWinnerOfTheMonth() {
        LocalDate localDate = LocalDate.now();
        MonthlyPrizeWinner monthlyPrizeWinner = monthlyPrizeWinnerRepository.findByDate_MonthAndDate_Year(localDate.getMonth(), localDate.getYear());
        if (monthlyPrizeWinner == null) {
            throw new EntityNotFoundException("");
        }
        return entityToDto(monthlyPrizeWinner.getEmployee());
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

    @Autowired
    public void setLocationRepository(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }
}
