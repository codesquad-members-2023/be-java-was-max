package model;

public enum Status {

    OK(200, "OK"),
    FOUND(302, "Found");

    private final int code;
    private final String reason;

    private Status(int code, String reason) {
        this.code = code;
        this.reason = reason;
    }

    public static Status getStatusOfCode(int statusCode) {
        for (Status status : Status.values()) {
            if (status.code == statusCode) {
                return status;
            }
        }
        return null;
    }

    public String getStatusMessage() {
        return String.valueOf(code) + " " + reason;
    }

    public int getCode() {
        return code;
    }

    public String getStatus() {
        return reason;
    }
}
