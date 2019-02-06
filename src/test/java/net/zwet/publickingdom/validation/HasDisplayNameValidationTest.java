package net.zwet.publickingdom.validation;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HasDisplayNameValidationTest {

    @Test
    public void testIsValidSucceed() {
        ItemMeta itemMeta = mock(ItemMeta.class);
        when(itemMeta.hasDisplayName()).thenReturn(true);

        ItemStack itemStack = mock(ItemStack.class);
        when(itemStack.getItemMeta()).thenReturn(itemMeta);

        Player player = mock(Player.class);
        when(player.getItemInHand()).thenReturn(itemStack);

        HasDisplayNameValidation hasDisplayNameValidation = new HasDisplayNameValidation(player);
        assertEquals(true, hasDisplayNameValidation.isValid());

    }

    @Test
    public void testIsValidFail(){
        ItemMeta itemMeta = mock(ItemMeta.class);
        when(itemMeta.hasDisplayName()).thenReturn(false);

        ItemStack itemStack = mock(ItemStack.class);
        when(itemStack.getItemMeta()).thenReturn(itemMeta);

        Player player = mock(Player.class);
        when(player.getItemInHand()).thenReturn(itemStack);

        HasDisplayNameValidation hasDisplayNameValidation = new HasDisplayNameValidation(player);
        assertEquals(false, hasDisplayNameValidation.isValid());

    }

    @Test
    public void isInvalid() {
    }
}