package net.zwet.publickingdom.validation;

import net.zwet.publickingdom.objects.Playerdata;
import org.bukkit.entity.Player;

public class HasHomeValidation implements Validation {

    private Player player;
    private String errorMessage;
    private Playerdata playerdata;
    public HasHomeValidation(Player player){
        this.player = player;
        this.playerdata = new Playerdata(this.player);
    }

    public HasHomeValidation(Playerdata playerdata){
        this.playerdata = playerdata;
    }

    @Override
    public boolean isValid() {
        boolean result = this.playerdata.hasHome();
        if (!result){
            this.errorMessage = "Je hebt geen home!";
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
