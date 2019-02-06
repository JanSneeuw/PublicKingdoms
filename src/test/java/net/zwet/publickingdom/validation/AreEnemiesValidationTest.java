package net.zwet.publickingdom.validation;

import net.zwet.publickingdom.objects.Kingdom;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AreEnemiesValidationTest {

    @Test
    public void testIsValidSucceed() {
        Kingdom kingdom = mock(Kingdom.class);
        Kingdom enemykingdom = mock(Kingdom.class);
        when(kingdom.isEnemyWith(enemykingdom)).thenReturn(true);
        AreEnemiesValidation areEnemiesValidation = new AreEnemiesValidation(kingdom, enemykingdom);
        assertEquals(true, areEnemiesValidation.isValid());

    }

    @Test
    public void testIsValidFail(){
        Kingdom kingdom = mock(Kingdom.class);
        Kingdom enemykingdom = mock(Kingdom.class);
        when(kingdom.isEnemyWith(enemykingdom)).thenReturn(false);
        AreEnemiesValidation areEnemiesValidation = new AreEnemiesValidation(kingdom, enemykingdom);
        assertEquals(false, areEnemiesValidation.isValid());

    }
    @Test
    public void isInvalid() {
    }
}