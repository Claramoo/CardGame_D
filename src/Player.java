import java.util.ArrayList;

public class Player {
    private final String name;
    private ArrayList<Card> hand;
    private int numPoints;
    private boolean isHandicapped;

    public Player(String name) {
        this.name = name;
        hand = new ArrayList<Card>();
        numPoints = 5;
        isHandicapped = false;
    }

    public void playRandomCardFromHand(ArrayList<Player> players) {
        // select a random card from our hand to play
        int randomCardIndex = Rand.randomInt(0, hand.size());
        Card randomCard = hand.remove(randomCardIndex);
        randomCard.play(this, players);
    }

    public boolean hasCardsInHand() {
        return !hand.isEmpty();
    }

    public void addCardToHand(Card card) {
        hand.add(card);
    }

    public boolean isHandicapped() {
        return isHandicapped;
    }

    public void freeze() {
        isHandicapped = true;
    }

    public void unfreeze() {
        isHandicapped = false;
    }

    public Card removeRandomCard() {
        if (hand.isEmpty()) {
            return null; // returning null indicates there are no cards to remove
        }

        int randomCardIndex = Rand.randomInt(0, hand.size());
        return hand.remove(randomCardIndex); // ArrayList.remove both removes AND returns a reference to the object
    }

    public String getName() {
        return name;
    }

    public void addPoints(int pointsToAdd) {
        numPoints += pointsToAdd;
    }

    public void removePoints(int pointsToRemove) {
        addPoints(-pointsToRemove);
    }

    public int getNumPoints() {
        return numPoints;
    }
    
    public String pointsToString(int numPoints) {
        return "★".repeat(Math.max(0, numPoints));
    }

    public void displayStatus() {
        if (isHandicapped) {
            System.out.println(" | ----/ ❄ " + name + " ❄ /---- ");
        }
        else {
            System.out.println(" | ----- " + name + " ----- ");
        }
        System.out.println(" | Points: " + numPoints + " " + pointsToString(numPoints));
        if (isHandicapped) {
            System.out.println(" | ❄ FROZEN ❄ ");
        }
        System.out.println(" | Cards in hand:");
        for (int i = 0; i < hand.size(); i++) {
            System.out.print(" | " + (i+1) + ": ");
            System.out.println(hand.get(i));
        }
        System.out.println(" | ----- ----- ----- ");
    }

    public Player selectAnotherPlayer(ArrayList<Player> players) {

        // pick a random player (but not oneself) to apply any additional actions to
        boolean selectedAnotherPlayer = false;
        Player otherPlayer = null;

        while (!selectedAnotherPlayer) {
            int randomPlayerIndex = Rand.randomInt(0, players.size());
            otherPlayer = players.get(randomPlayerIndex);
            if (otherPlayer != this) {
                selectedAnotherPlayer = true;
            }
        }

        return otherPlayer;
    }
}
