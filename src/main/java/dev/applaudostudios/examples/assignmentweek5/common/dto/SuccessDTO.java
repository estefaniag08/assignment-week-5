package dev.applaudostudios.examples.assignmentweek5.common.dto;

public class SuccessDTO implements IEntityDTO{
    private String message;

    public SuccessDTO() {
    }

    public SuccessDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
