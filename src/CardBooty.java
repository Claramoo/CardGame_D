import java.util.ArrayList;

public class CardBooty extends Card {

    public CardBooty() {
        super(0);
    }

    @Override
    public void play(Player currentPlayer, ArrayList<Player> allPlayers) {
        currentPlayer.addPoints(currentPlayer.getNumPoints());

        System.out.println(currentPlayer.getName() + " played " + this);
        System.out.println(currentPlayer.getName() + " now has " + currentPlayer.getNumPoints() + " points.");
    }

    @Override
    public String toString() {
        return "Booty Card {double player's gold count}";
    }

}
