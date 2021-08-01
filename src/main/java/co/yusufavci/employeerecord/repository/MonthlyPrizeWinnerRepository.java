package co.yusufavci.employeerecord.repository;

import co.yusufavci.employeerecord.domain.MonthlyPrizeWinner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Month;

@Repository
public interface MonthlyPrizeWinnerRepository extends JpaRepository<MonthlyPrizeWinner, String> {
    MonthlyPrizeWinner findByDate_MonthAndDate_Year(Month month, int year);
}