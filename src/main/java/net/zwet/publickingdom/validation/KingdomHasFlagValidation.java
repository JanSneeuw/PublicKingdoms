package net.zwet.publickingdom.validation;

import net.zwet.publickingdom.objects.Kingdom;

public class KingdomHasFlagValidation implements Validation {

    private Kingdom kingdom;
    private String errorMessage;
    private String flag;

    public KingdomHasFlagValidation(Kingdom kingdom, String flag){
        this.kingdom = kingdom;
        this.flag = flag;

    }

    public KingdomHasFlagValidation kingdom(Kingdom kingdom){
        this.kingdom = kingdom;
        return this;
    }

    public KingdomHasFlagValidation flag(String flag){
        this.flag = flag;
        return this;
    }

    @Override
    public boolean isValid() {
        boolean result = kingdom.hasFlag(flag);
        if (!result){
            errorMessage = kingdom.getName() + " heeft niet de flag " + flag;
        }
        return result;
    }

    @Override
    public boolean isInvalid() {
        return false;
    }

    @Override
    public String getErrorMessage() {
        return this.errorMessage;
    }
}
