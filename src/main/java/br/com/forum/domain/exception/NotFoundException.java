package br.com.forum.domain.exception;

public final class NotFoundException extends RuntimeException {

    private final Class<?> classTarget;
    private final Object target;

    public NotFoundException(Class<?> classTarget, Object target) {
        super();
        this.classTarget = classTarget;
        this.target = target;
    }

    public Object getTarget() {
        return target;
    }

    public Class<?> getClassTarget() {
        return classTarget;
    }
}
