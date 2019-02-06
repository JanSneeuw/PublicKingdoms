package net.zwet.publickingdom.validation;

import net.zwet.publickingdom.commands.Spawn;
import org.bukkit.entity.Player;

public class NotSpawningValidation implements Validation {

    private String errorMessage;
    private Player player;

    public NotSpawningValidation(Player player){
        this.player = player;
    }

    @Override
    public boolean isValid() {
        boolean result = !Spawn.hspawner.containsKey(this.player);
        if (!result){
            this.errorMessage = "Je gaat al teleporteren!";
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
