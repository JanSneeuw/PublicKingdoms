package net.zwet.publickingdom.validation;

import net.zwet.publickingdom.objects.Playerdata;
import org.bukkit.entity.Player;

public class IsHigherValidation implements Validation {

    private Player player;
    private Playerdata playerdata;
    private String rank;
    private String errorMessage;

    public IsHigherValidation(Player player){
        this.player = player;
        this.playerdata = new Playerdata(this.player);
    }

    public IsHigherValidation(Playerdata playerdata){
        this.playerdata = playerdata;
    }

    public IsHigherValidation(Player player, String rank){
        this.player = player;
        this.playerdata = new Playerdata(this.player);
        this.rank = rank;
    }

    public IsHigherValidation rank(String rank){
        this.rank = rank;
        return this;
    }

    @Override
    public boolean isValid() {
        boolean result = this.playerdata.isHigherThan(this.rank);
        if (!result){
            this.errorMessage = "Je staat niet hoger dan " + this.rank;
        }

        return result;
    }

    @Override
    public boolean isInvalid() {
        return ! isValid();
    }

    @Override
    public String getErrorMessage() {
        return this.errorMessage;
    }
}
