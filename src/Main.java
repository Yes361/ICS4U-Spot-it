import java.util.*;

public class Main {
    /* Declarations for variables related to the Deck */
    static int[][] deck;
    static String[] entries;

    /* Declarations for variables related to Score */
    int highscore = -1;

    /* Declaration of Objects */
    static Scanner input;

    /* Declaration for Console Colors */
    // Copied from https://www.w3schools.blog/ansi-colors-java

    public static final String RESET = "\033[0m";  // Text Reset
    // Regular Colors
    public static final String BLACK = "\033[0;30m";   // BLACK
    public static final String RED = "\033[0;31m";     // RED
    public static final String GREEN = "\033[0;32m";   // GREEN
    public static final String YELLOW = "\033[0;33m";  // YELLOW
    public static final String BLUE = "\033[0;34m";    // BLUE
    public static final String PURPLE = "\033[0;35m";  // PURPLE
    public static final String CYAN = "\033[0;36m";    // CYAN
    public static final String WHITE = "\033[0;37m";   // WHITE
    // Bold
    public static final String BLACK_BOLD = "\033[1;30m";  // BLACK
    public static final String RED_BOLD = "\033[1;31m";    // RED
    public static final String GREEN_BOLD = "\033[1;32m";  // GREEN
    public static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW
    public static final String BLUE_BOLD = "\033[1;34m";   // BLUE
    public static final String PURPLE_BOLD = "\033[1;35m"; // PURPLE
    public static final String CYAN_BOLD = "\033[1;36m";   // CYAN
    public static final String WHITE_BOLD = "\033[1;37m";  // WHITE
    // Underline
    public static final String BLACK_UNDERLINED = "\033[4;30m";  // BLACK
    public static final String RED_UNDERLINED = "\033[4;31m";    // RED
    public static final String GREEN_UNDERLINED = "\033[4;32m";  // GREEN
    public static final String YELLOW_UNDERLINED = "\033[4;33m"; // YELLOW
    public static final String BLUE_UNDERLINED = "\033[4;34m";   // BLUE
    public static final String PURPLE_UNDERLINED = "\033[4;35m"; // PURPLE
    public static final String CYAN_UNDERLINED = "\033[4;36m";   // CYAN
    public static final String WHITE_UNDERLINED = "\033[4;37m";  // WHITE
    // Background
    public static final String BLACK_BACKGROUND = "\033[40m";  // BLACK
    public static final String RED_BACKGROUND = "\033[41m";    // RED
    public static final String GREEN_BACKGROUND = "\033[42m";  // GREEN
    public static final String YELLOW_BACKGROUND = "\033[43m"; // YELLOW
    public static final String BLUE_BACKGROUND = "\033[44m";   // BLUE
    public static final String PURPLE_BACKGROUND = "\033[45m"; // PURPLE
    public static final String CYAN_BACKGROUND = "\033[46m";   // CYAN
    public static final String WHITE_BACKGROUND = "\033[47m";  // WHITE
    // High Intensity
    public static final String BLACK_BRIGHT = "\033[0;90m";  // BLACK
    public static final String RED_BRIGHT = "\033[0;91m";    // RED
    public static final String GREEN_BRIGHT = "\033[0;92m";  // GREEN
    public static final String YELLOW_BRIGHT = "\033[0;93m"; // YELLOW
    public static final String BLUE_BRIGHT = "\033[0;94m";   // BLUE
    public static final String PURPLE_BRIGHT = "\033[0;95m"; // PURPLE
    public static final String CYAN_BRIGHT = "\033[0;96m";   // CYAN
    public static final String WHITE_BRIGHT = "\033[0;97m";  // WHITE
    // Bold High Intensity
    public static final String BLACK_BOLD_BRIGHT = "\033[1;90m"; // BLACK
    public static final String RED_BOLD_BRIGHT = "\033[1;91m";   // RED
    public static final String GREEN_BOLD_BRIGHT = "\033[1;92m"; // GREEN
    public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";// YELLOW
    public static final String BLUE_BOLD_BRIGHT = "\033[1;94m";  // BLUE
    public static final String PURPLE_BOLD_BRIGHT = "\033[1;95m";// PURPLE
    public static final String CYAN_BOLD_BRIGHT = "\033[1;96m";  // CYAN
    public static final String WHITE_BOLD_BRIGHT = "\033[1;97m"; // WHITE
    // High Intensity backgrounds
    public static final String BLACK_BACKGROUND_BRIGHT = "\033[0;100m";// BLACK
    public static final String RED_BACKGROUND_BRIGHT = "\033[0;101m";// RED
    public static final String GREEN_BACKGROUND_BRIGHT = "\033[0;102m";// GREEN
    public static final String YELLOW_BACKGROUND_BRIGHT = "\033[0;103m";// YELLOW
    public static final String BLUE_BACKGROUND_BRIGHT = "\033[0;104m";// BLUE
    public static final String PURPLE_BACKGROUND_BRIGHT = "\033[0;105m"; // PURPLE
    public static final String CYAN_BACKGROUND_BRIGHT = "\033[0;106m";  // CYAN
    public static final String WHITE_BACKGROUND_BRIGHT = "\033[0;107m";   // WHITE

