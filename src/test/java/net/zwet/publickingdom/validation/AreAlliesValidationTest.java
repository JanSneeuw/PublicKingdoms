package net.zwet.publickingdom.validation;

import net.zwet.publickingdom.objects.Kingdom;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AreAlliesValidationTest {

    @Test
    public void testIsValidSucceed() {
        Kingdom kingdom = mock(Kingdom.class);
        Kingdom allykingdom = mock(Kingdom.class);
        when(kingdom.isAllyWith(allykingdom)).thenReturn(true);
        AreAlliesValidation areAlliesValidation = new AreAlliesValidation(kingdom, allykingdom);
        assertEquals(true, areAlliesValidation.isValid());

    }
    @Test
    public void testIsValidFailed(){
        Kingdom kingdom = mock(Kingdom.class);
        Kingdom allykingdom = mock(Kingdom.class);
        when(kingdom.isAllyWith(allykingdom)).thenReturn(false);
        AreAlliesValidation areAlliesValidation = new AreAlliesValidation(kingdom, allykingdom);
        assertEquals(false, areAlliesValidation.isValid());

    }

    @Test
    public void isInvalid() {
    }
}