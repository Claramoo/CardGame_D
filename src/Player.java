import java.util.ArrayList;

public class Player {
    private final String name;
    private ArrayList<Card> hand;
    private int goldAmount;
    private boolean isInjured;

    public Player(String name) {
        this.name = name;
        hand = new ArrayList<Card>();
        goldAmount = 5;
        isInjured = false;
    }

    public void playRandomCardFromHand(ArrayList<Player> players) {
        // select a random card from our hand to play
        Card randomCard = hand.remove(Rand.randomInt(0, hand.size()));
        randomCard.play(this, players);
    }

    public boolean hasCardsInHand() {
        return !hand.isEmpty();
    }

    public void addCardToHand(Card card) {
        hand.add(card);
    }

    public boolean isInjured() {
        return isInjured;
    }

    public void injure() {
        isInjured = true;
    }

    public void healFromInjury() {
        isInjured = false;
    }

    public Card removeRandomCard() {
        if (hand.isEmpty()) {
            return null; // returning null indicates there are no cards to remove
        }
        return hand.remove(Rand.randomInt(0, hand.size())); // ArrayList.remove both removes AND returns a reference to the object
    }

    public String getName() {
        return name;
    }

    public void addGold(int goldToAdd) {
        goldAmount = (goldAmount + goldToAdd) % 10;
        if (goldAmount < 0) {
            goldAmount += 10;
        }
    }

    public void loseGold(int goldToLose) {
        goldAmount = (goldAmount - goldToLose) % 10;
        if (goldAmount < 0) {
            goldAmount += 10;
        }
    }

    public int getGoldAmount() {
        return goldAmount;
    }
    
    public String goldToString(int numPoints) {
        return "★".repeat(Math.max(0, numPoints));
    }

    public void displayStatus() {
        if (isInjured) {
            System.out.println(" | ----/ ❄ " + name + " ❄ /---- ");
        }
        else {
            System.out.println(" | ----- " + name + " ----- ");
        }
        System.out.println(" | Points: " + goldAmount + " " + goldToString(goldAmount));
        if (isInjured) {
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
