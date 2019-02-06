package net.zwet.publickingdom.validation;

import net.zwet.publickingdom.objects.Kingdom;

public class HasHertogdomValidation implements Validation {

    private String errorMessage;
    private Kingdom kingdom;

    public HasHertogdomValidation(Kingdom kingdom){
        this.kingdom = kingdom;
    }

    @Override
    public boolean isValid() {
        boolean result = kingdom.hasHertogdom();
        if (!result){
            this.errorMessage = kingdom.getName() + " heeft geen hertogdom!";
        }


        return result;
    }

    @Override
    public boolean isInvalid() {
        return ! isValid();
    }

    @Override
    public String getErrorMessage() {
        return this.errorMessage;
    }
}
