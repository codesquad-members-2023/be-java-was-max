package webserver.response;

public enum StatusCode {
    OK(200);

    private int value;

    StatusCode(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("%d %s", value, name());
    }
}