    public static void main(String[] args) {
        input = new Scanner(System.in);

        /* Generate a default deck of cards */
        String[] DefaultItems = {"0", "1", "2", "3", "4", "5", "6"};
        GenerateDeck(3, DefaultItems);

        /* Loading Screen */
        print_progressbar(20, 1000);
        clear();
        init_main_menu();

        /* Main Menu Options */
        main_menu();
    }

    public static void setEntries(String[] items) {
        entries = items;
    }

    /**
     * Prints a Progressbar
     * My take on the progress bar inspried by <a href="https://stackoverflow.com/questions/852665/command-line-progress-bar-in-java">...</a>
     *
     * @param length:   Number of Icons
     * @param interval: Interval between icon loading
     */
    public static void print_progressbar(int length, long interval) {
        char incomplete = '.';
        char complete = '=';

        String progressBar = "";
        for (int i = 0; i < length; i++) {
            progressBar += String.valueOf(incomplete);
        }

        for (int i = 0; i < length; i++) {
            progressBar = progressBar.substring(0, i) + String.valueOf(complete) + progressBar.substring(i + 1);
            System.out.print("\r" + progressBar);
            try {
                Thread.sleep(interval);
            } catch (InterruptedException error) {

            }
        }
        System.out.println(progressBar);
    }

