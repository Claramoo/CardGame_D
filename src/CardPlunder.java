import java.util.ArrayList;

public class CardPlunder extends Card {

    public CardPlunder() {
        super(0);
    }

    @Override
    public void play(Player currentPlayer, ArrayList<Player> allPlayers) {
        System.out.println(currentPlayer.getName() + " plays " + this);

        if (allPlayers.size() < 2) {
            System.out.println("Error: No other players for the AttackCard to damage.");
            return;
        }

        Player otherPlayer = currentPlayer.selectAnotherPlayer(allPlayers);

        if (!otherPlayer.hasCardsInHand()) {
            System.out.println("Can't steal from " + otherPlayer.getName() + " because they have no cards!");
            return;
        }

        Card stolenCard = otherPlayer.removeRandomCard();
        currentPlayer.addCardToHand(stolenCard);
        System.out.println(currentPlayer.getName() + " steals " + stolenCard + " from " + otherPlayer.getName());

    }

    @Override
    public String toString() {
        return "Plunder Card {steal another player's card}";
    }
}
