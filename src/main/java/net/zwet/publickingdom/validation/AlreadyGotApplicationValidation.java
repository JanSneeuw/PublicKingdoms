package net.zwet.publickingdom.validation;

import net.zwet.publickingdom.objects.Kingdom;
import org.bukkit.entity.Player;

public class AlreadyGotApplicationValidation implements Validation {

    private String errorMessage;
    private Player player;
    private Kingdom kingdom;
    public AlreadyGotApplicationValidation(Player player, Kingdom kingdom){
        this.player = player;
        this.kingdom = kingdom;
    }

    @Override
    public boolean isValid() {
        boolean result = !this.kingdom.hasApplication(this.player.getUniqueId().toString());
        if (!result){
            this.errorMessage = this.kingdom.getName() + " doesn't have application from " + this.player.getName();
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
