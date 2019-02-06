package net.zwet.publickingdom.validation;

import org.bukkit.entity.Player;

public class ItemInHandValidation implements Validation {

    private Player player;
    private String errormessage;

    public ItemInHandValidation(Player player){
        this.player = player;

    }
    public ItemInHandValidation player(Player player){
        this.player = player;
        return this;
    }

    @Override
    public boolean isValid() {
        boolean result = player.getItemInHand() != null;
        if (!result){
            this.errormessage = "Je hebt niks in je hand!";
        }
        return result;
    }

    @Override
    public boolean isInvalid() {
        return ! isValid();
    }

    @Override
    public String getErrorMessage() {
        return errormessage;
    }
}
