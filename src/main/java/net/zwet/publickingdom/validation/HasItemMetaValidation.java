package net.zwet.publickingdom.validation;

import org.bukkit.entity.Player;

public class HasItemMetaValidation implements Validation {

    private String errorMessage;
    private Player player;

    public HasItemMetaValidation(Player player){
        this.player = player;
    }

    @Override
    public boolean isValid() {
        boolean result = player.getItemInHand().hasItemMeta();
        if (!result){
            this.errorMessage = player.getItemInHand() + " heeft geen itemmeta";
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
