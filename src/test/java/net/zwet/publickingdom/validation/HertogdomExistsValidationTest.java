package net.zwet.publickingdom.validation;

import net.zwet.publickingdom.objects.Kingdom;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HertogdomExistsValidationTest {

    @Test
    public void testIsValidSucceed() {
        Kingdom kingdom = mock(Kingdom.class);
        String hertogdom = "";

        when(kingdom.hertogdomExists(hertogdom)).thenReturn(true);
        HertogdomExistsValidation hertogdomExistsValidation = new HertogdomExistsValidation(kingdom, hertogdom);
        assertEquals(true, hertogdomExistsValidation.isValid());

    }

    @Test
    public void testIsValidFail(){
        Kingdom kingdom = mock(Kingdom.class);
        String hertogdom = "";

        when(kingdom.hertogdomExists(hertogdom)).thenReturn(false);
        HertogdomExistsValidation hertogdomExistsValidation = new HertogdomExistsValidation(kingdom, hertogdom);
        assertEquals(false, hertogdomExistsValidation.isValid());

    }

    @Test
    public void isInvalid() {
    }
}