package net.zwet.publickingdom.validation;

import net.zwet.publickingdom.objects.Playerdata;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class IsHigherValidationTest {

    @Test
    public void testIsValidSucceed() {
        Playerdata playerdata = mock(Playerdata.class);
        String rank = "";
        when(playerdata.isHigherThan(rank)).thenReturn(true);
        IsHigherValidation isHigherValidation = new IsHigherValidation(playerdata).rank(rank);
        assertEquals(true, isHigherValidation.isValid());
    }

    @Test
    public void testIsValidFail(){
        Playerdata playerdata = mock(Playerdata.class);
        String rank = "";
        when(playerdata.isHigherThan(rank)).thenReturn(false);
        IsHigherValidation isHigherValidation = new IsHigherValidation(playerdata).rank(rank);
        assertEquals(false, isHigherValidation.isValid());
    }


    @Test
    public void isInvalid() {
    }
}