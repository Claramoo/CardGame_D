import java.util.ArrayList;
import java.util.Collections;

public class CardWind extends Card{

    public CardWind() {
        super(0);
    }

    @Override
    public void play(Player currentPlayer, ArrayList<Player> allPlayers) {
        System.out.println(currentPlayer.getName() + " plays " + this);

        ArrayList<Card> allCards = new ArrayList<>();
        for (Player p : allPlayers) {
            while (p.hasCardsInHand()) {
                allCards.add(p.removeRandomCard());
            }
        }
        Collections.shuffle(allCards);

        for (int i=0;i < allCards.size()/allPlayers.size();i++) {
            for (Player p : allPlayers) {
                p.addCardToHand(allCards.removeLast());
            }
        }

        System.out.println("\nCards have been shuffled and distributed.");
    }


    @Override
    public String toString() {
        return "Wind Card {shuffles all player's cards and distributes evenly, remaining cards get discarded}";
    }


}
