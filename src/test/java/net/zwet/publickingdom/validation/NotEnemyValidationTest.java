package net.zwet.publickingdom.validation;

import net.zwet.publickingdom.objects.Kingdom;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NotEnemyValidationTest {

    @Test
    public void testIsValidSucceed() {
        Kingdom kingdom = mock(Kingdom.class);
        Kingdom kingdom1 = mock(Kingdom.class);
        when(kingdom.isEnemyWith(kingdom1)).thenReturn(true);
        NotEnemyValidation notEnemyValidation = new NotEnemyValidation(kingdom, kingdom1);
        assertEquals(false, notEnemyValidation.isValid());


    }

    @Test
    public void testIsValidFail(){
        Kingdom kingdom = mock(Kingdom.class);
        Kingdom kingdom1 = mock(Kingdom.class);
        when(kingdom.isEnemyWith(kingdom1)).thenReturn(false);
        NotEnemyValidation notEnemyValidation = new NotEnemyValidation(kingdom, kingdom1);
        assertEquals(true, notEnemyValidation.isValid());

    }

    @Test
    public void isInvalid() {
    }
}