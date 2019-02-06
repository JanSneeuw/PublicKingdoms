package net.zwet.publickingdom.validation;

import net.zwet.publickingdom.objects.Kingdom;

public class AreEnemiesValidation implements Validation {

    private String errorMessage;
    private Kingdom kingdom;
    private Kingdom kingdom2;

    public AreEnemiesValidation(){

    }
    public AreEnemiesValidation(Kingdom kingdom, Kingdom kingdom2){
        this.kingdom = kingdom;
        this.kingdom2 = kingdom2;
    }
    public AreEnemiesValidation with(Kingdom kingdom){
        this.kingdom2 = kingdom;
        return this;
    }
    public AreEnemiesValidation base(Kingdom kingdom){
        this.kingdom = kingdom;
        return this;
    }
    @Override
    public boolean isValid() {
        boolean result = kingdom.isEnemyWith(kingdom2);
        if (!result){
            this.errorMessage = kingdom.getName() + " en " + kingdom2.getName() + " zijn geen vijanden!";
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
