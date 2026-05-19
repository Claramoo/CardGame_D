import java.util.ArrayList;

public class Player {
    private final String name;
    private int goldAmount;
    private boolean playable;
    private boolean isInjured  = false;
    private ArrayList<Card> hand = new ArrayList<>();

    public Player(String name, boolean playable, int goldAmount) {
        this.name = name;
        this.goldAmount = goldAmount;
        this.playable = playable;
    }

    public void playCardFromHand(ArrayList<Player> players) {
        int index;
        if (playable) {
            do {
                index = Input.getUserInt("Which Card to play? (1-" + hand.size() + ")\n>") - 1;
            } while (index < 0 || index >= hand.size());
        }
        else {
            index = Rand.randomInt(0, hand.size());
        }
        Card randomCard = hand.remove(index);
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

    public boolean isPlayable() {
        return playable;
    }

    public Card removeCard(int index) {
        if (hand.isEmpty()) {
            return null; // returning null indicates there are no cards to remove
        }
        return hand.remove(index); // ArrayList.remove both removes AND returns a reference to the object
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
    
    public String goldToString() {
        return "★".repeat(Math.max(0, goldAmount));
    }

    public int handSize() {
        return hand.size();
    }

    public void displayStatus() {
        if (isInjured) {
            System.out.println(" | ----/ ❄ " + name + " ❄ /---- ");
        }
        else {
            System.out.println(" | ----- " + name + " ----- ");
        }
        System.out.println(" | Gold: " + goldAmount + " " + goldToString());
        if (isInjured) {
            System.out.println(" | X INJURED X ");
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
        Player otherPlayer;

        if (playable) {
            int index;
            ArrayList<Player> otherPlayers = (ArrayList<Player>) players.clone();
            otherPlayers.remove(this);
            for (int i=0;i < otherPlayers.size();i++) {
                System.out.println((i+1) + ".) " + otherPlayers.get(i));
            }

            do {
                index = Input.getUserInt("Choose a player!\n>") - 1;
            } while (index < 0 || index >= otherPlayers.size());
            otherPlayer = otherPlayers.get(index);
        }
        else {
            do {
                otherPlayer = players.get(Rand.randomInt(0, players.size()));
            } while (otherPlayer == this);
        }
        return otherPlayer;
    }

    @Override
    public String toString() {
        return (this.getName() + " {" + hand.size() +
                " cards in hand, Gold: " + goldAmount + " " + goldToString() + "}");
    }

}
