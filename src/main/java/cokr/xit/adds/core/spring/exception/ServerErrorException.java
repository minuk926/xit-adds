package cokr.xit.adds.core.spring.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ServerErrorException extends RuntimeException {
    private final HttpStatus status;
    private final String body;

    public ServerErrorException(HttpStatus status, String body) {
        this.status = status;
        this.body = body;
    }
}
