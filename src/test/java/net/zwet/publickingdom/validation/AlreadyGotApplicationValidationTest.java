package net.zwet.publickingdom.validation;

import net.zwet.publickingdom.objects.Kingdom;
import org.bukkit.entity.Player;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AlreadyGotApplicationValidationTest {
    private Player player;
    private Kingdom kingdom;
    @Test
    public void testIsValid() {
        Kingdom kingdom = mock(Kingdom.class);
        Player player = mock(Player.class);
        when(kingdom.hasApplication(player.getUniqueId().toString())).thenReturn(true);
        AlreadyGotApplicationValidation alreadyGotApplicationValidation = new AlreadyGotApplicationValidation(player, kingdom);
        assertEquals(true, alreadyGotApplicationValidation.isValid());

    }
    @Test
    public void testIsValidFailed(){
        Kingdom kingdom = mock(Kingdom.class);
        Player player = mock(Player.class);
        when(kingdom.hasApplication(player.getUniqueId().toString())).thenReturn(false);
        AlreadyGotApplicationValidation alreadyGotApplicationValidation = new AlreadyGotApplicationValidation(player, kingdom);
        assertEquals(false, alreadyGotApplicationValidation.isValid());

    }
    @Test
    public void isInvalid() {
    }
}