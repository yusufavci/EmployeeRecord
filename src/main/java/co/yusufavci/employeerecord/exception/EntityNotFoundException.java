package co.yusufavci.employeerecord.exception;

/**
 * Created by yusuf on 1.08.2021.
 */

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException() {
    }

    public EntityNotFoundException(String message) {
        super(message);
    }
}
