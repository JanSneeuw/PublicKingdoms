package net.zwet.publickingdom.validation;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HasItemMetaValidationTest {

    @Test
    public void testIsValidSucceed() {

        ItemStack itemStack = mock(ItemStack.class);
        when(itemStack.hasItemMeta()).thenReturn(true);

        Player player = mock(Player.class);
        when(player.getItemInHand()).thenReturn(itemStack);

        HasItemMetaValidation itemMetaValidation = new HasItemMetaValidation(player);
        assertEquals(true, itemMetaValidation.isValid());

    }

    @Test
    public void testIsValidFail(){
        ItemStack itemStack = mock(ItemStack.class);
        when(itemStack.hasItemMeta()).thenReturn(false);

        Player player = mock(Player.class);
        when(player.getItemInHand()).thenReturn(itemStack);

        HasItemMetaValidation itemMetaValidation = new HasItemMetaValidation(player);
        assertEquals(false, itemMetaValidation.isValid());
    }

    @Test
    public void isInvalid() {
    }
}