package springMvc.config.exception;

/**
 * Created by hyeondeok on 2018. 3. 9..
 */
public class RestException extends RuntimeException{
    String message;
    String status;

    public void setMessage(String _msg) {
        this.message = _msg;
    }

    public String getMessage() {
        return this.message;
    }

    public void setStatus(String _status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }
}