    public static boolean isPrime(int n) {
        for (int i = 2;i < n;i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static int findNthPrime(int n) {
        if (n <= 0) {
            return -1;
        }

        int primesFound = 0;
        int i = 1;
        while (primesFound < n) {
            if (isPrime(++i)) {
                primesFound++;
            }
        }

        return i;
    }

    public static void console_gradient(int interval, int duration) {
        int[] colors = {30, 31, 32, 33, 34, 35, 36, 37, 90, 91, 92, 93, 94, 95, 96, 97};
        int iterations = duration / interval;

        for (int i = 0;i < iterations;i++) {
            for (int color : colors) {
                System.out.printf("\033[0;%dm%s", color, "\rskibidi");

                try {
                    Thread.sleep(interval);
                } catch (InterruptedException error) {

                }
            }
        }
    }

    public static void print_card(int[] elements) {

        int length = elements.length;
        double side = Math.sqrt(length);

        int row = (int) Math.ceil(side);
        int col = (int) Math.round(side);

        int[][] grid = new int[row][col];
        int maxLength = 0;

        for (int i = 0; i < row; i++) {
            int sum = 0;
            for (int j = 0; j < col; j++) {
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
     * Get the total number of images based on the amount of images per card
     */
    public static int getNumberOfImages(int num_of_images_per_card) {
        return (int) Math.pow(num_of_images_per_card, 2) - num_of_images_per_card + 1;
    }

    // dw abt this stuff yet this is poorly written code i wrote on a whim
    public static int[] pickTwoIndices(int length) {
        int first_choice = (int) (Math.random() * length);
        int second_choice;

        do {
            second_choice = (int) (Math.random() * length);
        } while (first_choice == second_choice);

        return new int[]{ first_choice, second_choice };
    }

    // ignore this to
    public static void ShuffleDeck(int iterations) {
        int deckLength = deck.length;

        for (int i = 0; i < iterations; i++) {
            int[] choiceIndex = pickTwoIndices(deckLength);
            int first_choice = choiceIndex[0];
            int second_choice = choiceIndex[1];

            int[] temp = deck[first_choice];
            deck[first_choice] = deck[second_choice];
            deck[second_choice] = temp;
        }
    }

    public static void ShuffleCard(int idx, int iterations) {
        int[] card = deck[idx];
        int deckLength = card.length;

        for (int i = 0; i < iterations; i++) {
            int[] choiceIndex = pickTwoIndices(deckLength);
            int first_choice = choiceIndex[0];
            int second_choice = choiceIndex[1];

            int temp = card[first_choice];
            card[first_choice] = card[second_choice];
            card[second_choice] = temp;
        }
    }

    public static void ShuffleWholeDeck() {
        ShuffleDeck(20);
        for (int i = 0;i < deck.length;i++) {
            ShuffleCard(i, 20);
        }
    }

    /**
     * Generates the deck given the images per card and a list of images
     * For now it will only produce a deck of cards with 3 images per card
     */
    public static void GenerateDeck(int num_of_images_per_card, String[] items) {

        int num_of_cards = getNumberOfImages(num_of_images_per_card);

        try {
            if (items.length < num_of_cards) {
                throw new RuntimeException("Number of cards is less than required");
            }
        } catch (RuntimeException error) {
            System.out.println(error.getMessage());
            return;
        }

        setEntries(items);

        /* Custom Spot it Generation Algorithm Implementation */
        // https://www.101computing.net/the-dobble-algorithm/

        deck = new int[num_of_cards][num_of_images_per_card];
        int primeN = num_of_images_per_card - 1;

        for (int i = 0;i < primeN + 1;i++) {
            deck[i][0] = 0;
            for (int j = 0;j < primeN;j++) {
                deck[i][j + 1] = (j + 1) + i * primeN;
            }
        }

        for (int i = 0;i < primeN;i++) {
            for (int j = 0;j < primeN;j++) {
                int idx = primeN * (i + 1) + j + 1;
                deck[idx][0] = i + 1;

                for (int k = 0;k < primeN;k++) {
                    deck[idx][k + 1] = primeN + 1 + primeN * k + (i * k + j) % primeN;
                }
            }
        }
    }

    public static int FindCommonElement(int firstCard, int secondCard) {
        boolean[] images = new boolean[deck.length];
        int images_per_card = deck[firstCard].length;

        for (int i = 0; i < images_per_card; i++) {
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

    public static int readIntWithInputValidation(String prompt, boolean checkPositive) {
        int num;
        do {
            try {
                num = readInt(prompt);
                input.nextLine();

                if (!checkPositive || num > 0) {
                    break;
                }

                System.out.println("Please input a positive integer");

            } catch (InputMismatchException error) {
                System.out.println("Please input a positive integer");
            }
        } while (true);

        return num;
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

            clear();

            switch (command.toLowerCase()) {
                case "1":
                    System.out.printf("Current number of images per Card is %d\n", current_images_per_card);

                    int new_images_per_card = readIntWithInputValidation("Enter the number of images per card! ", true);

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
                    for (int i = 0; i < entries.length; i++) {
                        System.out.printf("No. %d: %s\n", i + 1, entries[i]);
                    }

                    int index = readIntWithInputValidation("Enter the index of the entry you wish to ", true);
                    String newImage = readLine("What is the new Image? ");
                    entries[index - 1] = newImage;

                    break;
                case "e":
                    continue;
                default:
                    System.out.println("Unknown command");
                    break;
            }

        } while (!command.equalsIgnoreCase("exit") && !command.equalsIgnoreCase("e"));
    }

    public static void normal_mode() {
        int rounds = readIntWithInputValidation(CYAN_BACKGROUND + "How many rounds do you wish to play? ", true);
        int cardLength = deck.length;
        int score = 0;

        for (int i = 0; i < rounds; i++) {
            int[] choiceIndex = pickTwoIndices(cardLength);
            int first_choice = choiceIndex[0];
            int second_choice = choiceIndex[1];

            print_card(first_choice);
            print_card(second_choice);

            String guess = readLine(CYAN_BACKGROUND + "What's the common element? " + BLACK_BACKGROUND).trim();
            String correct_answer = entries[FindCommonElement(first_choice, second_choice)];

            if (guess.equals(correct_answer)) {
                System.out.println(GREEN + "Correct! +1" + RESET);
                score++;
            } else {
                System.out.printf(RED + "Not correct :( The correct answer was %s\n" + RESET, correct_answer);
            }
        }

        System.out.printf(GREEN + "%d / %d Correct\n" + RESET, score, rounds);
    }

    public static void endless_mode() {
        int score = 0;
        int cardLength = deck.length;
        String guess, correct_answer;
        long currentTime = System.currentTimeMillis();

        do {
            int[] choiceIndex = pickTwoIndices(cardLength);
            int first_choice = choiceIndex[0];
            int second_choice = choiceIndex[1];

            print_card(first_choice);
            print_card(second_choice);

            guess = readLine("What's the common element? ").trim();
            correct_answer = entries[FindCommonElement(first_choice, second_choice)];

            if (guess.equals(correct_answer)) {
                score++;
                System.out.println("Correct!");
            } else {
                System.out.printf("Wrong, the correct answer was %s\n", correct_answer);
            }

        } while (guess.equals(correct_answer));

        double time = (double) (System.currentTimeMillis() - currentTime) / 1000;
        System.out.printf("You got %d correct! Completed in %.2f seconds\n", score, time);
    }

    public static void timed_variant(long seconds) {
        int score = 0;
        int cardLength = deck.length;
        String guess, correct_answer;

        try {
            System.out.print("3... ");
            Thread.sleep(1000);
            System.out.print("2... ");
            Thread.sleep(1000);
            System.out.println("1... ");
            Thread.sleep(1000);
            System.out.println("Go!");
        } catch (InterruptedException ignored) {
            System.out.println("Something Unexpected happened!");
            return;
        }

        long previousTime = System.currentTimeMillis();
        double timeElapsed;

        do {
            int[] choiceIndex = pickTwoIndices(cardLength);
            int first_choice = choiceIndex[0];
            int second_choice = choiceIndex[1];

            print_card(first_choice);
            print_card(second_choice);

            guess = readLine("What's the common element? ").trim();
            correct_answer = entries[FindCommonElement(first_choice, second_choice)];

            timeElapsed = (double) (System.currentTimeMillis() - previousTime) / 1000;

            if (timeElapsed > seconds) {
                break;
            }

            System.out.printf("Completed in %.2f. %.2f seconds remaining\n", timeElapsed, seconds - timeElapsed);

            if (guess.equals(correct_answer)) {
                score++;
                System.out.println(GREEN + "Correct!" + RESET);
            } else {
                System.out.printf("Wrong, the correct answer was %s\n", correct_answer);
            }
        } while (true);
    }

    // This is how the user is going to be able to run the spot-it game
    public static void start_game() {
        String command;

        do {
            System.out.println(WHITE_UNDERLINED + WHITE_BOLD_BRIGHT + "Game Modes" + RESET);
            System.out.println(RED_BACKGROUND + WHITE_BOLD_BRIGHT + "Normal Mode [1]" + RESET);
            System.out.println(BLUE_BACKGROUND + WHITE_BOLD_BRIGHT + "Endless Mode [2]" + RESET);
            System.out.println(YELLOW_BACKGROUND + WHITE_BOLD_BRIGHT + "Timed Variant [3]" + RESET);
            System.out.println("Quit to previous [Q]");
            System.out.println();

            command = input.nextLine();

            // TODO: add arguments for difficulty
            switch (command.toLowerCase()) {
                case "1":
                    normal_mode();
                    break;
                case "2":
                    endless_mode();
                    break;
                case "3":
                    timed_variant(20);
                    break;
                case "quit", "q":
                    continue;
                default:
                    System.out.println("Unknown Command");
                    break;
            }

            clear();
        } while (!command.equalsIgnoreCase("quit") && !command.equalsIgnoreCase("q"));
    }

    /**
     * Main Menu
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
                case "quit", "q":
                    System.out.println("why u leave :(");
                    break;
                default:
                    System.out.println("Unknown Command");
                    break;
            }

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
}