package net.zwet.publickingdom.validation;

import net.zwet.publickingdom.objects.Kingdom;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class KingdomHasFlagValidationTest {

    @Test
    public void testIsValidSucceed() {
        Kingdom kingdom = mock(Kingdom.class);
        String flag = "";
        when(kingdom.hasFlag(flag)).thenReturn(true);
        KingdomHasFlagValidation kingdomHasFlagValidation = new KingdomHasFlagValidation(kingdom, flag);
        assertEquals(true, kingdomHasFlagValidation.isValid());

    }

    @Test
    public void testIsValidFail() {
        Kingdom kingdom = mock(Kingdom.class);
        String flag = "";
        when(kingdom.hasFlag(flag)).thenReturn(false);
        KingdomHasFlagValidation kingdomHasFlagValidation = new KingdomHasFlagValidation(kingdom, flag);
        assertEquals(false, kingdomHasFlagValidation.isValid());

    }
    @Test
    public void isInvalid() {
    }
}