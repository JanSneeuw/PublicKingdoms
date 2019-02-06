package net.zwet.publickingdom.validation;

import org.bukkit.entity.Player;

public class HasDisplayNameValidation implements Validation {

    private String errorMessage;
    private Player player;

    public HasDisplayNameValidation(Player player){
        this.player = player;
    }

    @Override
    public boolean isValid() {
        boolean result = player.getItemInHand().getItemMeta().hasDisplayName();
        if (!result){
            this.errorMessage = player.getItemInHand() + " heeft geen displayname!";
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
