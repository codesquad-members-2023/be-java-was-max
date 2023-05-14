package container.domain;

public enum HttpMethod {
    OK(200),
    NOT_FOUND(404);

    private final int value;

    HttpMethod(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
