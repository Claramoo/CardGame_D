import java.util.ArrayList;

public class CardRum extends Card {

    public CardRum() {
        super(0);
    }

    @Override
    public void play(Player currentPlayer, ArrayList<Player> allPlayers) {
        int pointValue = currentPlayer.getNumPoints();
        currentPlayer.addPoints(-currentPlayer.getNumPoints());

        if (allPlayers.size() < 2) {
            System.out.println("Error: No other player to give gold to!");
            return;
        }

        Player otherPlayer = currentPlayer.selectAnotherPlayer(allPlayers);

        otherPlayer.addPoints(pointValue);

        System.out.println(currentPlayer.getName() + " played " + this);
        System.out.println(currentPlayer.getName() + " gave all of their gold to " + otherPlayer.getName() + "!");
        System.out.println(otherPlayer.getName() + "now has " + otherPlayer.getNumPoints() + " gold.");
    }

    @Override
    public String toString() {
        return "Rum Card {gives all your gold to another player}";
    }

}
