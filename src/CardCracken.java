import java.util.ArrayList;

public class CardCracken extends Card{

    public CardCracken() {
        int min = 3;
        int max = 5;

        super(Rand.randomInt(min, max+1));
    }

    @Override
    public void play(Player currentPlayer, ArrayList<Player> allPlayers) {
        System.out.println(currentPlayer.getName() + " played " + this);

        for (Player p : allPlayers) {
            p.addPoints(-super.getPointValue());

            System.out.println(p.getName() + " now has " + p.getNumPoints() + " gold.");
        }
    }

    @Override
    public String toString() {
        return "Cracken Card {all players lose " + super.getPointValue() + " gold}";
    }

}
