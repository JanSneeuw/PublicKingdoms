package net.zwet.publickingdom.validation;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.zwet.publickingdom.objects.Kingdom;

public class HasRegionValidation implements Validation {

    private String errorMessage;
    private Kingdom kingdom;
    private ProtectedRegion region;

    public HasRegionValidation(Kingdom kingdom, ProtectedRegion region){
        this.kingdom = kingdom;
        this.region = region;
    }

    public HasRegionValidation(Kingdom kingdom){
        this.kingdom = kingdom;
    }

    public HasRegionValidation region(ProtectedRegion region){
        this.region = region;
        return this;
    }

    @Override
    public boolean isValid() {
        boolean result = this.kingdom.hasRegion(this.region);
        if (!result){
            this.errorMessage = this.region.getId() + " is geen region van " + kingdom.getName();
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
