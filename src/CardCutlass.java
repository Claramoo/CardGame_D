import java.util.ArrayList;

public class CardCutlass extends Card implements InjurePlayer {

    public CardCutlass(int min, int max) {
        super(Rand.randomInt(min, max+1));
    }

    @Override
    public void play(Player currentPlayer, ArrayList<Player> allPlayers) {
        System.out.println(currentPlayer.getName() + " played " + this);

        // choose a target player (and not the current player)
        if (allPlayers.size() < 2) {
            System.out.println("Error: No other players for Cutlass Card.");
            return;
        }

        Player otherPlayer = currentPlayer.selectAnotherPlayer(allPlayers);

        otherPlayer.loseGold(super.getPointValue());
        System.out.println(otherPlayer.getName() + " now has " + otherPlayer.getGoldAmount() + " gold.");

        attackPlayer(currentPlayer, otherPlayer);
    }

    @Override
    public String toString() {
        return "Cutlass Card {another player loses gold: " + super.getPointValue() + ", injures another player}";
    }

    @Override
    public void attackPlayer(Player currentPlayer, Player playerToAttack) {
        playerToAttack.injure();
        System.out.println(currentPlayer.getName() + " attacks " + playerToAttack.getName() + "!");
    }
}
