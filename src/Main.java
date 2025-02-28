import java.util.*;
import java.io.*;

public class Main {
    /* Declarations for variables related to the Deck */
    static int[][] deck;
    static String[] entries;

    /* Declarations for variables related to Score */
    int highscore = -1;

    /* Declaration of Objects */
    static Scanner input;

    /* Declaration for Console Colors */
    public static final String RESET = "\033[0m";
    public static final String YELLOW = "\033[0;33m";
    public static final String PURPLE_BOLD_BRIGHT = "\033[1;95m";

    public static void main(String[] args) {
        input = new Scanner(System.in);

        /* Generate a default deck of cards */
        String[] DefaultItems = {"a", "b", "c", "d", "e", "f", "g"};
        GenerateDeck(3, DefaultItems);

        /* Menu Methods */
        init_main_menu();
        main_menu();

    }

    public static void print_card(int[] elements) {
//        ArrayList<Integer> list = new ArrayList<Integer>(Arrays.asList(elements));

        int length = elements.length;
        double side = Math.sqrt(length);

        int row = (int) Math.ceil(side);
        int col = (int) Math.round(side);

        int[][] grid = new int[row][col];
        int maxLength = 0;

        for (int i = 0;i < row;i++) {
            int sum = 0;
            for (int j = 0;j < col;j++) {
                int index = i * row + col;

                int entryIndex = elements[index];
                grid[i][j] = entryIndex;

                sum += entries[entryIndex].length();
            }

            maxLength = Math.max(maxLength, sum);
        }





    }

    /* Helper Methods */

    /**
     * Clear Screen
     */
    public static void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * readInt prompts the user for an int
     */
    public static int readInt(String prompt) {
        System.out.print(prompt);
        return input.nextInt();
    }

    /**
     * readInt prompts the user for a String
     */
    public static String readLine(String prompt) {
        System.out.print(prompt);
        return input.nextLine();
    }

    /**
     *  Get the total number of images based on the amount of images per card
     */
    public static int getNumberOfImages(int num_of_images_per_card) {
        return (int) Math.pow(num_of_images_per_card, 2) - num_of_images_per_card + 1;
    }

    public static void print_header_msg() {
        System.out.println("---------------------------------------");
    }

    // dw abt this stuff yet this is poorly written code i wrote on a whim
    public static int pickIndex(boolean[] selected) {
        int index;
        int length = selected.length;

        do {
            index = (int)(Math.random() * length);
        } while (selected[index]);

        return index;
    }

    // ignore this to
    public static void ShuffleDeck(boolean withRemoval, int iterations) {
        boolean[] selected = new boolean[deck.length];
        int second = pickIndex(selected);

        if (withRemoval) {
            iterations = deck.length / 2;
        }

        for (int i = 0;i < iterations;i++) {
            int first = pickIndex(selected);
            selected[first] = true;

            int cardLength = deck[0].length;

            for (int idx = 0;idx < cardLength;idx++) {
                int a = deck[first][idx];
                deck[first][idx] = deck[second][idx];
                deck[second][idx] = a;
            }

            if (withRemoval) {
                selected[second] = true;
            } else {
                selected[first] = false;
            }
        }
    }

    // this too
    public static void ShuffleCard(int card, boolean withRemoval, int iterations) {
        boolean[] selected = new boolean[deck[card].length];
        int cardLength = deck[card].length;

        if (withRemoval) {
            iterations = deck[card].length / 2;
        }

        for (int i = 0;i < iterations;i++) {
            int first = pickIndex(selected);
            selected[first] = true;

            int second = pickIndex(selected);

            int a = deck[card][first];
            deck[card][first] = deck[card][second];
            deck[card][second] = a;

            if (withRemoval) {
                selected[second] = true;
            } else {
                selected[first] = false;
            }
        }
    }

    /**
     * Generates the deck given the images per card and a list of images
     * For now it will only produce a deck of cards with 3 images per card
     */
    public static void GenerateDeck(int num_of_images_per_card, String[] items) {

        int num_of_cards = getNumberOfImages(num_of_images_per_card);

        deck = new int[][]{
            {0, 1, 2},
            {0, 3, 4},
            {0, 5, 6},
            {1, 3, 5},
            {1, 4, 6},
            {2, 4, 5},
            {2, 3, 6},
        };

        entries = items;
    }

