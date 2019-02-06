package net.zwet.publickingdom.validation;

import net.zwet.publickingdom.objects.Kingdom;

public class AreAlliesValidation implements Validation {

    private String errorMessage;
    private Kingdom kingdom;
    private Kingdom kingdom2;

    public AreAlliesValidation(){

    }
    public AreAlliesValidation(Kingdom kingdom, Kingdom kingdom2){
        this.kingdom = kingdom;
        this.kingdom2 = kingdom2;
    }
    public AreAlliesValidation with(Kingdom kingdom){
        this.kingdom2 = kingdom;
        return this;
    }
    public AreAlliesValidation base(Kingdom kingdom){
        this.kingdom = kingdom;
        return this;
    }
    @Override
    public boolean isValid() {
        boolean result = kingdom.isAllyWith(kingdom2);
        if (!result){
            this.errorMessage = kingdom.getName() + " en " + kingdom2.getName() + " zijn geen bondgenoten!";
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

