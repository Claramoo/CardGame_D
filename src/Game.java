import java.util.ArrayList;
import java.util.Collections;

public class Game {

    // ----------- Settings ----------- //

    // Player settings
    private int startingHandSize;
    private int numOfPlayers;

    private float playerChancesOfPlayingCard; // % chance (0-1) that a player plays a card from their hand
    private float playerChancesOfDrawingFromMixedDeck; // % chance (0-1) that a player draws from the mixed deck
    // private float playerChancesOfDrawingFromDamageDeck; // damage deck chances are the leftovers of the other chances

    // Deck settings
    private int totalNumberOfCards;
    private float pointCardChances; // % chance (from 0-1) of generating a point card
    private float attackCardChances; // % chance (from 0-1) of generating an attack card
    private float freezeCardChances; // % chance (from 0-1) of generating a freeze card
    //private float thiefCardChances; // thief card chances are the leftovers of the other chances

    private float chancesOfDamageCardBeingInDamageDeck; // % chance of a generated damage card being added to the damage-only deck

    // -------- End of Settings ------- //


    // --------- Game Objects --------- //

    private ArrayList<Player> players;
    private ArrayList<Card> mixedDeck; // contains a mix of all types of cards
    private ArrayList<DealsDamage> damageDeck; // contains only cards that implement DealsDamage

    // ------ End of Game Objects ----- //


    public Game() {
        // Game objects
        players = new ArrayList<Player>();
        mixedDeck = new ArrayList<Card>();
        damageDeck = new ArrayList<DealsDamage>();

        // Set game settings
        setGameSettings();

        // Generate the decks
        generateDecks();

    }

    // Initializes the settings fields.
    private void setGameSettings() {
        // Player settings
        startingHandSize = 3;
        playerChancesOfPlayingCard = 0.5f; // 50% play card, 25% draw card from mixed, 25% draw card from damage deck and play immediately
        playerChancesOfDrawingFromMixedDeck = 0.25f;

        // Deck settings
        totalNumberOfCards = 20;
        chancesOfDamageCardBeingInDamageDeck = 0.4f;

        pointCardChances = 0.5f; // must be between 0 and 1
        attackCardChances = 0.25f; // must be between 0 and 1
        freezeCardChances = 0.15f; // must be between 0 and 1

    } // void setGameSettings()


    // Populates the two ArrayLists with random Cards, according to the settings.
    private void generateDecks() {
        float randomValue;
        for (int i = 0; i < totalNumberOfCards; i++) {

            randomValue = Rand.random(); // 0.0 -> 0.999...

            // % chance of creating a point card
            if (randomValue < pointCardChances) {
                mixedDeck.add(new PointCard());
            }

            // % chance of creating an attack card
            else if (randomValue < pointCardChances + attackCardChances) {
                if (Rand.random() < chancesOfDamageCardBeingInDamageDeck) {
                    damageDeck.add(new AttackCard());
                } else {
                    mixedDeck.add(new AttackCard());
                }
            }

            // % chance of creating a freeze card
            else if (randomValue < pointCardChances + attackCardChances + freezeCardChances) {

                if (Rand.random() < chancesOfDamageCardBeingInDamageDeck) {
                    damageDeck.add(new FreezeCard());
                } else {
                    mixedDeck.add(new FreezeCard());
                }
            }

            // % chance of creating a thief card
            else {
                mixedDeck.add(new ThiefCard());
            }

        } // for (i < totalNumberOfCards; i++)

        Collections.shuffle(mixedDeck);
        Collections.shuffle(damageDeck);
    } // void generateDecks()


    public void errorDetections() {

        if (1f < playerChancesOfPlayingCard + playerChancesOfDrawingFromMixedDeck) {
            System.out.println("ERROR: Chances of different player actions are not all positive.");
        }

        // thief card chances should be positive based on the math, but check just to be safe
        if (1f < pointCardChances + attackCardChances + freezeCardChances) {
            System.out.println("ERROR: Card chances are not all positive.");
        }

        if (totalNumberOfCards < startingHandSize * (players.size() + 1)) {
            System.out.println("WARNING: NOT ENOUGH CARDS FOR RECOMMENDED PLAY. ");
        }
    }


