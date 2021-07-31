package co.yusufavci.employeerecord.repository;

import co.yusufavci.employeerecord.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    List<Employee> findAllByEmployment_StartDateAndEmployment_SalaryGreaterThan(Date date, Double salary);
}