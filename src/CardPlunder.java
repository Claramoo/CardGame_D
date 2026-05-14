import java.util.ArrayList;

public class CardPlunder extends Card {

    public CardPlunder() {
        super(-4);
    }

    @Override
    public void play(Player currentPlayer, ArrayList<Player> allPlayers) {
        currentPlayer.addPoints(super.getPointValue());

        System.out.println(currentPlayer.getName() + " played " + this);
        System.out.println(currentPlayer.getName() + " now has " + currentPlayer.getNumPoints() + " points.");

        // 1. choose a target player (and not oneself)
        if (allPlayers.size() < 2) {
            System.out.println("Error: No other players for the ThiefCard to steal from.");
            return;
        }

        Player otherPlayer = currentPlayer.selectAnotherPlayer(allPlayers);

        // 2. remove a random card from that player
        Card removedCard = otherPlayer.removeRandomCard();
        if (removedCard == null) {
            System.out.println("Cannot steal from " + otherPlayer.getName() + " because they have no cards!");
        }

        // 3. add the removed card to the current player
        else {
            currentPlayer.addCardToHand(removedCard);
            System.out.println(currentPlayer.getName() + " stole " + removedCard + " from " + otherPlayer.getName() + ".");
        }
    }

    @Override
    public String toString() {
        return "Plunder Card {gold lost: -4, steals another player's card";
    }
}
