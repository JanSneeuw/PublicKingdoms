package net.zwet.publickingdom.validation;

import net.zwet.publickingdom.objects.Kingdom;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MaxAlliesReachedValidationTest {

    @Test
    public void testIsValidSucceed() {
        Kingdom kingdom = mock(Kingdom.class);

        when(kingdom.maxAlliesReached()).thenReturn(true);
        MaxAlliesReachedValidation maxAlliesReachedValidation = new MaxAlliesReachedValidation(kingdom);
        assertEquals(false, maxAlliesReachedValidation.isValid());


    }

    @Test
    public void testIsValidFail(){

        Kingdom kingdom = mock(Kingdom.class);

        when(kingdom.maxAlliesReached()).thenReturn(false);
        MaxAlliesReachedValidation maxAlliesReachedValidation = new MaxAlliesReachedValidation(kingdom);
        assertEquals(true, maxAlliesReachedValidation.isValid());

    }

    @Test
    public void isInvalid() {
    }
}