    public void registerPlayer(Player player) {
        players.add(player);
    }

    public void run() {
        errorDetections();

        // deal cards to each player
        for (Player player : players) {
            for (int i=0; i < startingHandSize;i++) {
                player.addCardToHand(drawCard(mixedDeck));
            }
        }

        int currentPlayerIndex = -1; // will increase to 0 when the loop starts
        Player currentPlayer;

        // game loop -- loop as long as either deck has cards

        while (!mixedDeck.isEmpty() || !damageDeck.isEmpty()) {

            // switch to next player
            currentPlayerIndex += 1;
            if (currentPlayerIndex >= players.size()){
                currentPlayerIndex = 0;
            }
            currentPlayer = players.get(currentPlayerIndex);

            System.out.println("\n# cards remaining in Mixed deck: " + mixedDeck.size() + ".");
            System.out.println("# cards remaining in Damage deck: " + damageDeck.size() + ".\n");

            System.out.println("It's " + currentPlayer.getName() + "'s turn.\n");
            currentPlayer.displayStatus();
            Input.waitForUserToPressEnter("\nPress Enter to play " + currentPlayer.getName() + "'s turn.");

            // check if the player should be skipped
            if (currentPlayer.isFrozen()) {
                System.out.println(currentPlayer.getName() + " is frozen! Skipping turn.");
                currentPlayer.unfreeze();
                continue; // skips the rest of the body of the loop, and returns to the start of the loop
            }

            // generate a random value to choose a random action
            float randomValue = Rand.random();

            // 1. play a card from player's hand
            if (randomValue < playerChancesOfPlayingCard && currentPlayer.hasCardsInHand()) {
                currentPlayer.playRandomCardFromHand(players);
            }

            // 2. OR draw a card from mixed deck (but don't play it yet)
            else if (damageDeck.isEmpty() || (!mixedDeck.isEmpty() && randomValue < playerChancesOfPlayingCard + playerChancesOfDrawingFromMixedDeck)) {
                Card drawnCard = drawCard(mixedDeck);
                currentPlayer.addCardToHand(drawnCard);

                System.out.println(currentPlayer.getName() + " drew a " + drawnCard + " from the Mixed deck.");
            }

            // 3. OR draw a card from damage deck and use its damage effect immediately, without getting points
            else {
                DealsDamage damageCard = (DealsDamage) drawCard(damageDeck);

                System.out.println(currentPlayer.getName() + " drew a " + damageCard + " from the Damage deck.");

                // pick a random player (but not oneself) to apply the damage card to
                Player otherPlayer;
                do {
                    otherPlayer = players.get(Rand.randomInt(0, players.size()));
                } while (otherPlayer == currentPlayer);

                damageCard.doDamage(currentPlayer, otherPlayer);

                if (damageCard instanceof AppliesFreeze) {
                    AppliesFreeze freezeCard = (AppliesFreeze) damageCard;
                    freezeCard.freeze(currentPlayer, otherPlayer);
                }
            }

            Input.waitForUserToPressEnter("\nPress Enter to end " + currentPlayer.getName() + "'s turn.\n");
        }

        // End game: determine which Player had the most points
        declareWinner();
    }


    private void declareWinner() {
        int highestScore = 0;
        Player playerWithHighestScore = null;

        System.out.println("\nFinal Scoreboard:");
        for (Player p : players) {
            System.out.println(p.getName() + ": " + p.getNumPoints());

            // update the highest score tracker
            if (p.getNumPoints() >= highestScore) {
                highestScore = p.getNumPoints();
                playerWithHighestScore = p;
            }
        }

        System.out.println("Player '" + playerWithHighestScore.getName() + "' wins!");
    }

    public Card drawCard(ArrayList deck) {
        Card card = (Card) deck.getFirst();
        deck.removeFirst();
        return card;
    }
}
