package net.zwet.publickingdom.validation;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.managers.RegionManager;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class InRegionValidation implements Validation {
    private WGBukkit bukkit;

    private RegionManager regioManager;

    private Player player;
    private World world;
    private String errormessage;

    public InRegionValidation(Player player, World world){
        this.player = player;
        this.world = world;
    }

    public InRegionValidation(WGBukkit bukkit, Player player, World world) {
        this.player = player;
        this.world = world;
        this.bukkit = bukkit;
    }

    @Override
    public boolean isValid() {
        boolean result = bukkit.getRegionManager(world).getApplicableRegions(player.getLocation()).size() != 0;
        if (!result){
            errormessage = "Je staat niet in een region!";
        }
        return result;
    }

    @Override
    public boolean isInvalid() {
        return ! isValid();
    }

    @Override
    public String getErrorMessage() {
        return this.errormessage;
    }
}
