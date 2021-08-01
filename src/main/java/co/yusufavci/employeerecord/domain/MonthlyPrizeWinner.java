package co.yusufavci.employeerecord.domain;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Created by yusuf on 31.07.2021.
 */

@Entity
public class MonthlyPrizeWinner extends BaseEntity {

    @Column
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
