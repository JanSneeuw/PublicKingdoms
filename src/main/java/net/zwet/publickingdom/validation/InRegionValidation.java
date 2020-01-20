package net.zwet.publickingdom.validation;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class InRegionValidation implements Validation {
    private RegionQuery query;

    private RegionManager regioManager;

    private Player player;
    private World world;
    private String errormessage;

    public InRegionValidation(Player player, World world){
        this.player = player;
        this.world = world;
    }

    public InRegionValidation(RegionQuery query, Player player, World world) {
        this.player = player;
        this.world = world;
        this.query = query;
    }

    @Override
    public boolean isValid() {

        LocalPlayer lplayer = WorldGuardPlugin.inst().wrapPlayer(player);
        boolean result = query.getApplicableRegions(lplayer.getLocation()).size() != 0;
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
