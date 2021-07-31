package co.yusufavci.employeerecord.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by yusuf on 31.07.2021.
 */

@Entity
public class Employment extends BaseEntity {

    @Column
    private Double salary;

    @Column
    private Date startDate;

    @Column
    private Date endDate;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "employee_id")
    private Employee employee;

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
