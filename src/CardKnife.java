import java.util.ArrayList;

public class CardKnife extends Card implements InjurePlayer {

    public CardKnife() {
        super(0);
    }

    @Override
    public void play(Player currentPlayer, ArrayList<Player> allPlayers) {
        System.out.println(currentPlayer.getName() + " plays " + this);
        
        // choose a target player (and not the current player)
        if (allPlayers.size() < 2) {
            System.out.println("Error: No other players for Knife Card.");
            return;
        }

        Player otherPlayer = currentPlayer.selectAnotherPlayer(allPlayers);

        attackPlayer(currentPlayer, otherPlayer);

    }

    @Override
    public void attackPlayer(Player currentPlayer, Player playerToAttack) {
        playerToAttack.injure();
        System.out.println(currentPlayer.getName() + " attacks " + playerToAttack.getName() + "!");
    }


    @Override
    public String toString() {
        return "Card Knife {injures another player}";
    }
}
