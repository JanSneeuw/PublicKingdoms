package net.zwet.publickingdom.validation;

import net.zwet.publickingdom.objects.Kingdom;

public class KingdomHasRegionValidation implements Validation {

    private Kingdom kingdom;
    private String errorMessage;

    public KingdomHasRegionValidation(Kingdom kingdom){
        this.kingdom = kingdom;
    }


    @Override
    public boolean isValid() {
        boolean result = kingdom.getRegion() != null;
        if (!result){
            this.errorMessage = kingdom.getName() + " heeft geen region!";
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
