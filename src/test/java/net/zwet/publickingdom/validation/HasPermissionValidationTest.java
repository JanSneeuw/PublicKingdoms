package net.zwet.publickingdom.validation;

import net.zwet.publickingdom.objects.Playerdata;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.junit.Test;

import java.io.File;
import java.util.logging.Logger;

import static org.bukkit.Bukkit.getPluginManager;
import static org.bukkit.Bukkit.getServer;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HasPermissionValidationTest {

    @Test
    public void testIsValidSucceed() {
        /*Playerdata playerdata = mock(Playerdata.class);
        Player player = mock(Player.class);
        String permission = "";
        when(playerdata.hasPermission(permission)).thenReturn(true);

        final Server server = mock(Server.class);
        when(server.getLogger()).thenReturn(Logger.getGlobal());
        when(server.getName()).thenReturn("");
        when(server.getVersion()).thenReturn("");
        when(server.getBukkitVersion()).thenReturn("");
        Bukkit.setServer(server);

        PluginManager pluginManager = mock(PluginManager.class);
        when(getServer().getPluginManager()).thenReturn(pluginManager);

        Plugin plugin = mock(Plugin.class);
        when(pluginManager.getPlugin("")).thenReturn(plugin);

        File mockFile = mock(File.class);
        when(plugin.getDataFolder()).thenReturn(mockFile);


        HasPermissionValidation hasPermissionValidation = new HasPermissionValidation(player, permission);
        assertEquals(true, hasPermissionValidation.isValid());

        */
    }

    @Test
    public void isInvalid() {
    }
}