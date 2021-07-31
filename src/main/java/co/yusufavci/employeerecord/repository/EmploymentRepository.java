package co.yusufavci.employeerecord.repository;

import co.yusufavci.employeerecord.domain.Employment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmploymentRepository extends JpaRepository<Employment, String> {
}