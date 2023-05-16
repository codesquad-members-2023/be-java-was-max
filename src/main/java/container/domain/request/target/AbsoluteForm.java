package container.domain.request.target;

// A complete URL, known as the absolute form, is mostly used with GET when connected to a proxy.
// GET https://developer.mozilla.org/en-US/docs/Web/HTTP/Messages HTTP/1.1
public class AbsoluteForm {
    private final Url url;

    public AbsoluteForm(Url url) {
        this.url = url;
    }

    public Url getUrl() {
        return url;
    }
}
