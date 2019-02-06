package net.zwet.publickingdom.validation;

import org.bukkit.entity.Player;

public class HasBukkitPermissionValidation implements Validation {

    private String permission;
    private Player player;
    private String errorMessage;
    public HasBukkitPermissionValidation(Player player, String permission){
        this.player = player;
        this.permission = permission;
    }

    @Override
    public boolean isValid() {
        boolean result = player.hasPermission(permission);
        if (!result){
            this.errorMessage = "Je hebt niet de permission: " + permission;
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
