import java.util.ArrayList;
import java.util.Random;

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
        System.out.println(currentPlayer.getName() + " chose " + otherPlayer.getName() + "!");

        if (!otherPlayer.hasCardsInHand()) {
            System.out.println("\nCan't steal from " + otherPlayer.getName() + " because they have no cards!");
            return;
        }
        Card stolenCard;

        if (currentPlayer.isPlayable()) {
            System.out.println();
            otherPlayer.displayStatus();

            int index;
            do {
                index = Input.getUserInt(">") - 1;
            } while (index < 0 || index >= otherPlayer.handSize());
            stolenCard = otherPlayer.removeCard(index);

        }
        else {
            stolenCard = otherPlayer.removeCard(Rand.randomInt(0, otherPlayer.handSize()));
        }

        currentPlayer.addCardToHand(stolenCard);
        System.out.println("\n" + currentPlayer.getName() + " steals " + stolenCard + " from " + otherPlayer.getName());
    }

    @Override
    public String toString() {
        return "Plunder Card {steal another player's card}";
    }
}
