package net.zwet.publickingdom.validation;

import net.zwet.publickingdom.objects.Kingdom;
import org.bukkit.entity.Player;

public class MaxAlliesReachedValidation implements Validation {

    private String errorMessage;
    private Kingdom kingdom;

    public MaxAlliesReachedValidation(){

    }

    public MaxAlliesReachedValidation(Kingdom kingdom){
        this.kingdom = kingdom;
    }

    public MaxAlliesReachedValidation kingdom(Kingdom kingdom){
        this.kingdom = kingdom;
        return this;
    }

    @Override
    public boolean isValid() {
        boolean result = !kingdom.maxAlliesReached();
        if (!result){
            this.errorMessage = "Je hebt het maximale aantal bondgenoten behaald!";
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
