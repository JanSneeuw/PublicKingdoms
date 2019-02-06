package net.zwet.publickingdom.validation;

import net.zwet.publickingdom.Exceptions.NoSuchKingdomException;
import net.zwet.publickingdom.objects.Kingdom;

public class KingdomExistsValidation implements Validation {

    private String errorMessage;
    private String KingdomString;
    private Kingdom kingdom;

    public KingdomExistsValidation(String kingdom){
        this.KingdomString = kingdom;
        try{
            this.kingdom = new Kingdom(kingdom);
        }catch(NoSuchKingdomException e){
            this.kingdom = null;
        }
    }

    @Override
    public boolean isValid() {
        boolean result = this.kingdom != null;
        if (!result){
            this.errorMessage = this.KingdomString + " bestaat niet!!";
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
