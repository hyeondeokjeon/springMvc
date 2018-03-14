package springMvc.config.exception;

/**
 * Created by hyeondeok on 2018. 3. 9..
 */
public class FooException extends RuntimeException {
    private String message;

    public FooException(String _msg) {
        this.message = _msg;
    }

    public String getMessage() {
        return this.message;
    }
}
