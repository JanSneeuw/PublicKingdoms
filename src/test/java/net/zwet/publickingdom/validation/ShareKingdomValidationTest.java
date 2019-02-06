package net.zwet.publickingdom.validation;

import net.zwet.publickingdom.objects.Playerdata;
import org.bukkit.entity.Player;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ShareKingdomValidationTest {

    @Test
    public void testIsValidSucceed() {

        Playerdata playerdata = mock(Playerdata.class);
        Playerdata playerdata1 = mock(Playerdata.class);

        when(playerdata.getKingdomName()).thenReturn("Kingdom");
        when(playerdata1.getKingdomName()).thenReturn("Kingdom");

        ShareKingdomValidation kingdomValidation = new ShareKingdomValidation(playerdata, playerdata1);
        assertEquals(true, kingdomValidation.isValid());

    }

    @Test
    public void testIsValidFail(){



        Playerdata playerdata = mock(Playerdata.class);
        Playerdata playerdata1 = mock(Playerdata.class);

        when(playerdata.getKingdomName()).thenReturn("Kingdom");
        when(playerdata1.getKingdomName()).thenReturn("Kein");

        when(playerdata.getName()).thenReturn("Sven");
        when(playerdata1.getName()).thenReturn("PimIsEenNiggah");

        ShareKingdomValidation kingdomValidation = new ShareKingdomValidation(playerdata, playerdata1);
        assertEquals(false, kingdomValidation.isValid());


    }

    @Test
    public void isInvalid() {
    }
}