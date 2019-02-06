package net.zwet.publickingdom.validation;

import org.bukkit.entity.Player;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HasBukkitPermissionValidationTest {

    @Test
    public void testIsValidSucceed() {
        Player player = mock(Player.class);
        String permission = "kingdom.staff";
        when(player.hasPermission(permission)).thenReturn(true);
        HasBukkitPermissionValidation hasBukkitPermissionValidation = new HasBukkitPermissionValidation(player, permission);
        assertEquals(true, hasBukkitPermissionValidation.isValid());

    }
    @Test
    public void testIsValidFail(){
        Player player = mock(Player.class);
        String permission = "kingdom.staff";
        when(player.hasPermission(permission)).thenReturn(false);
        HasBukkitPermissionValidation hasBukkitPermissionValidation = new HasBukkitPermissionValidation(player, permission);
        assertEquals(false, hasBukkitPermissionValidation.isValid());
    }

    @Test
    public void isInvalid() {
    }
}