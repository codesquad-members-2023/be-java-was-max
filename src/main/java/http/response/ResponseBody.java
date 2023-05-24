package http.response;


public class ResponseBody {
    private byte[] contents;
    public ResponseBody(){
    }

    public void add(byte[] body) {
        contents = body;
    }

    public byte[] get(){
        return contents;
    }

    // TODO: 바이트 코드 문자열로 수정하기
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (byte b: contents) {
            sb.append((char) b);
        }
        return sb.toString();
    }
}
