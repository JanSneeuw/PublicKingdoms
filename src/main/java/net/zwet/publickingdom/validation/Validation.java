package net.zwet.publickingdom.validation;

public interface Validation {
    boolean isValid();
    boolean isInvalid();
    String getErrorMessage();
}

