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

    public Optional<StringBuilder> getContents(){
        return Optional.of(contents);
    }

}
