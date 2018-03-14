package springMvc.config.exception;

/**
 * Created by hyeondeok on 2018. 3. 9..
 */
public class BarException extends RuntimeException{
    private String message;

    public BarException(String _msg) {
        this.message = _msg;
    }

    public String getMessage() {
        return this.message;
    }
}
