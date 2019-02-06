package net.zwet.publickingdom.validation;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerOnlineValidationTest {

    @Test
    public void testIsValidSucceed() {
        Player player = mock(Player.class);
        String name = "";
        //noinspection deprecation
        when(Bukkit.getServer().getPlayer(name)).thenReturn(player);
        PlayerOnlineValidation playerOnlineValidation = new PlayerOnlineValidation("");
        assertEquals(true, playerOnlineValidation.isValid());
    }

    @Test
    public void testIsValidFail(){
        Player player = null;
        String name = "";
        //noinspection deprecation
        when(Bukkit.getServer().getPlayer(name)).thenReturn(player);
        PlayerOnlineValidation playerOnlineValidation = new PlayerOnlineValidation("");
        assertEquals(false, playerOnlineValidation.isValid());

    }

    @Test
    public void isInvalid() {
    }
}