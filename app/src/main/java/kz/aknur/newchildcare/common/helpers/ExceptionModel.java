package kz.aknur.newchildcare.common.helpers;

public class ExceptionModel {
    private String error_code;
    private String status;
    private String error_description;
    public String code() {
        return error_code;
    }
    public String status() {
        return status;
    }
    public String message() {
        return error_description;
    }
}
