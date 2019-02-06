package net.zwet.publickingdom.events;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.zwet.publickingdom.PublicKingdom;
import net.zwet.publickingdom.objects.Kingdom;
import net.zwet.publickingdom.objects.Playerdata;
import net.zwet.publickingdom.validation.InKingdomValidation;
import net.zwet.publickingdom.validation.InRegionValidation;
import net.zwet.publickingdom.validation.Validator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathMessage implements Listener {
    PublicKingdom plugin;

    public DeathMessage(PublicKingdom instance) {
        plugin = instance;
    }

    @EventHandler
    public void cancelDeathMessage(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Playerdata playerdata = new Playerdata(player);
        EntityDamageEvent damegeevent = player.getLastDamageCause();
        EntityDamageEvent.DamageCause damageCause = damegeevent.getCause();
        String fireprefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().get("Message-Prefix").toString());
        if (playerdata.exists()) {
            if (playerdata.isInKingdom()) {
                Kingdom kingdom = new Kingdom(player);
                if (kingdom.exists()) {
                    if (damageCause == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
                        event.setDeathMessage(fireprefix + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + " " + player.getName() + ChatColor.GRAY + " is opgeblazen!");
                    } else if (damageCause == EntityDamageEvent.DamageCause.DROWNING) {
                        event.setDeathMessage(fireprefix + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + " " + player.getName() + ChatColor.GRAY + " is verdronken!");
                    } else if (damageCause == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
                        event.setDeathMessage(fireprefix + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + " " + player.getName() + ChatColor.GRAY + " is opgeblazen!");
                    } else if (damageCause == EntityDamageEvent.DamageCause.CONTACT) {
                        event.setDeathMessage(fireprefix + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + " " + player.getName() + ChatColor.GRAY + " is dood gegaan door een block!");
                    } else if (damageCause == EntityDamageEvent.DamageCause.FALL) {
                        event.setDeathMessage(fireprefix + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + " " + player.getName() + ChatColor.GRAY + " is dood gevallen!");
                    } else if (damageCause == EntityDamageEvent.DamageCause.FALLING_BLOCK) {
                        event.setDeathMessage(fireprefix + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + " " + player.getName() + ChatColor.GRAY + " is dood gegaan door een vallend!");
                    } else if (damageCause == EntityDamageEvent.DamageCause.FIRE) {
                        event.setDeathMessage(fireprefix + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + " " + player.getName() + ChatColor.GRAY + " is verbrand!");
                    } else if (damageCause == EntityDamageEvent.DamageCause.FIRE_TICK) {
                        event.setDeathMessage(fireprefix + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + " " + player.getName() + ChatColor.GRAY + " is verbrand!");
                    } else if (damageCause == EntityDamageEvent.DamageCause.LAVA) {
                        event.setDeathMessage(fireprefix + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + " " + player.getName() + ChatColor.GRAY + " is dood gegaan door in de lava te zwemmen!");
                    } else if (damageCause == EntityDamageEvent.DamageCause.LIGHTNING) {
                        event.setDeathMessage(fireprefix + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + " " + player.getName() + ChatColor.GRAY + " is dood gegaan door de bliksem!");
                    } else if (damageCause == EntityDamageEvent.DamageCause.MAGIC) {
                        event.setDeathMessage(fireprefix + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + " " + player.getName() + ChatColor.GRAY + " is dood gegaan door magic!");
                    } else if (damageCause == EntityDamageEvent.DamageCause.POISON) {
                        event.setDeathMessage(fireprefix + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + " " + player.getName() + ChatColor.GRAY + " is dood gegaan door het poison effect!");
                    } else if (damageCause == EntityDamageEvent.DamageCause.STARVATION) {
                        event.setDeathMessage(fireprefix + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + " " + player.getName() + ChatColor.GRAY + " is omgekomen van de honger!");
                    } else if (damageCause == EntityDamageEvent.DamageCause.SUFFOCATION) {
                        event.setDeathMessage(fireprefix + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + " " + player.getName() + ChatColor.GRAY + " is gestikt in een muur!");
                    } else if (damageCause == EntityDamageEvent.DamageCause.THORNS) {
                        event.setDeathMessage(fireprefix + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + " " + player.getName() + ChatColor.GRAY + " is dood gegaan door thorns!");
                    } else if (damageCause == EntityDamageEvent.DamageCause.VOID) {
                        event.setDeathMessage(fireprefix + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + " " + player.getName() + ChatColor.GRAY + " is in de void gevallen");
                    } else if (damageCause == EntityDamageEvent.DamageCause.WITHER) {
                        event.setDeathMessage(fireprefix + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + " " + player.getName() + ChatColor.GRAY + " is dood gegaan door het wither effect!");
                    } else {
                        event.setDeathMessage(null);
                    }
                }
            } else {
                if (damageCause == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
                    event.setDeathMessage(fireprefix + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + " " + player.getName() + ChatColor.GRAY + " is opgeblazen!");
                } else if (damageCause == EntityDamageEvent.DamageCause.DROWNING) {
                    event.setDeathMessage(fireprefix + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + " " + player.getName() + ChatColor.GRAY + " is verdronken!");
                } else if (damageCause == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
                    event.setDeathMessage(fireprefix + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + " " + player.getName() + ChatColor.GRAY + " is opgeblazen!");
                } else if (damageCause == EntityDamageEvent.DamageCause.CONTACT) {
                    event.setDeathMessage(fireprefix + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + " " + player.getName() + ChatColor.GRAY + " is dood gegaan door een block!");
                } else if (damageCause == EntityDamageEvent.DamageCause.FALL) {
                    event.setDeathMessage(fireprefix + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + " " + player.getName() + ChatColor.GRAY + " is dood gevallen!");
                } else if (damageCause == EntityDamageEvent.DamageCause.FALLING_BLOCK) {
                    event.setDeathMessage(fireprefix + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + " " + player.getName() + ChatColor.GRAY + " is dood gegaan door een vallend!");
                } else if (damageCause == EntityDamageEvent.DamageCause.FIRE) {
                    event.setDeathMessage(fireprefix + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + " " + player.getName() + ChatColor.GRAY + " is verbrand!");
                } else if (damageCause == EntityDamageEvent.DamageCause.FIRE_TICK) {
                    event.setDeathMessage(fireprefix + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + " " + player.getName() + ChatColor.GRAY + " is verbrand!");
                } else if (damageCause == EntityDamageEvent.DamageCause.LAVA) {
                    event.setDeathMessage(fireprefix + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + " " + player.getName() + ChatColor.GRAY + " is dood gegaan door in de lava te zwemmen!");
                } else if (damageCause == EntityDamageEvent.DamageCause.LIGHTNING) {
                    event.setDeathMessage(fireprefix + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + " " + player.getName() + ChatColor.GRAY + " is dood gegaan door de bliksem!");
                } else if (damageCause == EntityDamageEvent.DamageCause.MAGIC) {
                    event.setDeathMessage(fireprefix + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + " " + player.getName() + ChatColor.GRAY + " is dood gegaan door magic!");
                } else if (damageCause == EntityDamageEvent.DamageCause.POISON) {
                    event.setDeathMessage(fireprefix + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + " " + player.getName() + ChatColor.GRAY + " is dood gegaan door het poison effect!");
                } else if (damageCause == EntityDamageEvent.DamageCause.STARVATION) {
                    event.setDeathMessage(fireprefix + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + " " + player.getName() + ChatColor.GRAY + " is omgekomen van de honger!");
                } else if (damageCause == EntityDamageEvent.DamageCause.SUFFOCATION) {
                    event.setDeathMessage(fireprefix + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + " " + player.getName() + ChatColor.GRAY + " is gestikt in een muur!");
                } else if (damageCause == EntityDamageEvent.DamageCause.THORNS) {
                    event.setDeathMessage(fireprefix + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + " " + player.getName() + ChatColor.GRAY + " is dood gegaan door thorns!");
                } else if (damageCause == EntityDamageEvent.DamageCause.VOID) {
                    event.setDeathMessage(fireprefix + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + " " + player.getName() + ChatColor.GRAY + " is in de void gevallen");
                } else if (damageCause == EntityDamageEvent.DamageCause.WITHER) {
                    event.setDeathMessage(fireprefix + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + " " + player.getName() + ChatColor.GRAY + " is dood gegaan door het wither effect!");
                } else {
                    event.setDeathMessage(null);
                }
            }
        }
    }
    @EventHandler
    public void sendDeathMessage(EntityDamageByEntityEvent event) {
        String fireprefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().get("Message-Prefix").toString());
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            Validator deathMessageValidator = new Validator().addValidation(new InKingdomValidation(player)).addValidation(new InRegionValidation(player, player.getWorld()));
            Playerdata playerdata = new Playerdata(player);
            if (playerdata.exists()) {
                if (playerdata.isInKingdom()) {
                    Kingdom kingdom = new Kingdom(player);
                    if (kingdom.exists()) {
                        if (player.getHealth() <= event.getFinalDamage()) {
                            if (event.getDamager() instanceof Zombie) {
                                for (Player players : Bukkit.getOnlinePlayers()) {
                                    players.sendMessage(fireprefix + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door een zombie!");
                                }
                            } else if (event.getDamager() instanceof Player) {
                                Player killer = (Player) event.getDamager();
                                Playerdata killerdata = new Playerdata(killer);
                                if (killerdata.exists()) {
                                    if (killerdata.isInKingdom()) {
                                        Kingdom killerkingdom = new Kingdom(killer);
                                        if (killerkingdom.exists()) {
                                            if (WGBukkit.getRegionManager(player.getWorld()).getApplicableRegions(player.getLocation()).size() != 0) {
                                                for (ProtectedRegion kingdomRegion : WGBukkit.getRegionManager(player.getWorld()).getApplicableRegions(player.getLocation())) {
                                                    if (!kingdomRegion.getId().equalsIgnoreCase(kingdom.getRegion().getId()) || !kingdom.hasFlag("enemy-hit")) {
                                                        if (!playerdata.getKingdomName().equals(killerdata.getKingdomName())) {
                                                            for (Player players : Bukkit.getOnlinePlayers()) {
                                                                if (killer.getItemInHand() != null) {
                                                                    if (killer.getItemInHand().hasItemMeta()) {
                                                                        if (killer.getItemInHand().getItemMeta().hasDisplayName()) {
                                                                            players.sendMessage(fireprefix + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door " + ChatColor.WHITE + "[" + killerkingdom.getColoredName() + ChatColor.WHITE + "][" + killerkingdom.getColoredRank(killerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + killer.getName() + ChatColor.GRAY + " met " + killer.getItemInHand().getItemMeta().getDisplayName());
                                                                        } else {
                                                                            players.sendMessage(fireprefix + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door " + ChatColor.WHITE + "[" + killerkingdom.getColoredName() + ChatColor.WHITE + "][" + killerkingdom.getColoredRank(killerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + killer.getName() + ChatColor.GRAY + "!");
                                                                        }
                                                                    } else {
                                                                        players.sendMessage(fireprefix + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door " + ChatColor.WHITE + "[" + killerkingdom.getColoredName() + ChatColor.WHITE + "][" + killerkingdom.getColoredRank(killerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + killer.getName() + ChatColor.GRAY + "!");
                                                                    }
                                                                } else {
                                                                    players.sendMessage(fireprefix + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door " + ChatColor.WHITE + "[" + killerkingdom.getColoredName() + ChatColor.WHITE + "][" + killerkingdom.getColoredRank(killerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + killer.getName() + ChatColor.GRAY + "!");
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            } else {
                                                if (!playerdata.getKingdomName().equals(killerdata.getKingdomName())) {
                                                    for (Player players : Bukkit.getOnlinePlayers()) {
                                                        if (killer.getItemInHand() != null) {
                                                            if (killer.getItemInHand().hasItemMeta()) {
                                                                if (killer.getItemInHand().getItemMeta().hasDisplayName()) {
                                                                    players.sendMessage(fireprefix + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door " + ChatColor.WHITE + "[" + killerkingdom.getColoredName() + ChatColor.WHITE + "][" + killerkingdom.getColoredRank(killerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + killer.getName() + ChatColor.GRAY + " met " + killer.getItemInHand().getItemMeta().getDisplayName());
                                                                } else {
                                                                    players.sendMessage(fireprefix + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door " + ChatColor.WHITE + "[" + killerkingdom.getColoredName() + ChatColor.WHITE + "][" + killerkingdom.getColoredRank(killerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + killer.getName() + ChatColor.GRAY + "!");
                                                                }
                                                            } else {
                                                                players.sendMessage(fireprefix + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door " + ChatColor.WHITE + "[" + killerkingdom.getColoredName() + ChatColor.WHITE + "][" + killerkingdom.getColoredRank(killerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + killer.getName() + ChatColor.GRAY + "!");
                                                            }
                                                        } else {
                                                            players.sendMessage(fireprefix + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door " + ChatColor.WHITE + "[" + killerkingdom.getColoredName() + ChatColor.WHITE + "][" + killerkingdom.getColoredRank(killerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + killer.getName() + ChatColor.GRAY + "!");
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        if (WGBukkit.getRegionManager(player.getWorld()).getApplicableRegions(player.getLocation()).size() != 0) {
                                            for (ProtectedRegion kingdomRegion : WGBukkit.getRegionManager(player.getWorld()).getApplicableRegions(player.getLocation())) {
                                                if (!kingdomRegion.getId().equalsIgnoreCase(kingdom.getRegion().getId()) || !kingdom.hasFlag("enemy-hit")) {
                                                    if (!playerdata.getKingdomName().equals(killerdata.getKingdomName())) {
                                                        for (Player players : Bukkit.getOnlinePlayers()) {
                                                            if (killer.getItemInHand() != null) {
                                                                if (killer.getItemInHand().hasItemMeta()) {
                                                                    if (killer.getItemInHand().getItemMeta().hasDisplayName()) {
                                                                        players.sendMessage(fireprefix + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door " + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + killer.getName() + ChatColor.GRAY + " met " + killer.getItemInHand().getItemMeta().getDisplayName());
                                                                    } else {
                                                                        players.sendMessage(fireprefix + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door " + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + killer.getName() + ChatColor.GRAY + "!");
                                                                    }
                                                                } else {
                                                                    players.sendMessage(fireprefix + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door " + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + killer.getName() + ChatColor.GRAY + "!");
                                                                }
                                                            } else {
                                                                players.sendMessage(fireprefix + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door " + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + killer.getName() + ChatColor.GRAY + "!");
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        } else {
                                            if (!playerdata.getKingdomName().equals(killerdata.getKingdomName())) {
                                                for (Player players : Bukkit.getOnlinePlayers()) {
                                                    if (killer.getItemInHand() != null) {
                                                        if (killer.getItemInHand().hasItemMeta()) {
                                                            if (killer.getItemInHand().getItemMeta().hasDisplayName()) {
                                                                players.sendMessage(fireprefix + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door " + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + killer.getName() + ChatColor.GRAY + " met " + killer.getItemInHand().getItemMeta().getDisplayName());
                                                            } else {
                                                                players.sendMessage(fireprefix + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door " + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + killer.getName() + ChatColor.GRAY + "!");
                                                            }
                                                        } else {
                                                            players.sendMessage(fireprefix + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door " + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + killer.getName() + ChatColor.GRAY + "!");
                                                        }
                                                    } else {
                                                        players.sendMessage(fireprefix + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door " + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + killer.getName() + ChatColor.GRAY + "!");
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            } else if (event.getDamager() instanceof Skeleton) {
                                for (Player players : Bukkit.getOnlinePlayers()) {
                                    players.sendMessage(fireprefix + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door een skeleton!");
                                }
                            } else if (event.getDamager() instanceof Spider) {
                                for (Player players : Bukkit.getOnlinePlayers()) {
                                    players.sendMessage(fireprefix + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door een spider!");
                                }
                            } else if (event.getDamager() instanceof Slime) {
                                for (Player players : Bukkit.getOnlinePlayers()) {
                                    players.sendMessage(fireprefix + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door een slime!");
                                }
                            } else if (event.getDamager() instanceof Ghast) {
                                for (Player players : Bukkit.getOnlinePlayers()) {
                                    players.sendMessage(fireprefix + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door een ghast!");
                                }
                            } else if (event.getDamager() instanceof PigZombie) {
                                for (Player players : Bukkit.getOnlinePlayers()) {
                                    players.sendMessage(fireprefix + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door een pigman!");
                                }
                            } else if (event.getDamager() instanceof Enderman) {
                                for (Player players : Bukkit.getOnlinePlayers()) {
                                    players.sendMessage(fireprefix + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door een enderman!");
                                }
                            } else if (event.getDamager() instanceof CaveSpider) {
                                for (Player players : Bukkit.getOnlinePlayers()) {
                                    players.sendMessage(fireprefix + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door een cave spider!");
                                }
                            } else if (event.getDamager() instanceof Blaze) {
                                for (Player players : Bukkit.getOnlinePlayers()) {
                                    players.sendMessage(fireprefix + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door een blaze!");
                                }
                            } else if (event.getDamager() instanceof MagmaCube) {
                                for (Player players : Bukkit.getOnlinePlayers()) {
                                    players.sendMessage(fireprefix + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door een magma cube!");
                                }
                            } else if (event.getDamager() instanceof Silverfish) {
                                for (Player players : Bukkit.getOnlinePlayers()) {
                                    players.sendMessage(fireprefix + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door een silverfish!");
                                }
                            } else if (event.getDamager() instanceof EnderDragon) {
                                for (Player players : Bukkit.getOnlinePlayers()) {
                                    players.sendMessage(fireprefix + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door de ender dragon!");
                                }
                            } else if (event.getDamager() instanceof Guardian) {
                                for (Player players : Bukkit.getOnlinePlayers()) {
                                    players.sendMessage(fireprefix + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door een guardian!");
                                }
                            } else if (event.getDamager() instanceof Wolf) {
                                for (Player players : Bukkit.getOnlinePlayers()) {
                                    players.sendMessage(fireprefix + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door een wolf!");
                                }
                            } else if (event.getDamager() instanceof IronGolem) {
                                for (Player players : Bukkit.getOnlinePlayers()) {
                                    players.sendMessage(fireprefix + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door een iron golem!");
                                }
                            } else if (event.getDamager() instanceof Arrow) {
                                Arrow arrow = (Arrow) event.getDamager();
                                if (arrow.getShooter() instanceof Player) {
                                    Player killer = (Player) arrow.getShooter();
                                    Playerdata killerdata = new Playerdata(killer);
                                    if (killerdata.isInKingdom()) {
                                        Kingdom killerkingdomdata = new Kingdom(killer);
                                        if (killerkingdomdata.exists()) {
                                            if (playerdata.getKingdomName().equalsIgnoreCase(killerdata.getKingdomName())) {
                                                if (WGBukkit.getRegionManager(Bukkit.getWorld(plugin.getConfig().getString("Kingdom-World"))).getApplicableRegions(player.getLocation()).size() != 0) {
                                                    for (ProtectedRegion kingdomRegion : WGBukkit.getRegionManager(Bukkit.getWorld(plugin.getConfig().getString("Kingdom-World"))).getApplicableRegions(player.getLocation())) {
                                                        if (!kingdomRegion.getId().equalsIgnoreCase(kingdom.getRegion().getId()) || !kingdom.hasFlag("enemy-hit")) {
                                                            for (Player players : Bukkit.getOnlinePlayers()) {
                                                                if (killer.getItemInHand() != null) {
                                                                    if (killer.getItemInHand().hasItemMeta()) {
                                                                        if (killer.getItemInHand().getItemMeta().hasDisplayName()) {
                                                                            players.sendMessage(fireprefix + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door " + ChatColor.WHITE + "[" + killerkingdomdata.getColoredName() + ChatColor.WHITE + "][" + killerkingdomdata.getColoredRank(killerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + killer.getName() + ChatColor.GRAY + " met " + killer.getItemInHand().getItemMeta().getDisplayName());
                                                                        } else {
                                                                            players.sendMessage(fireprefix + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door " + ChatColor.WHITE + "[" + killerkingdomdata.getColoredName() + ChatColor.WHITE + "][" + killerkingdomdata.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + killer.getName() + ChatColor.GRAY + "!");
                                                                        }
                                                                    } else {
                                                                        players.sendMessage(fireprefix + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door " + ChatColor.WHITE + "[" + killerkingdomdata.getColoredName() + ChatColor.WHITE + "][" + killerkingdomdata.getColoredRank(killerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + killer.getName() + ChatColor.GRAY + "!");
                                                                    }
                                                                } else {
                                                                    players.sendMessage(fireprefix + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door " + ChatColor.WHITE + "[" + killerkingdomdata.getColoredName() + ChatColor.WHITE + "][" + killerkingdomdata.getColoredRank(killerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + killer.getName() + ChatColor.GRAY + "!");
                                                                }
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    for (Player players : Bukkit.getOnlinePlayers()) {
                                                        if (killer.getItemInHand() != null) {
                                                            if (killer.getItemInHand().hasItemMeta()) {
                                                                if (killer.getItemInHand().getItemMeta().hasDisplayName()) {
                                                                    players.sendMessage(fireprefix + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door " + ChatColor.WHITE + "[" + killerkingdomdata.getColoredName() + ChatColor.WHITE + "][" + killerkingdomdata.getColoredRank(killerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + killer.getName() + ChatColor.GRAY + " met " + killer.getItemInHand().getItemMeta().getDisplayName());
                                                                } else {
                                                                    players.sendMessage(fireprefix + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door " + ChatColor.WHITE + "[" + killerkingdomdata.getColoredName() + ChatColor.WHITE + "][" + killerkingdomdata.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + killer.getName() + ChatColor.GRAY + "!");
                                                                }
                                                            } else {
                                                                players.sendMessage(fireprefix + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door " + ChatColor.WHITE + "[" + killerkingdomdata.getColoredName() + ChatColor.WHITE + "][" + killerkingdomdata.getColoredRank(killerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + killer.getName() + ChatColor.GRAY + "!");
                                                            }
                                                        } else {
                                                            players.sendMessage(fireprefix + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door " + ChatColor.WHITE + "[" + killerkingdomdata.getColoredName() + ChatColor.WHITE + "][" + killerkingdomdata.getColoredRank(killerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + killer.getName() + ChatColor.GRAY + "!");
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        if (!playerdata.getKingdomName().equalsIgnoreCase(killerdata.getKingdomName())) {
                                            if (WGBukkit.getRegionManager(Bukkit.getWorld(plugin.getConfig().getString("Kingdom-World"))).getApplicableRegions(player.getLocation()).size() != 0) {
                                                for (ProtectedRegion kingdomRegion : WGBukkit.getRegionManager(Bukkit.getWorld(plugin.getConfig().getString("Kingdom-World"))).getApplicableRegions(player.getLocation())) {
                                                    if (!kingdomRegion.getId().equalsIgnoreCase(kingdom.getRegion().getId()) || !kingdom.hasFlag("enemy-hit")) {
                                                        for (Player players : Bukkit.getOnlinePlayers()) {
                                                            if (killer.getItemInHand() != null) {
                                                                if (killer.getItemInHand().hasItemMeta()) {
                                                                    if (killer.getItemInHand().getItemMeta().hasDisplayName()) {
                                                                        players.sendMessage(fireprefix + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door " + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + killer.getName() + ChatColor.GRAY + " met " + killer.getItemInHand().getItemMeta().getDisplayName());
                                                                    } else {
                                                                        players.sendMessage(fireprefix + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door " + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + killer.getName() + ChatColor.GRAY + "!");
                                                                    }
                                                                } else {
                                                                    players.sendMessage(fireprefix + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door " + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + killer.getName() + ChatColor.GRAY + "!");
                                                                }
                                                            } else {
                                                                players.sendMessage(fireprefix + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door " + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + killer.getName() + ChatColor.GRAY + "!");
                                                            }
                                                        }
                                                    }
                                                }
                                            } else {
                                                for (Player players : Bukkit.getOnlinePlayers()) {
                                                    if (killer.getItemInHand() != null) {
                                                        if (killer.getItemInHand().hasItemMeta()) {
                                                            if (killer.getItemInHand().getItemMeta().hasDisplayName()) {
                                                                players.sendMessage(fireprefix + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door " + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + killer.getName() + ChatColor.GRAY + " met " + killer.getItemInHand().getItemMeta().getDisplayName());
                                                            } else {
                                                                players.sendMessage(fireprefix + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door " + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + killer.getName() + ChatColor.GRAY + "!");
                                                            }
                                                        } else {
                                                            players.sendMessage(fireprefix + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door " + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + killer.getName() + ChatColor.GRAY + "!");
                                                        }
                                                    } else {
                                                        players.sendMessage(fireprefix + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door " + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + killer.getName() + ChatColor.GRAY + "!");
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    if (player.getHealth() <= event.getFinalDamage()) {
                        if (event.getDamager() instanceof Zombie) {
                            for (Player players : Bukkit.getOnlinePlayers()) {
                                players.sendMessage(fireprefix + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door een zombie!");
                            }
                        } else if (event.getDamager() instanceof Player) {
                            Player killer = (Player) event.getDamager();
                            Playerdata killerdata = new Playerdata(killer);
                            if (killerdata.isInKingdom()) {
                                Kingdom killerkingdom = new Kingdom(killer);
                                if (killerkingdom.exists()) {
                                    for (Player players : Bukkit.getOnlinePlayers()) {
                                        if (killer.getItemInHand() != null) {
                                            if (killer.getItemInHand().hasItemMeta()) {
                                                if (killer.getItemInHand().getItemMeta().hasDisplayName()) {
                                                    players.sendMessage(fireprefix + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door " + ChatColor.WHITE + "[" + killerkingdom.getColoredName() + ChatColor.WHITE + "][" + killerkingdom.getColoredRank(playerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + killer.getName() + ChatColor.GRAY + " met " + killer.getItemInHand().getItemMeta().getDisplayName());
                                                } else {
                                                    players.sendMessage(fireprefix + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door " + ChatColor.WHITE + "[" + killerkingdom.getColoredName() + ChatColor.WHITE + "][" + killerkingdom.getColoredRank(killerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + killer.getName() + ChatColor.GRAY + "!");
                                                }
                                            } else {
                                                players.sendMessage(fireprefix + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door " + ChatColor.WHITE + "[" + killerkingdom.getColoredName() + ChatColor.WHITE + "][" + killerkingdom.getColoredRank(killerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + killer.getName() + ChatColor.GRAY + "!");
                                            }
                                        } else {
                                            players.sendMessage(fireprefix + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door " + ChatColor.WHITE + "[" + killerkingdom.getColoredName() + ChatColor.WHITE + "][" + killerkingdom.getColoredRank(killerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + killer.getName() + ChatColor.GRAY + "!");
                                        }
                                    }

                                }
                            } else {
                                for (Player players : Bukkit.getOnlinePlayers()) {
                                    if (killer.getItemInHand() != null) {
                                        if (killer.getItemInHand().hasItemMeta()) {
                                            if (killer.getItemInHand().getItemMeta().hasDisplayName()) {
                                                players.sendMessage(fireprefix + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door " + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + killer.getName() + ChatColor.GRAY + " met " + killer.getItemInHand().getItemMeta().getDisplayName());
                                            } else {
                                                players.sendMessage(fireprefix + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door " + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + killer.getName() + ChatColor.GRAY + "!");
                                            }
                                        } else {
                                            players.sendMessage(fireprefix + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door " + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + killer.getName() + ChatColor.GRAY + "!");
                                        }
                                    } else {
                                        players.sendMessage(fireprefix + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door " + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + killer.getName() + ChatColor.GRAY + "!");
                                    }
                                }
                            }


                        }else if (event.getDamager() instanceof Skeleton) {
                            for (Player players : Bukkit.getOnlinePlayers()) {
                                players.sendMessage(fireprefix + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door een skeleton!");
                            }
                        } else if (event.getDamager() instanceof Spider) {
                            for (Player players : Bukkit.getOnlinePlayers()) {
                                players.sendMessage(fireprefix + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door een spider!");
                            }
                        } else if (event.getDamager() instanceof Slime) {
                            for (Player players : Bukkit.getOnlinePlayers()) {
                                players.sendMessage(fireprefix + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door een slime!");
                            }
                        } else if (event.getDamager() instanceof Ghast) {
                            for (Player players : Bukkit.getOnlinePlayers()) {
                                players.sendMessage(fireprefix + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door een ghast!");
                            }
                        } else if (event.getDamager() instanceof PigZombie) {
                            for (Player players : Bukkit.getOnlinePlayers()) {
                                players.sendMessage(fireprefix + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door een pigman!");
                            }
                        } else if (event.getDamager() instanceof Enderman) {
                            for (Player players : Bukkit.getOnlinePlayers()) {
                                players.sendMessage(fireprefix + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door een enderman!");
                            }
                        } else if (event.getDamager() instanceof CaveSpider) {
                            for (Player players : Bukkit.getOnlinePlayers()) {
                                players.sendMessage(fireprefix + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door een cave spider!");
                            }
                        } else if (event.getDamager() instanceof Blaze) {
                            for (Player players : Bukkit.getOnlinePlayers()) {
                                players.sendMessage(fireprefix + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door een blaze!");
                            }
                        } else if (event.getDamager() instanceof MagmaCube) {
                            for (Player players : Bukkit.getOnlinePlayers()) {
                                players.sendMessage(fireprefix + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door een magma cube!");
                            }
                        } else if (event.getDamager() instanceof Silverfish) {
                            for (Player players : Bukkit.getOnlinePlayers()) {
                                players.sendMessage(fireprefix + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door een silverfish!");
                            }
                        } else if (event.getDamager() instanceof EnderDragon) {
                            for (Player players : Bukkit.getOnlinePlayers()) {
                                players.sendMessage(fireprefix + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door de ender dragon!");
                            }
                        } else if (event.getDamager() instanceof Guardian) {
                            for (Player players : Bukkit.getOnlinePlayers()) {
                                players.sendMessage(fireprefix + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door een guardian!");
                            }
                        } else if (event.getDamager() instanceof Wolf) {
                            for (Player players : Bukkit.getOnlinePlayers()) {
                                players.sendMessage(fireprefix + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door een wolf!");
                            }
                        } else if (event.getDamager() instanceof IronGolem) {
                            for (Player players : Bukkit.getOnlinePlayers()) {
                                players.sendMessage(fireprefix + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door een iron golem!");
                            }
                        } else if (event.getDamager() instanceof Arrow) {
                            Arrow arrow = (Arrow) event.getDamager();
                            if (arrow.getShooter() instanceof Player) {
                                Player killer = (Player) arrow.getShooter();
                                Playerdata killerdata = new Playerdata(killer);
                                if (killerdata.isInKingdom()) {
                                    Kingdom kingdom = new Kingdom(killer);
                                    for (Player players : Bukkit.getOnlinePlayers()) {
                                            if (killer.getItemInHand() != null) {
                                                if (killer.getItemInHand().hasItemMeta()) {
                                                    if (killer.getItemInHand().getItemMeta().hasDisplayName()) {
                                                        players.sendMessage(fireprefix + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door " + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(killerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + killer.getName() + ChatColor.GRAY + " met " + killer.getItemInHand().getItemMeta().getDisplayName());
                                                    } else {
                                                        players.sendMessage(fireprefix + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door " + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(killerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + killer.getName() + ChatColor.GRAY + "!");
                                                    }
                                                } else {
                                                    players.sendMessage(fireprefix + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door " + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(killerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + killer.getName() + ChatColor.GRAY + "!");
                                                }
                                            } else {
                                                players.sendMessage(fireprefix + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door " + ChatColor.WHITE + "[" + kingdom.getColoredName() + ChatColor.WHITE + "][" + kingdom.getColoredRank(killerdata.getRank()) + ChatColor.WHITE + "]" + ChatColor.YELLOW + killer.getName() + ChatColor.GRAY + "!");
                                            }
                                        }
                                } else {
                                    for (Player players : Bukkit.getOnlinePlayers()) {
                                        if (killer.getItemInHand() != null) {
                                            if (killer.getItemInHand().hasItemMeta()) {
                                                if (killer.getItemInHand().getItemMeta().hasDisplayName()) {
                                                    players.sendMessage(fireprefix + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door " + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + killer.getName() + ChatColor.GRAY + " met " + killer.getItemInHand().getItemMeta().getDisplayName());
                                                } else {
                                                    players.sendMessage(fireprefix + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door " + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + killer.getName() + ChatColor.GRAY + "!");
                                                }
                                            } else {
                                                players.sendMessage(fireprefix + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door " + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + killer.getName() + ChatColor.GRAY + "!");
                                            }
                                        } else {
                                            players.sendMessage(fireprefix + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + player.getName() + ChatColor.GRAY + " is vermoord door " + ChatColor.WHITE + "[" + ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("Kingdomloos")) + ChatColor.WHITE + "]" + ChatColor.YELLOW + killer.getName() + ChatColor.GRAY + "!");
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}