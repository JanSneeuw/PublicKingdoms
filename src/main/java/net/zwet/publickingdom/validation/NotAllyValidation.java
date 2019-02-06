package net.zwet.publickingdom.validation;

import net.zwet.publickingdom.objects.Kingdom;

public class NotAllyValidation implements Validation {

    private String errorMessage;
    private Kingdom kingdom;
    private Kingdom kingdom1;

    public NotAllyValidation(Kingdom kingdom, Kingdom kingdom1){
        this.kingdom = kingdom;
        this.kingdom1 = kingdom1;
    }

    @Override
    public boolean isValid() {
        boolean result = kingdom.isAllyWith(kingdom1);
        if (result){
            this.errorMessage = kingdom.getName() + " en " + kingdom1.getName() + " zijn al bondgenoten!";
        }

        return ! result;
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
