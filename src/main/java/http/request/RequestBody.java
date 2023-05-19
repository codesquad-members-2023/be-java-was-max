package http.request;

import java.util.Optional;

public class RequestBody {
    private final StringBuilder contents;

    public RequestBody(){
        this.contents = new StringBuilder();
    }

    public void addContent(char ch){
        contents.append(ch);
    }

    public String getContents(){
        return contents.toString();
    }

}
