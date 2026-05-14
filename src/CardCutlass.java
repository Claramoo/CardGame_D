import java.util.ArrayList;

public class CardCutlass extends Card implements SkipsPlayerTurn {

    private final int damage;

    public CardCutlass() {

        // Attack card settings
        int minDamage = 2;
        int maxDamage = 4;
        this.damage = Rand.randomInt(minDamage, maxDamage + 1);

        // Points gained from playing this card
        int minPoints = 2;
        int maxPoints = 4;
        int pointValue = Rand.randomInt(minPoints, maxPoints + 1);

        super(pointValue);
    }

    @Override
    public void play(Player currentPlayer, ArrayList<Player> allPlayers) {
        System.out.println(currentPlayer.getName() + " played " + this);

        currentPlayer.addPoints(super.getPointValue());
        System.out.println(currentPlayer.getName() + " now has " + currentPlayer.getNumPoints() + " points.");

        // choose a target player (and not the current player)
        if (allPlayers.size() < 2) {
            System.out.println("Error: No other players for the AttackCard to damage.");
            return;
        }

        Player otherPlayer = currentPlayer.selectAnotherPlayer(allPlayers);
        loseGold(currentPlayer, otherPlayer);
        handicapPlayer(currentPlayer, otherPlayer);
    }

    public void loseGold(Player currentPlayer, Player playerToDamage) {
        playerToDamage.removePoints(damage);
        System.out.println("\n" + currentPlayer.getName() + " did " + damage + " damage to " + playerToDamage.getName() + ".");
        System.out.println(playerToDamage.getName() + " now has " + playerToDamage.getNumPoints() + " points.");
    }

    @Override
    public String toString() {
        return "Cutlass Card {gold gained: " + super.getPointValue() + ", gold stolen: " + damage + "}";
    }

    @Override
    public void handicapPlayer(Player currentPlayer, Player playerToFreeze) {
        playerToFreeze.freeze();
        System.out.println("\n" + currentPlayer.getName() + " attacked " + playerToFreeze.getName() + "!");
    }
}
