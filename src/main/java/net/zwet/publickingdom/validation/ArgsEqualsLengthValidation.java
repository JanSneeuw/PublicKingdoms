package net.zwet.publickingdom.validation;

public class ArgsEqualsLengthValidation implements Validation {

    private int size;
    private int argsSize;
    private String errorMessage;

    public ArgsEqualsLengthValidation(int size, int argsSize){
        this.size = size;
        this.argsSize = argsSize;
    }

    @Override
    public boolean isValid() {
        boolean result = this.size == this.argsSize;
        if (!result){
            this.errorMessage = "Amount of arguments should be " + this.size + " but is " + this.argsSize;
        }
        return result;
    }

    @Override
    public boolean isInvalid() {
        return false;
    }

    @Override
    public String getErrorMessage() {
        return null;
    }
}
