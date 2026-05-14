import java.util.ArrayList;

public class CardGold extends Card {

    public CardGold() {
        // Point card settings
        int minPoints = 3;
        int maxPoints = 9;

        int pointValue = Rand.randomInt(minPoints, maxPoints + 1);

        super(pointValue);
    }

    @Override
    public void play(Player currentPlayer, ArrayList<Player> allPlayers) {
        currentPlayer.addPoints(super.getPointValue());

        System.out.println(currentPlayer.getName() + " played " + this);
        System.out.println(currentPlayer.getName() + " now has " + currentPlayer.getNumPoints() + " points.");
    }

    @Override
    public String toString() {
        return "Gold Card {gold value: " + super.getPointValue() + "}";
    }
}
