package net.zwet.publickingdom.validation;

import net.zwet.publickingdom.objects.Kingdom;

public class HertogdomExistsValidation implements Validation {

    private Kingdom kingdom;
    private String errorMessage;
    private String hertogdom;

    public HertogdomExistsValidation(Kingdom kingdom){
        this.kingdom = kingdom;
    }

    public HertogdomExistsValidation(Kingdom kingdom, String hertogdom){
        this.kingdom = kingdom;
        this.hertogdom = hertogdom;
    }

    public HertogdomExistsValidation hertogdom(String hertogdom){
        this.hertogdom = hertogdom;
        return this;
    }

    @Override
    public boolean isValid() {
        boolean result = this.kingdom.hertogdomExists(this.hertogdom);
        if (!result){
            this.errorMessage = this.hertogdom + " bestaat niet!";
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