    public static int FindCommonElement(int firstCard, int secondCard) {
        boolean[] images = new boolean[deck.length];
        int images_per_card = deck[firstCard].length;

        for (int i = 0;i < images_per_card;i++) {
            int firstImage = deck[firstCard][i];
            int secondImage = deck[secondCard][i];

            if (images[firstImage]) {
                return firstImage;
            }
            images[firstImage] = true;

            if (images[secondImage]) {
                return secondImage;
            }
            images[secondImage] = true;
        }

        return -1;
    }

    /**
     * Loading screen
     * TODO: We can add cool effects to make the laoding screen cool
     */
    public static void init_main_menu() {
        System.out.println(
            """
              _________              __    .__  __  \s
             /   _____/_____   _____/  |_  |__|/  |_\s
             \\_____  \\\\____ \\ /  _ \\   __\\ |  \\   __\\
             /        \\  |_> >  <_> )  |   |  ||  | \s
            /_______  /   __/ \\____/|__|   |__||__| \s
                    \\/|__|                          \s
            __________________________________________
            """
        );
    }
    // lmao
    // IGNORE THIS DEFINETLY IGNORE THIS
    public static void edit_cards() {
        String command;

        do {
            int current_images_per_card = deck[0].length;

            System.out.println("Enter new Cards [1]");
            System.out.println("Modify Entry [2]");
            System.out.println("Exit [E]");

            command = input.nextLine();

            print_header_msg();

            switch (command.toLowerCase()) {
                case "1":
                    System.out.printf("Current number of images per Card is %d\n", current_images_per_card);
                    int new_images_per_card = readInt("Enter the number of images per card! ");
                    input.nextLine();

                    int num_of_cards = getNumberOfImages(new_images_per_card);

                    System.out.printf("Input %d Cards\n", num_of_cards);

                    String[] items = new String[num_of_cards];
                    for (int i = 0; i < num_of_cards; i++) {
                        do {
                            System.out.printf("No. %d: ", i + 1);
                            items[i] = input.nextLine();
                        } while (items[i].isEmpty());
                    }

                    String response = readLine("Save Selection? [y/n]\n");
                    if (response.equalsIgnoreCase("y")) {
                        GenerateDeck(new_images_per_card, items);
                    }

                    break;
                case "2":
                    System.out.println("Current Image Selection:");
                    for (int i = 0;i < entries.length;i++) {
                        System.out.printf("No. %d: %s\n", i + 1, entries[i]);
                    }

                    int index = readInt("Modify Image: ") - 1;
                    input.nextLine();
                    String newImage = readLine("What is the new Image? ");
                    entries[index] = newImage;

                    break;
                case "e":
                    continue;
                default:
                    System.out.println("Unknown command");
                    break;
            }

        } while (!command.equalsIgnoreCase("exit") && !command.equalsIgnoreCase("e"));
    }

    // This is how the user is going to be able to run the spot-it game
    public static void start_game() {
        String command;
        int cards = deck.length;

        do {
            System.out.println("Game Modes");
            System.out.println("Normal Mode [1]");
            System.out.println("Endless Mode [2]");
            System.out.println("Timed Variant [3]");
            System.out.println("Quit to previous [Q]");
            System.out.println();

            command = input.nextLine();

            int score;
            switch (command.toLowerCase()) {
                case "1":
                    int rounds = readInt("How many rounds? ");
                    input.nextLine();

                    score = 0;

                    for (int i = 0;i < rounds;i++) {
                        int first = (int) (Math.random() * cards);
                        int second;
                        do {
                            second = (int) (Math.random() * cards);
                        } while (first == second);

                        print_card(first);
                        print_card(second);

                        String guess = readLine("What's the common element? ").trim();
                        String correct_answer = entries[FindCommonElement(first, second)];

                        if (guess.equals(correct_answer)) {
                            System.out.println("Correct! +1");
                            score++;
                        } else {
                            System.out.printf("Not correct :( The correct answer was %s\n", correct_answer);
                        }
                    }

                    System.out.printf("%d / %d Correct\n", score, rounds);

                    break;
                case "2":

                    score = 0;
                    String guess, correct_answer;
                    long currentTime = System.currentTimeMillis();

                    do {
                        int first = (int) (Math.random() * cards);
                        int second;

                        do {
                            second = (int) (Math.random() * cards);
                        } while (first == second);

                        print_card(first);
                        print_card(second);

                        guess = readLine("What's the common element? ").trim();
                        correct_answer = entries[FindCommonElement(first, second)];

                        if (guess.equals(correct_answer)) {
                            score++;
                            System.out.println("Correct!");
                        } else {
                            System.out.printf("Wrong, the correct answer was %s\n", correct_answer);
                        }

                    } while (guess.equals(correct_answer));

                    double time = (double) (System.currentTimeMillis() - currentTime) / 1000;
                    System.out.printf("You got %d correct! Completed in %.2f seconds\n", score, time);

                    break;
                case "3":
                    break;
                case "quit", "q":
                    continue;
                default:
                    System.out.println("Unknown Command");
                    break;
            }

            input.nextLine();
            clear();
        } while (!command.equalsIgnoreCase("quit") && !command.equalsIgnoreCase("q"));
    }

