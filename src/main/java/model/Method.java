package model;

public enum Method {

    GET("GET", "get"),
    POST("POST", "post"),
    PUT("PUT", "put"),
    DELETE("DELETE", "delete");

    private final String upperMethod;
    private final String lowerMethod;

    private Method(String upperMethod, String lowerMethod) {
        this.upperMethod = upperMethod;
        this.lowerMethod = lowerMethod;
    }

    public boolean isGet() {
        if (lowerMethod.equals("get")) {
            return true;
        }
        return false;
    }

    public boolean isPost() {
        if (lowerMethod.equals("post")) {
            return true;
        }
        return false;
    }

    public boolean isPut() {
        if (lowerMethod.equals("put")) {
            return true;
        }
        return false;
    }

    public boolean isDelete() {
        if (lowerMethod.equals("delete")) {
            return true;
        }
        return false;
    }

    public boolean hasBody() {
        if (isPost() || isPut()) {
            return true;
        }
        return false;
    }

}
