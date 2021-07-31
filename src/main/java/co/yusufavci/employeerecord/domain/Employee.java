package co.yusufavci.employeerecord.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created by yusuf on 31.07.2021.
 */

@Entity
public class Employee extends BaseEntity{

    @Column
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
