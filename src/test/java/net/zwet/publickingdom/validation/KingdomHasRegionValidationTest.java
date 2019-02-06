package net.zwet.publickingdom.validation;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.zwet.publickingdom.objects.Kingdom;
import org.junit.Test;

import javax.swing.plaf.synth.Region;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class KingdomHasRegionValidationTest {

    @Test
    public void testIsValidSucceed() {
        Kingdom kingdom = mock(Kingdom.class);
        ProtectedRegion region = mock(ProtectedRegion.class);
        when(kingdom.getRegion()).thenReturn(region);
        KingdomHasRegionValidation kingdomHasRegionValidation = new KingdomHasRegionValidation(kingdom);
        assertEquals(true, kingdomHasRegionValidation.isValid());
    }

    @Test
    public void testIsValidFail(){
        Kingdom kingdom = mock(Kingdom.class);

        when(kingdom.getRegion()).thenReturn(null);
        KingdomHasRegionValidation kingdomHasRegionValidation = new KingdomHasRegionValidation(kingdom);
        assertEquals(false, kingdomHasRegionValidation.isValid());
    }

    @Test
    public void isInvalid() {
    }
}