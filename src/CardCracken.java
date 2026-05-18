import java.util.ArrayList;

public class CardCracken extends Card {

    public CardCracken(int min, int max) {
        super(Rand.randomInt(min, max+1));
    }

    @Override
    public void play(Player currentPlayer, ArrayList<Player> allPlayers) {
        System.out.println(currentPlayer.getName() + " plays " + this);

        for (Player p : allPlayers) {
            p.loseGold(super.getPointValue());

            System.out.println(p.getName() + " now has " + p.getGoldAmount() + " gold.");
        }

    }

    @Override
    public String toString() {
        return "Cracken Card {all players lose gold: " + super.getPointValue() + "}";
    }

}
