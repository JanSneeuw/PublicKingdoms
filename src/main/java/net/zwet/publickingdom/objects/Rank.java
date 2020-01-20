package net.zwet.publickingdom.objects;

import net.zwet.publickingdom.Exceptions.NoSuchRankInKingdom;
import org.bukkit.entity.Player;

public class Rank {

    String rank = null;
    Kingdom kingdom = null;

    public Rank(String rank, Kingdom kingdom) throws NoSuchRankInKingdom {
        this.kingdom = kingdom;
        if (kingdom.hasRank(rank)){
            this.rank = rank;
        }else{
            throw new NoSuchRankInKingdom();
        }
    }

    public boolean hasPermission(String permission){
        return this.kingdom.getRankPermissions(this.rank).contains(permission);
    }

    public boolean seeApps(){
        return hasPermission("see-applications");
    }

    public String getPrefix(){
        return this.kingdom.getColoredRank(rank);
    }

}
