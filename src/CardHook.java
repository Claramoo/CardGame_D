import java.util.ArrayList;

public class CardHook extends Card implements InjurePlayer {

    public CardHook() {
        super(0);
    }

    @Override
    public void play(Player currentPlayer, ArrayList<Player> allPlayers) {
        System.out.println(currentPlayer.getName() + " plays " + this);
        
        // choose a target player (and not the current player)
        if (allPlayers.size() < 2) {
            System.out.println("Error: No other players for Hook Card.");
            return;
        }

        Player otherPlayer = currentPlayer.selectAnotherPlayer(allPlayers);
        System.out.println(currentPlayer.getName() + " chose " + otherPlayer.getName() + "!");

        attackPlayer(currentPlayer, otherPlayer);

    }

    @Override
    public void attackPlayer(Player currentPlayer, Player playerToAttack) {
        playerToAttack.injure();
        System.out.println("\n" + currentPlayer.getName() + " attacks " + playerToAttack.getName() + "!");
    }


    @Override
    public String toString() {
        return "Hook Card {injures another player}";
    }
}
