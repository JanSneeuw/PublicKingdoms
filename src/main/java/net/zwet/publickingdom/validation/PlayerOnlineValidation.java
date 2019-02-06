package net.zwet.publickingdom.validation;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerOnlineValidation implements Validation {

    private String playername;
    private String errorMessage;

    public PlayerOnlineValidation(String playername){
        this.playername = playername;
    }

    @Override
    public boolean isValid() {
        //noinspection deprecation
        boolean result = Bukkit.getPlayer(playername) != null;
        if (!result){
            this.errorMessage = playername + " is not online!";
            
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
