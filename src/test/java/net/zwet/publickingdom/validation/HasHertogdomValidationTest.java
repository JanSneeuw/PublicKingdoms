package net.zwet.publickingdom.validation;

import net.zwet.publickingdom.objects.Kingdom;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HasHertogdomValidationTest {

    @Test
    public void testIsValidSucceed() {

        Kingdom kingdom = mock(Kingdom.class);
        when(kingdom.hasHertogdom()).thenReturn(true);
        HasHertogdomValidation hasHertogdomValidation = new HasHertogdomValidation(kingdom);
        assertEquals(true, hasHertogdomValidation.isValid());
    }

    @Test
    public void testIsValidFail(){

        Kingdom kingdom = mock(Kingdom.class);
        when(kingdom.hasHertogdom()).thenReturn(false);
        HasHertogdomValidation hasHertogdomValidation = new HasHertogdomValidation(kingdom);
        assertEquals(false, hasHertogdomValidation.isValid());
    }

    @Test
    public void isInvalid() {
    }
}