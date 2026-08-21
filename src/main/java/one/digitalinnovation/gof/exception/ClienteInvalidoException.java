package one.digitalinnovation.gof.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ClienteInvalidoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ClienteInvalidoException(String mensagem) {
        super(mensagem);
    }
}