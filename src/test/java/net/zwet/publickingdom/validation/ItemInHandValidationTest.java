package net.zwet.publickingdom.validation;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemInHandValidationTest {

    @Test
    public void testIsValidSucceed() {
        Player player = mock(Player.class);
        ItemStack material = new ItemStack(Material.GOLD_SWORD);
        when(player.getItemInHand()).thenReturn(material);
        ItemInHandValidation itemInHandValidation = new ItemInHandValidation(player);
        assertEquals(true, itemInHandValidation.isValid());


    }

    @Test
    public void testIsValidFail(){

        Player player = mock(Player.class);
        ItemStack material = new ItemStack(Material.GOLD_SWORD);
        when(player.getItemInHand()).thenReturn(null);
        ItemInHandValidation itemInHandValidation = new ItemInHandValidation(player);
        assertEquals(false, itemInHandValidation.isValid());

    }

    @Test
    public void isInvalid() {
    }
}