package net.zwet.publickingdom.validation;

import net.zwet.publickingdom.objects.Playerdata;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InKingdomValidationTest {

    @Test
    public void testIsValidSucceed() {
        Playerdata playerdata = mock(Playerdata.class);

        when(playerdata.isInKingdom()).thenReturn(true);
        InKingdomValidation inKingdomValidation = new InKingdomValidation(playerdata);
        assertEquals(true, inKingdomValidation.isValid());


    }

    @Test
    public void testIsValidFail(){
        Playerdata playerdata = mock(Playerdata.class);

        when(playerdata.isInKingdom()).thenReturn(false);
        InKingdomValidation inKingdomValidation = new InKingdomValidation(playerdata);
        assertEquals(false, inKingdomValidation.isValid());

    }

    @Test
    public void isInvalid() {
    }
}