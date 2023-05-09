package webserver;

public enum HttpMethod {
    OK(200);

    private final int value;

    HttpMethod(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
