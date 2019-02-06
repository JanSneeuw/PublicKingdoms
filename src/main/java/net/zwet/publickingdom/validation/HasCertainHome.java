package net.zwet.publickingdom.validation;

import net.zwet.publickingdom.objects.Playerdata;
import org.bukkit.entity.Player;

public class HasCertainHome implements Validation {

    private Player player;
    private String home;
    private String errorMessage;
    private Playerdata playerdata;
    public HasCertainHome(Player player, String home){
        this.player = player;
        this.home = home;
        this.playerdata = new Playerdata(this.player);
    }

    public HasCertainHome(Playerdata playerdata, String home){
        this.playerdata = playerdata;
        this.home = home;
    }

    @Override
    public boolean isValid() {
        boolean result = this.playerdata.hasHome(this.home);
        if (!result){
            this.errorMessage = "De home " + this.home + " bestaat niet!";
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
