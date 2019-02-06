package net.zwet.publickingdom.validation;

import net.zwet.publickingdom.objects.Kingdom;
import net.zwet.publickingdom.objects.Playerdata;
import org.bukkit.entity.Player;

public class ShareKingdomValidation implements Validation {

    private String errorMessage;
    private Player player;
    private Player p;
    private Playerdata playerdata1;
    private Playerdata playerdata2;

    public ShareKingdomValidation(Player player1, Player player2){
        this.player = player1;
        this.p = player2;
        this.playerdata1 = new Playerdata(this.player);
        this.playerdata2 = new Playerdata(this.p);
    }
    public ShareKingdomValidation(){
    }

    public ShareKingdomValidation fromPlayer(Player player){
        this.player = player;
        this.playerdata1 = new Playerdata(this.player);
        return this;
    }
    public ShareKingdomValidation toPlayer(Player player){
        this.p = player;
        this.playerdata2 = new Playerdata(this.p);
        return this;
    }
    public ShareKingdomValidation(Playerdata playerdata, Playerdata playerdata1){
        this.playerdata1 = playerdata;
        this.playerdata2 = playerdata1;
    }

    @Override
    public boolean isValid() {
        boolean result = this.playerdata1.getKingdomName().equals(this.playerdata2.getKingdomName());
        if (!result){
            this.errorMessage = this.playerdata1.getName() + " en " + this.playerdata2.getName() + " zitten niet in het zelfde kindgom!";
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
