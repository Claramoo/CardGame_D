import java.util.ArrayList;

public class CardCracken extends Card {

    public CardCracken() {
        int min = 3;
        int max = 5;

        super(-Rand.randomInt(min, max+1));
    }

    @Override
    public void play(Player currentPlayer, ArrayList<Player> allPlayers) {

    }
}
