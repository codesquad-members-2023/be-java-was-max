package http.response;

public class ResponseBody {
    private final StringBuilder contents;
    public ResponseBody(){
        this.contents = new StringBuilder();
    }

    public void add(String line) {
        contents.append(line);
    }

    public String get(){
        return contents.toString();
    }
}
