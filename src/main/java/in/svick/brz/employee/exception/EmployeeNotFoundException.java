package in.svick.brz.employee.exception;

public class EmployeeNotFoundException extends RuntimeException {

    public EmployeeNotFoundException(String ex) {
        super(ex);
    }
}
