import java.util.ArrayList;

public class CardDoubloons extends Card {

    public CardDoubloons(int min, int max) {
        super(Rand.randomInt(min, max+1));
    }

    @Override
    public void play(Player currentPlayer, ArrayList<Player> allPlayers) {
        System.out.println(currentPlayer.getName() + " plays " + this);

        if (allPlayers.size() < 2) {
            System.out.println("Error: No other players for Doubloons Card.");
            return;
        }

        Player otherPlayer = currentPlayer.selectAnotherPlayer(allPlayers);
        System.out.println(currentPlayer.getName() + " chose " + otherPlayer.getName() + "!");

        otherPlayer.addGold(super.getPointValue());
        System.out.println("\n" + otherPlayer.getName() + " now has " + otherPlayer.getGoldAmount() + " gold.");
    }

    @Override
    public String toString() {
        return "Doubloons Card {another player gains gold: " + super.getPointValue() + "}";
    }}

