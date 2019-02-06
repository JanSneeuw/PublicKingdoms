package net.zwet.publickingdom.validation;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.zwet.publickingdom.objects.Kingdom;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HasRegionValidationTest {

    @Test
    public void testIsValidSucceed() {
        Kingdom kingdom = mock(Kingdom.class);
        ProtectedRegion region = mock(ProtectedRegion.class);

        when(kingdom.hasRegion(region)).thenReturn(true);
        HasRegionValidation hasRegionValidation = new HasRegionValidation(kingdom, region);
        assertEquals(true, hasRegionValidation.isValid());
    }

    @Test
    public void testIsValidFail(){

        Kingdom kingdom = mock(Kingdom.class);
        ProtectedRegion region = mock(ProtectedRegion.class);

        when(kingdom.hasRegion(region)).thenReturn(false);
        HasRegionValidation hasRegionValidation = new HasRegionValidation(kingdom, region);
        assertEquals(false, hasRegionValidation.isValid());
    }

    @Test
    public void isInvalid() {
    }
}