import java.util.ArrayList;

public class CardPlank extends Card {

    public CardPlank() {
        super(0);
    }

    @Override
    public void play(Player currentPlayer, ArrayList<Player> allPlayers) {
        System.out.println(currentPlayer.getName() + " plays " + this);

        if (allPlayers.size() < 2) {
            System.out.println("Error: No other player for Plank Card");
            return;
        }
        Player otherPlayer = currentPlayer.selectAnotherPlayer(allPlayers);

        int goldValue = otherPlayer.getGoldAmount();

        otherPlayer.loseGold(goldValue);
        System.out.println(otherPlayer.getName() + "now has " + otherPlayer.getGoldAmount() + " gold.");

        currentPlayer.addGold(goldValue);
        System.out.println("\n" + currentPlayer.getName() + " now has " + currentPlayer.getGoldAmount() + " gold.");

    }

    @Override
    public String toString() {
        return "Plank Card {another player gives all their gold to you}";
    }

}
