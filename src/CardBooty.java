import java.util.ArrayList;

public class CardBooty extends Card {

    public CardBooty() {
        super(0);
    }

    @Override
    public void play(Player currentPlayer, ArrayList<Player> allPlayers) {
        currentPlayer.addGold(currentPlayer.getGoldAmount());

        System.out.println(currentPlayer.getName() + " plays " + this);
        System.out.println(currentPlayer.getName() + " now has " + currentPlayer.getGoldAmount() + " gold.");
    }

    @Override
    public String toString() {
        return "Booty Card {double player's gold}";
    }

}
