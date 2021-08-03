package co.yusufavci.employeerecord;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(scanBasePackages = {"co.yusufavci.employeerecord.controller", "co.yusufavci.employeerecord.service"})
public class EmployeeRecordApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeRecordApplication.class, args);
    }

}
