package pl.symentis.alge.runtime;

public class SDLException extends RuntimeException {
    public SDLException(String message) {
        super(message);
    }

    public SDLException(Throwable cause) {
        super(cause);
    }

    public SDLException(String message, Throwable cause) {
        super(message, cause);
    }
}
