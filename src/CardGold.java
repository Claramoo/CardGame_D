import java.util.ArrayList;

public class CardGold extends Card {

    public CardGold(int min, int max) {
        super(Rand.randomInt(min, max+1));
    }

    @Override
    public void play(Player currentPlayer, ArrayList<Player> allPlayers) {
        currentPlayer.addGold(super.getPointValue());

        System.out.println(currentPlayer.getName() + " plays " + this);
        System.out.println(currentPlayer.getName() + " now has " + currentPlayer.getGoldAmount() + " gold.");
    }

    @Override
    public String toString() {
        return "Gold Card {gain gold: " + super.getPointValue() + "}";
    }
}
