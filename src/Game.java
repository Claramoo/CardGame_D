import java.util.ArrayList;
import java.util.Collections;

public class Game {

    // ----------- Settings ----------- //

    // Player settings
    private int startingHandSize;

    private float playerChancesOfPlayingCard; // % chance (0-1) that a player plays a card from their hand
    private float playerChancesOfDrawingFromMixedDeck; // % chance (0-1) that a player draws from the mixed deck
    // private float playerChancesOfDrawingFromDamageDeck; // damage deck chances are the leftovers of the other chances

    // Deck settings
    private int totalNumberOfCards;
    private float pointCardChances; // % chance (from 0-1) of generating a point card
    private float attackCardChances; // % chance (from 0-1) of generating an attack card
    private float freezeCardChances; // % chance (from 0-1) of generating a injure card
    //private float thiefCardChances; // thief card chances are the leftovers of the other chances

    private float chancesOfDamageCardBeingInDamageDeck; // % chance of a generated damage card being added to the damage-only deck

    // -------- End of Settings ------- //


    // --------- Game Objects --------- //

    private ArrayList<Player> players;
    private ArrayList<Card> mixedDeck; // contains a mix of all types of cards

    // ------ End of Game Objects ----- //



    public Game() {
        // Set game settings
        setGameSettings();

        // Game objects
        players = new ArrayList<>();
        mixedDeck = new ArrayList<>();

        // Generate the decks
        generateDecks();
    }

    public void registerPlayer(Player player) {
        players.add(player);
    }

    public void run() {

        // deal cards to each player
        int cardsAdded = 0;
        while (cardsAdded < startingHandSize) {
            for (Player player : players) {
                int randomCardIndex = Rand.randomInt(0, mixedDeck.size());
                Card randomCard = mixedDeck.get(randomCardIndex);
                mixedDeck.remove(randomCardIndex);
                player.addCardToHand(randomCard);
            }
            cardsAdded += 1;
        }

        int currentPlayerIndex = -1; // will increase to 0 when the loop starts
        Player currentPlayer;

        // game loop -- loop as long as either deck has cards

        while (!mixedDeck.isEmpty()) {

            // switch to next player
            currentPlayerIndex += 1;
            if (currentPlayerIndex >= players.size()) {
                currentPlayerIndex = 0;
            }
            currentPlayer = players.get(currentPlayerIndex);

            System.out.println("\n# cards remaining in Mixed deck: " + mixedDeck.size() + ".");

            System.out.println("It's " + currentPlayer.getName() + "'s turn.\n");
            currentPlayer.displayStatus();
            Input.waitForUserToPressEnter("\nPress Enter to play " + currentPlayer.getName() + "'s turn.");
            System.out.println();

            // check if the player should be skipped
            if (currentPlayer.isInjured()) {
                System.out.println(currentPlayer.getName() + " is frozen! Skipping turn.");
                currentPlayer.healFromInjury();
                System.out.println("\n" + "----- ----- ----- ----- ----- ----- ----- -----");
                continue; // skips the rest of the body of the loop, and returns to the start of the loop
            }

            // generate a random value to choose a random action
            float randomValue = Rand.random();

            // 1. play a card from player's hand
            if (randomValue < playerChancesOfPlayingCard && currentPlayer.hasCardsInHand()) {
                currentPlayer.playRandomCardFromHand(players);
            }

            // 2. OR draw a card from mixed deck (but don't play it yet)
            else if (!mixedDeck.isEmpty()) {
                Object drawnObject = drawRandomCard(mixedDeck);
                Card drawnCard = (Card)drawnObject;
                currentPlayer.addCardToHand(drawnCard);

                System.out.println(currentPlayer.getName() + " drew a " + drawnCard + " from the Mixed deck.");
            }

            Input.waitForUserToPressEnter("\nPress Enter to end " + currentPlayer.getName() + "'s turn.");

            System.out.println("\n" + "----- ----- ----- ----- ----- ----- ----- -----");
        }

        // End game: determine which Player had the most points
        declareWinner();
    }

    // Randomly selects a reference (Card or DealsDamage) from an ArrayList (mixedDeck or damageDeck).
    // Removes the randomly selected reference from the specified ArrayList.
    // Returns the selected reference as an Object (because we don't know what type the ArrayList stores).
    private Object drawRandomCard(ArrayList arrayList) {
        int randomCardIndex = Rand.randomInt(0, arrayList.size());
        return arrayList.remove(randomCardIndex);
    }

    // Initializes the settings fields.
    private void setGameSettings() {
        // Player settings
        startingHandSize = 3;
        playerChancesOfPlayingCard = 0.5f; // 50% play card, 25% draw card from mixed, 25% draw card from damage deck and play immediately
        playerChancesOfDrawingFromMixedDeck = 0.25f;
        float playerChancesOfDrawingFromDamageDeck = 1f - (playerChancesOfPlayingCard + playerChancesOfDrawingFromMixedDeck);
        if (playerChancesOfDrawingFromDamageDeck < 0f) {
            System.out.println("ERROR: Chances of different player actions are not all positive.");
        }


        // Deck settings
        //totalNumberOfCards = 20;
        chancesOfDamageCardBeingInDamageDeck = 0.4f;

        pointCardChances = 0.5f; // must be between 0 and 1
        attackCardChances = 0.25f; // must be between 0 and 1
        freezeCardChances = 0.15f; // must be between 0 and 1

        // thief card chances should be positive based on the math, but check just to be safe
        float thiefCardChances = 1f - (pointCardChances + attackCardChances + freezeCardChances);
        if (thiefCardChances < 0f) {
            System.out.println("ERROR: Card chances are not all positive.");
        }
    }

    // Populates the two ArrayLists with random Cards, according to the settings.
    private void generateDecks() {
        for (int i = 0; i < 3; i++) {
            mixedDeck.add(new CardBooty());
            mixedDeck.add(new CardCannon(3,6));
            mixedDeck.add(new CardCracken(4,7));
            mixedDeck.add(new CardCutlass(1,9));
            mixedDeck.add(new CardDoubloons(3,6));
            mixedDeck.add(new CardGold(1,4));
            mixedDeck.add(new CardKnife());
            mixedDeck.add(new CardPlank());
            mixedDeck.add(new CardPlunder());
            mixedDeck.add(new CardRum());
            mixedDeck.add(new CardTreasureMap(6,8));
            mixedDeck.add(new CardWind());
        }
        Collections.shuffle(mixedDeck);
    }

    private void declareWinner() {
        int highestScore = 0;
        Player playerWithHighestScore = null;

        System.out.println("\nFinal Scoreboard:");
        for (Player p : players) {
            System.out.println(p.getName() + ": " + p.getGoldAmount());

            // update the highest score tracker
            if (p.getGoldAmount() >= highestScore) {
                highestScore = p.getGoldAmount();
                playerWithHighestScore = p;
            }
        }

        System.out.println("Player '" + playerWithHighestScore.getName() + "' wins!");
    }
}
