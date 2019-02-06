package net.zwet.publickingdom.validation;

import net.zwet.publickingdom.objects.Playerdata;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HasHomeValidationTest {

    @Test
    public void testIsValidSucceed() {
        Playerdata playerdata = mock(Playerdata.class);
        when(playerdata.hasHome()).thenReturn(true);
        HasHomeValidation hasHomeValidation = new HasHomeValidation(playerdata);
        assertEquals(true, hasHomeValidation.isValid());


    }

    @Test
    public void testIsValidFail(){
        Playerdata playerdata = mock(Playerdata.class);
        when(playerdata.hasHome()).thenReturn(false);
        HasHomeValidation hasHomeValidation = new HasHomeValidation(playerdata);
        assertEquals(false, hasHomeValidation.isValid());

    }
    @Test
    public void isInvalid() {
    }
}