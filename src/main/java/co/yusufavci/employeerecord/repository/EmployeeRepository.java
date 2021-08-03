package co.yusufavci.employeerecord.repository;

import co.yusufavci.employeerecord.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    List<Employee> findAllByEmployment_StartDateAndEmployment_SalaryGreaterThan(LocalDate date, Double salary);
}