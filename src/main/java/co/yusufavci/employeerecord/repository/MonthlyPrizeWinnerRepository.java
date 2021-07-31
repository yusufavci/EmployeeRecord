package co.yusufavci.employeerecord.repository;

import co.yusufavci.employeerecord.domain.MonthlyPrizeWinner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonthlyPrizeWinnerRepository extends JpaRepository<MonthlyPrizeWinner, String> {
}