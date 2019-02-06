package net.zwet.publickingdom.validation;

import net.zwet.publickingdom.objects.Kingdom;

public class NotEnemyValidation implements Validation {

    private String errorMessage;
    private Kingdom kingdom;
    private Kingdom kingdom1;

    public NotEnemyValidation(Kingdom kingdom, Kingdom kingdom1){
        this.kingdom = kingdom;
        this.kingdom1 = kingdom1;
    }

    @Override
    public boolean isValid() {
        boolean result = kingdom.isEnemyWith(kingdom1);
        if (result){
            this.errorMessage = kingdom.getName() + " en " + kingdom1.getName() + " zijn al vijanden!";
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
