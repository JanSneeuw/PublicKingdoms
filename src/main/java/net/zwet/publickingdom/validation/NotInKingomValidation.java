package net.zwet.publickingdom.validation;

import net.zwet.publickingdom.objects.Playerdata;
import org.bukkit.entity.Player;

public class NotInKingomValidation implements Validation{

    private Playerdata playerdata;
    private String errormessage;

    public NotInKingomValidation(Player player){
        this.playerdata = new Playerdata(player);
    }

    public NotInKingomValidation(Playerdata playerdata){
        this.playerdata = playerdata;
    }

    @Override
    public boolean isValid() {
        boolean result = !playerdata.isInKingdom();
        if (result){
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
