import java.util.ArrayList;

public class CardTreasureMap extends Card {


    public CardTreasureMap(int min, int max) {
        super(Rand.randomInt(min, max+1));
    }

    @Override
    public void play(Player currentPlayer, ArrayList<Player> allPlayers) {
        System.out.println(currentPlayer.getName() + " plays " + this);

        for (Player p : allPlayers) {
            p.addGold(super.getPointValue());

            System.out.println(p.getName() + " now has " + p.getGoldAmount() + " gold.");
        }
    }

    @Override
    public String toString() {
        return "Treasure Map Card {all players gain gold: " + super.getPointValue() + "}";
    }

}
