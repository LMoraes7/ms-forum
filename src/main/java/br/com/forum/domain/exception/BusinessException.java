package br.com.forum.domain.exception;

public final class BusinessException extends RuntimeException {

    private final String message;

    public BusinessException(String message) {
        super();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
