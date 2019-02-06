package net.zwet.publickingdom.validation;

import net.zwet.publickingdom.objects.Playerdata;
import org.bukkit.entity.Player;

public class HasPermissionValidation implements Validation {

    private Playerdata playerdata;
    private String permission;
    private String errorMessage;

    public HasPermissionValidation(Player player, String permission){
        this.playerdata = new Playerdata(player);
        this.permission = permission;
    }

    public HasPermissionValidation(Player player){
        this.playerdata = new Playerdata(player);
    }

    public HasPermissionValidation permission(String permission){
        this.permission = permission;
        return this;
    }
    @Override
    public boolean isValid() {
        boolean result = playerdata.hasPermission(this.permission);
        if (!result){
            errorMessage = "Je hebt geen toegang tot dit command!";
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
