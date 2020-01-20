package net.zwet.publickingdom.validation;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InRegionValidationTest {

    @Test
    public void isValid() {
    /*
        Location location = mock(Location.class);

        World world = mock(World.class);

        Player player = mock(Player.class);
        when(player.getLocation()).thenReturn(location);

        ApplicableRegionSet set = mock(ApplicableRegionSet.class);
        when(set.size()).thenReturn(12);

        //WGBukkit.getRegionManager(world).getApplicableRegions(player.getLocation()).size() != 0
        RegionManager manager = mock(RegionManager.class);
        when(manager.getApplicableRegions(player.getLocation())).thenReturn(set);

        WGBukkit bukkit = mock(WGBukkit.class);
        when(bukkit.getRegionManager(world)).thenReturn(manager);

//        when(WGBukkit.getRegionManager(world).getApplicableRegions(player.getLocation()).size() != 0).thenReturn(true);

        InRegionValidation inRegionValidation = new InRegionValidation(player, world);
        assertEquals(true, inRegionValidation.isValid());
        */
    }

    @Test
    public void isInvalid() {
    }
}