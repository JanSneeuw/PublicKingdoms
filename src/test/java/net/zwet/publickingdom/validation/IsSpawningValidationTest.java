package net.zwet.publickingdom.validation;

import net.zwet.publickingdom.commands.Spawn;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class IsSpawningValidationTest {

    @Test
    public void testIsValidSucceed() {
        Player player = mock(Player.class);
        Map<Player, BukkitTask> list = mock(HashMap.class);
        BukkitTask task = mock(BukkitTask.class);

        when(list.containsKey(player)).thenReturn(true);

        Spawn.hspawner.put(player, task);
        IsSpawningValidation isSpawningValidation = new IsSpawningValidation(player);
        assertEquals(true, isSpawningValidation.isValid());

    }

    @Test
    public void testIsValidFail(){
        Player player = mock(Player.class);
        HashMap list = mock(HashMap.class);
        when(list.containsKey(player)).thenReturn(false);
        IsSpawningValidation isSpawningValidation = new IsSpawningValidation(player);
        assertEquals(false, isSpawningValidation.isValid());
    }


    @Test
    public void isInvalid() {
    }
}