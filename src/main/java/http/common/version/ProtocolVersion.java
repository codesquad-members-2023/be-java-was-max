package http.common.version;

import java.util.Objects;

public class ProtocolVersion {

    private final String protocol;
    private final int major;
    private final int minor;

    public ProtocolVersion(String protocol, int major, int minor) {
        this.protocol = protocol;
        this.major = major;
        this.minor = minor;
    }

    public String getProtocol() {
        return protocol;
    }

    public int getMajor() {
        return major;
    }

    public int getMinor() {
        return minor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProtocol(), getMajor(), getMinor());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProtocolVersion)) {
            return false;
        }
        ProtocolVersion that = (ProtocolVersion) o;
        return getMajor() == that.getMajor() && getMinor() == that.getMinor()
            && getProtocol().equals(
            that.getProtocol());
    }

    @Override
    public String toString() {
        return String.format("%s/%d.%d", protocol, major, minor);
    }
}
