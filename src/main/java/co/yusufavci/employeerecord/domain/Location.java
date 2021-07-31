package co.yusufavci.employeerecord.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created by yusuf on 31.07.2021.
 */

@Entity
public class Location extends BaseEntity {

    @Column
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
