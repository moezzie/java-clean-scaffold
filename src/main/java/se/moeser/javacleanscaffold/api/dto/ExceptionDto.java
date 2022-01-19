package se.moeser.javacleanscaffold.api.dto;

public class ExceptionDto {
    private String message;

    public ExceptionDto() {}
    public ExceptionDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
       this.message = message;
    }
}
