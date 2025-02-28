import java.util.*;
import java.io.*;

public class Main {
    static String[][] deck;
    static Scanner input;

    public static final String RESET = "\033[0m";
    public static final String YELLOW = "\033[0;33m";
    public static final String PURPLE_BOLD_BRIGHT = "\033[1;95m";

    public static void main(String[] args) {
        input = new Scanner(System.in);

        String[] DefaultItems = {"a", "b", "c", "d", "e", "f", "g"};

        // Running methods
        init_main_menu();
        main_menu();
    }

    public static int getNumberOfImages(int num_of_images_per_card) {
        return (int) Math.pow(num_of_images_per_card, 2) - num_of_images_per_card + 1;
    }

    public static int pickIndex(boolean[] selected) {
        int index = -1;
        int length = selected.length;

        do {
            index = (int)(Math.random() * length);
        } while (selected[index]);

        return index;
    }

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
                String a = deck[first][idx];
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

            String a = deck[card][first];
            deck[card][first] = deck[card][second];
            deck[card][second] = a;

            if (withRemoval) {
                selected[second] = true;
            } else {
                selected[first] = false;
            }
        }
    }

    public static void GenerateDeck(int num_of_images_per_card, String[] items) {

        int num_of_cards = getNumberOfImages(num_of_images_per_card);

//        int[][] deckIndex = new int[num_of_cards][num_of_images_per_card];

        int[][] deckIndex = {
            {0, 1, 2},
            {0, 3, 4},
            {0, 5, 6},
            {1, 3, 5},
            {1, 4, 6},
            {2, 3, 6},
            {2, 4, 5},
        };


        deck = new String[num_of_cards][num_of_images_per_card];

        for (int i = 0;i < deck.length;i++) {
            for (int j = 0;j < deck[i].length;j++) {
                int idx = deckIndex[i][j];
                deck[i][j] = items[idx];
            }
        }
    }

    /**
     * Loading screen
     */
    public static void init_main_menu() {
        System.out.println(
            "  _________              __    .__  __   \n" +
            " /   _____/_____   _____/  |_  |__|/  |_ \n" +
            " \\_____  \\\\____ \\ /  _ \\   __\\ |  \\   __\\\n" +
            " /        \\  |_> >  <_> )  |   |  ||  |  \n" +
            "/_______  /   __/ \\____/|__|   |__||__|  \n" +
            "        \\/|__|                           \n" +
            "__________________________________________\n"
        );
    }

    public static void edit_cards() {
        int current_images_per_card = deck[0].length;
    }

    /**
     * Main Menu
     *
     */
    public static void main_menu() {
        String command;

        do {

            System.out.println("Menu Options");
            System.out.println("Rules [1]");
            System.out.println("Scores [2]");
            System.out.println("New Game [3]");
            System.out.println("Edit Cards [4]");
            System.out.println();

            command = input.nextLine().toLowerCase();

            switch (command) {
                case "new game", "3":
                    System.out.println("toilet");
                    break;
                case "debug":
                    debug_console();
                    break;
                case "quit":
                    System.out.println("why u leave :(");
                    break;
                default:
                    System.out.println("Unknown Command");
                    break;
            }
            System.out.println();
        } while (!command.equals("quit"));
    }

    public static void print_deck() {
        for (String[] strings : deck) {
            for (String string : strings) {
                System.out.printf("%s, ", string);
            }
            System.out.println();
        }
    }

    public static void debug_console() {
        String command;

        System.out.println("Entering Debug Mode");

        do {
            command = input.nextLine().toLowerCase();

            System.out.println("Generate a new deck of Cards [1]");
            System.out.println("Shuffle Deck w/ Removal [2]");
            System.out.println("Shuffle Deck w/o Removal [3]");
            System.out.println("Shuffle Card [4]");
            System.out.println("Print current deck [5]");
            System.out.println("Exit [E]");

            switch (command) {
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
                default:
                    System.out.println("Unknown Command");
                    break;
            }
            System.out.println();
        } while (!command.equals("exit") && !command.equals("e"));

        System.out.println("Exiting Debug Console...");
    }
}
