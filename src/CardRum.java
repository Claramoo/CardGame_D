import java.util.ArrayList;

public class CardRum extends Card {

    public CardRum() {
        super(0);
    }

    @Override
    public void play(Player currentPlayer, ArrayList<Player> allPlayers) {
        System.out.println(currentPlayer.getName() + " plays " + this);

        int goldValue = currentPlayer.getGoldAmount();



        if (allPlayers.size() < 2) {
            System.out.println("Error: No other player for Rum Card");
            return;
        }

        Player otherPlayer = currentPlayer.selectAnotherPlayer(allPlayers);
        System.out.println(currentPlayer.getName() + " chose " + otherPlayer.getName() + "!");

        currentPlayer.loseGold(goldValue);
        System.out.println("\n" + currentPlayer.getName() + " now has " + currentPlayer.getGoldAmount() + " gold.");

        otherPlayer.addGold(goldValue);
        System.out.println(otherPlayer.getName() + " now has " + otherPlayer.getGoldAmount() + " gold.");
    }

    @Override
    public String toString() {
        return "Rum Card {gives all your gold to another player}";
    }

}