    /**
     * Main Menu
     *
     */
    public static void main_menu() {
        String command;

        do {

            // Main menu Options
            System.out.println("Menu Options");
            System.out.println("Rules [1]");
            System.out.println("Scores [2]");
            System.out.println("New Game [3]");
            System.out.println("Edit Cards [4]");
            System.out.println("Quit [Q]");
            System.out.println();

            // Read in the command
            command = input.nextLine();

            // Check command against the available options
            switch (command.toLowerCase()) {
                case "1":
                    clear();
                    System.out.println("Spot it is a skibidi game");
                    break;
                case "3":
                    clear();
                    start_game();
                    break;
                case "4":
                    edit_cards();
                    break;
                case "debug":
                    debug_console();
                    break;
                case "quit", "q":
                    System.out.println("why u leave :(");
                    break;
                default:
                    System.out.println("Unknown Command");
                    break;
            }

            input.nextLine();
            clear();
        } while (!command.equalsIgnoreCase("quit") && !command.equalsIgnoreCase("q")); // Continue if the command isn't "quit"
    }

    // Print out a card

    public static void print_card(int idx) {
        for (int index : deck[idx]) {
            System.out.printf("%s, ", entries[index]);
        }
        System.out.println();
    }

    // Helper method to print out the deck
    public static void print_deck() {
        // btw this is
        for (int[] indices : deck) {
            for (int index : indices) {
                System.out.printf("%s, ", entries[index]);
            }
            System.out.println();
        }
    }

    // This is mainly for testing, this function helps with debugging the program
    // For example I can easily test deck shuffling, printing, and card generation
    // without having to go through the actual game itself
    // typically the user shouldn't have access to this
    public static void debug_console() {
        String command;

        System.out.println("Entering Debug Mode");

        do {
            // Debug Options
            System.out.println("Generate a new deck of Cards [1]");
            System.out.println("Shuffle Deck w/ Removal [2]");
            System.out.println("Shuffle Deck w/o Removal [3]");
            System.out.println("Shuffle Card [4]");
            System.out.println("Print current deck [5]");
            System.out.println("Find the common element [6]");
            System.out.println("Exit [E]");

            command = input.nextLine();

            switch (command.toLowerCase()) {
                case "generate", "1":
                    int n = getNumberOfImages(3);

                    System.out.printf("Input %d Cards\n", n);

                    String[] items = new String[n];
                    for (int i = 0;i < n;i++) {
                        items[i] = input.nextLine();
                    }

                    System.out.println("Done!");

                    GenerateDeck(3, items);
                    break;
                case "shufflewithremoval", "2":
                    ShuffleDeck(true, -1);
                    break;
                case "shuffledeck", "3":
                    ShuffleDeck(false, 50);
                    break;
                case "shufflecard", "4":

                    System.out.println("Select a Card to Shuffle");
                    int card = input.nextInt();
                    input.nextLine();

                    ShuffleCard(card, false, 3);
                    break;
                case "print", "5":
                    print_deck();
                    break;
                case "findcommon", "6":
                    int first = readInt("first card: ") - 1;
                    input.nextLine();
                    int second = readInt("second card: ") - 1;
                    input.nextLine();

                    System.out.println(entries[FindCommonElement(first, second)]);
                    break;
                case "exit", "e":
                    continue;
                default:
                    System.out.println("Unknown Command");
                    break;
            }

            input.nextLine();
            System.out.println();
        } while (!command.equalsIgnoreCase("exit") && !command.equalsIgnoreCase("e"));

        System.out.println("Exiting Debug Console...");
    }
}
