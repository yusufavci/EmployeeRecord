package co.yusufavci.employeerecord.dto;

/**
 * Created by yusuf on 31.07.2021.
 */

public class DepartmentLocationUpdateDto {

    private String departmentId;
    private String newLocationId;

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getNewLocationId() {
        return newLocationId;
    }

    public void setNewLocationId(String newLocationId) {
        this.newLocationId = newLocationId;
    }
}
