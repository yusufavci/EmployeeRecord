package co.yusufavci.employeerecord.dto;

import java.util.Date;

/**
 * Created by yusuf on 31.07.2021.
 */
public class DateAndSalaryRequest {

    private Date fromDate;
    private Double salary;

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }
}
