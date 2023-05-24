package http.request;


public class RequestBody {
    private final StringBuilder contents;

    public RequestBody(){
        this.contents = new StringBuilder();
    }

    public void add(char ch){
        contents.append(ch);
    }

    public String get(){
        return contents.toString();
    }

}
