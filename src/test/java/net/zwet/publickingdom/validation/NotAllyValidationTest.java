package net.zwet.publickingdom.validation;

import net.zwet.publickingdom.objects.Kingdom;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NotAllyValidationTest {

    @Test
    public void testIsValidSucceed() {
        Kingdom kingdom = mock(Kingdom.class);
        Kingdom kingdom1 = mock(Kingdom.class);
        when(kingdom.isAllyWith(kingdom1)).thenReturn(true);
        NotAllyValidation notAllyValidation = new NotAllyValidation(kingdom, kingdom1);
        assertEquals(false, notAllyValidation.isValid());

    }

    @Test
    public void testIsValidFail(){
        Kingdom kingdom = mock(Kingdom.class);
        Kingdom kingdom1 = mock(Kingdom.class);
        when(kingdom.isAllyWith(kingdom1)).thenReturn(false);
        NotAllyValidation notAllyValidation = new NotAllyValidation(kingdom, kingdom1);
        assertEquals(true, notAllyValidation.isValid());
    }

    @Test
    public void isInvalid() {
    }
}