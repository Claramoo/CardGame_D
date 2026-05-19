import java.util.ArrayList;
import java.util.Collections;

public class Game {

    // ----------- Settings ----------- //
    private final int startingGoldAmount = 0;
    private final int startingHandSize = 5;
    private final float playerChancesOfDrawingCard = 0.4f; // % chance (0-1) that a player plays a card from their hand
    // -------- End of Settings ------- //


    // --------- Game Objects --------- //
    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<Card> deck = new ArrayList<>();
    // ------ End of Game Objects ----- //


    public Game() {
        registerPlayers();
        generateDecks();
    }


    private void registerPlayers() {
        players.add(new Player("Aang", true, startingGoldAmount));
        players.add(new Player("SpongeBob", false, startingGoldAmount));
        players.add(new Player("Michelangelo", false, startingGoldAmount));
        players.add(new Player("Damien", false, startingGoldAmount));
    }


    private void generateDecks() {
        for (int i = 0; i < 4; i++) {
            deck.add(new CardBooty());
            deck.add(new CardCannon(3,6));
            deck.add(new CardCracken(4,7));
            deck.add(new CardCutlass(1,9));
            deck.add(new CardDoubloons(3,6));
            deck.add(new CardGold(1,4));
            deck.add(new CardGold(1,4));
            deck.add(new CardPlank());
            deck.add(new CardPlunder());
            deck.add(new CardRum());
            deck.add(new CardTreasureMap(6,8));
        }
        deck.add(new CardCannon(3,6));
        deck.add(new CardHook());
        deck.add(new CardHook());
        deck.add(new CardWind());
        deck.add(new CardWind());

        Collections.shuffle(deck);

        if (deck.size() < (players.size()+1) * startingHandSize) {
            System.out.println("ERROR: NOT ENOUGH CARDS FOR COMFORTABLE PLAY");
        }
    }


    public void run() {

        // Deals cards to the players
        for (Player p : players) {
            for (int cards=0; cards < startingHandSize; cards++) {
                p.addCardToHand(deck.removeLast());
            }
        }

        int currentPlayerIndex = -1; // will increase to 0 when the loop starts
        Player currentPlayer;

        // game loop - game ends when the deck is empty and one of the players have no cards left
        while (!deck.isEmpty() || playersHaveHand()) {

            // switch to next player
            currentPlayerIndex += 1;
            if (currentPlayerIndex >= players.size()) {
                currentPlayerIndex = 0;
            }
            currentPlayer = players.get(currentPlayerIndex);

            System.out.println("\n# cards remaining in deck: " + deck.size() + ".");

            // Display current player
            System.out.println("It's " + currentPlayer.getName() + "'s turn.\n");
            currentPlayer.displayStatus();
            Input.waitForUserToPressEnter("\nPress Enter to play " + currentPlayer.getName() + "'s turn.");
            System.out.println();

            // check if the player should be skipped
            if (currentPlayer.isInjured()) {
                System.out.println(currentPlayer.getName() + " is injured! Skipping turn.");
                currentPlayer.healFromInjury();
                System.out.println("\n" + "----- ----- ----- ----- ----- ----- ----- -----");
                continue; // skips the rest of the body of the loop, and returns to the start of the loop
            }

            // check if the player can't do anything
            if (deck.isEmpty() && !currentPlayer.hasCardsInHand()) {
                System.out.println(currentPlayer.getName() + " has nothing to do! Skipping turn.");
                System.out.println("\n" + "----- ----- ----- ----- ----- ----- ----- -----");
                continue; // skips the rest of the body of the loop, and returns to the start of the loop
            }

            if (currentPlayer.isPlayable()) {
                playerDecision(currentPlayer);
            }
            else {
                botDecisions(currentPlayer);
            }

            Input.waitForUserToPressEnter("\nPress Enter to end " + currentPlayer.getName() + "'s turn.");
            System.out.println("\n" + "----- ----- ----- ----- ----- ----- ----- -----");
        }

        // End game: determine which Player had the most points
        declareWinner();
    }


    private void playerDecision(Player currentPlayer) {
        int index;
        do {
            index = Input.getUserInt("1.) Play a card in hand\n2.) Draw a card from the deck\n>");
        } while (index > 2 || index < 1 ||
                (index == 1 && !currentPlayer.hasCardsInHand()) || (index == 2 && deck.isEmpty()));

        if (index == 1) {
            currentPlayer.playCardFromHand(players);
        }
        else {
            Card drawnCard = deck.removeLast();
            currentPlayer.addCardToHand(drawnCard);

            System.out.println(currentPlayer.getName() + " drew a " + drawnCard + " from the deck.");
        }
    }


    private void botDecisions(Player currentPlayer) {
        // generate a random value to choose a random action
        float randomValue = Rand.random();

        // 1. draw a card from mixed deck (but don't play it yet)
        if (!deck.isEmpty() && (randomValue < playerChancesOfDrawingCard || !currentPlayer.hasCardsInHand())) {
            Card drawnCard = deck.removeLast();
            currentPlayer.addCardToHand(drawnCard);

            System.out.println(currentPlayer.getName() + " drew a " + drawnCard + " from the deck.");
        }

        // 2. OR play a card from player's hand
        else {
            currentPlayer.playCardFromHand(players);
        }
    }


    public boolean playersHaveHand() {
        for (Player p : players) {
            if (!p.hasCardsInHand()) {
                return false;
            }
        }
        return true;
    }


    private void declareWinner() {
        int highestGold = 0;
        ArrayList<Player> winners = new ArrayList<>();

        System.out.println("\nFinal Scoreboard:");
        for (Player p : players) {
            System.out.println(p.getName() + ": " + p.getGoldAmount());

            // update the highest score tracker
            if (p.getGoldAmount() > highestGold) {
                highestGold = p.getGoldAmount();
                winners.clear();
                winners.add(p);
            }
            else if (p.getGoldAmount() == highestGold) {
                winners.add(p);
            }
        }

        System.out.println();
        if (winners.size() == 1) {
            System.out.println("Player " + winners.getFirst().getName() + " wins!");
        }
        else {
            for (int i=0; i <winners.size()-1;i++) {
                System.out.print(winners.get(i).getName() + ", ");
            }
            System.out.println("and " + winners.getLast().getName() + " had a tie!");
        }

    }
}
