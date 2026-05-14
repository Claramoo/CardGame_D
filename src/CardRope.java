import java.util.ArrayList;

public class CardRope extends Card implements SkipsPlayerTurn {

    public CardRope() {
        super(1);
    }

    @Override
    public void play(Player currentPlayer, ArrayList<Player> allPlayers) {
        currentPlayer.addPoints(super.getPointValue());
        
        // choose a target player (and not the current player)
        if (allPlayers.size() < 2) {
            System.out.println("Error: No other players for the FreezeCard to freeze or damage.");
            return;
        }

        Player otherPlayer = currentPlayer.selectAnotherPlayer(allPlayers);

        handicapPlayer(currentPlayer, otherPlayer);

    }

    @Override
    public void handicapPlayer(Player currentPlayer, Player playerToFreeze) {
        playerToFreeze.freeze();
        System.out.println("\n" + currentPlayer.getName() + " froze " + playerToFreeze.getName() + "!");
    }


    @Override
    public String toString() {
        return "Rope Card {gold gained: 1, skips another player's turn}";
    }
}
