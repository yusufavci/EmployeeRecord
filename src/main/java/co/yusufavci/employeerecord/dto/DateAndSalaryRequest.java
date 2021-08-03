package co.yusufavci.employeerecord.dto;

import java.time.LocalDate;

/**
 * Created by yusuf on 31.07.2021.
 */
public class DateAndSalaryRequest {

    private LocalDate fromDate;
    private Double salary;

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }
}
