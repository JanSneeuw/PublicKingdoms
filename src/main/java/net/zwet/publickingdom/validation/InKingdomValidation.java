package net.zwet.publickingdom.validation;

import net.zwet.publickingdom.objects.Playerdata;
import org.bukkit.entity.Player;

public class InKingdomValidation implements Validation {
    private Playerdata playerdata;
    private String errormessage;

    public InKingdomValidation(Player player){
        this.playerdata = new Playerdata(player);
    }

    public InKingdomValidation(Playerdata playerdata){
        this.playerdata = playerdata;
    }

    public InKingdomValidation(String name){
        this.playerdata = new Playerdata(name);
    }

    @Override
    public boolean isValid() {
        boolean result = playerdata.isInKingdom();
        if (!result){
            this.errormessage = "Je zit niet in een kingdom!";
        }

        return result;
    }

    @Override
    public boolean isInvalid() {
        return ! this.isValid();
    }

    @Override
    public String getErrorMessage() {
        return this.errormessage;
    }
}

