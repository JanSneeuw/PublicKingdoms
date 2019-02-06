package net.zwet.publickingdom.validation;

import net.zwet.publickingdom.commands.Spawn;
import org.bukkit.entity.Player;

public class IsSpawningValidation implements Validation {

    private String errorMessage;
    private Player player;

    public IsSpawningValidation(Player player){
        this.player = player;
    }

    @Override
    public boolean isValid() {
        boolean result = Spawn.hspawner.containsKey(this.player);
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
