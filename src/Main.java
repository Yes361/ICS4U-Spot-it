import java.util.*;

public class Main {
    /* Declarations for variables related to the Deck */

    // the Deck is a static 2d array of ints, each int symbolizes a symbol which is stored in entries
    static int[][] deck;
    static String[] entries;

    /* Declarations for variables related to Score */
    int normalModeHighscore = -1;
    int endlessModeHighscore = -1;
    int timedVariantHighscore = -1;

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
        System.out.println(
            """
              _________              __    .__  __ \s
             /   _____/_____   _____/  |_  |__|/  |_\s
             \\_____  \\\\____ \\ /  _ \\   __\\ |  \\   __\\
             /        \\  |_> >  <_> )  |   |  ||  | \s
            /_______  /   __/ \\____/|__|   |__||__| \s
                    \\/|__|                          \s
            __________________________________________
           \s"""
        );

        /* Main Menu Options */
        main_menu();
    }

    // Setter function for entries
    public static void setEntries(String[] items) {
        entries = items;
    }

    /* Miscellaneous Helper Methods */

    /**
     * Prints a Progressbar
     * My take on the progress bar inspired by <a href="https://stackoverflow.com/questions/852665/command-line-progress-bar-in-java">...</a>
     * Inspired by Qazi
     *
     * @param length:   Number of Icons
     * @param interval: Interval between icon loading
     */
    public static void print_progressbar(int length, long interval) {
        char incomplete = '.';
        char complete = '=';

        // Create a string of incomplete chars
        String progressBar = "";
        for (int i = 0; i < length; i++) {
            // Stringbuilder would be better here but I can't be bothered to read documentation
            progressBar += String.valueOf(incomplete);
        }

        // Iteratively update it to fill with complete chars every interval
        for (int i = 0; i < length; i++) {
            progressBar = progressBar.substring(0, i) + String.valueOf(complete) + ">" + progressBar.substring(i + 2);
            System.out.print("\r" + progressBar);

            try {
                Thread.sleep(interval); // pause the thread
            } catch (InterruptedException error) {

            }
        }
        System.out.println(progressBar);
    }

    /**
     * Helper method to determine whether a given value is prime
     *
     * @param n Given value
     *
     * @return Boolean value indicating whether n is prime
     */
    public static boolean isPrime(int n) {
        // iterate from 2...n - 1, checking if n is divisible by it
        for (int i = 2;i < n;i++) {
            // return false if n is divisible by i (a number that is not 1 or n itself)
            if (n % i == 0) {
                return false;
            }
        }

        // return true if we don't find a divisor
        return true;
    }

    /**
     * Helper method to determine the nth prime
     *
     * @param n Nth prime
     *
     * @return Returns the nth prime
     */
    public static int findNthPrime(int n) {
        // Throw an exception if n is non-positive
        if (n <= 0) {
            throw new IllegalArgumentException("Can not find the nth prime when n is <= 0");
        }

        // declarations
        int primesFound = 0;
        int current_num = 1;

        // increment current_num until we've found n primes
        while (primesFound < n) {
            if (isPrime(++current_num)) {
                primesFound++; // increment primesFound when we, guess, find a prime!
            }
        }

        return current_num;
    }

    // I wanted to create a console gradient effect
    public static void console_gradient(String message, int interval, int duration) {
        int[] colors = {30, 31, 32, 33, 34, 35, 36, 37, 90, 91, 92, 93, 94, 95, 96, 97};
        int iterations = duration / interval;

        for (int i = 0;i < iterations;i++) {
            for (int color : colors) {
                System.out.printf("\033[0;%dm\r%s", color, message);

                try {
                    Thread.sleep(interval);
                } catch (InterruptedException error) {

                }
            }
        }
    }

    // AHNAF THIS IS UR SECTION
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

    /**
     * Clear Screen
     */
    public static void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Prompts the user for an int
     *
     *
     */
    public static int readInt(String prompt) {
        System.out.print(prompt);
        return input.nextInt();
    }

    /**
     * Prompts the user for a String
     */
    public static String readLine(String prompt) {
        System.out.print(prompt);
        return input.nextLine();
    }

    /* Spot it Helper Methods */

    /**
     * Get the total number of images based on the amount of images per card
     *
     * @param images_per_card Symbols per card
     *
     * @return Number of cards and total number of symbols required
     */
    public static int getNumberOfImages(int images_per_card) {
        return (int) Math.pow(images_per_card, 2) - images_per_card + 1;
    }

    /**
     * Picks two random unique indices
     *
     * @param length The length of the array to choose indices from
     *
     * @return An array of ints [0] - first choice and [1] - second choice
     */
    public static int[] pickTwoIndices(int length) {
        // Determine the first choice
        int first_choice = (int) (Math.random() * length);
        int second_choice;

        // Determine the second choice but only if it doesn't match the first choice
        do {
            second_choice = (int) (Math.random() * length);
        } while (first_choice == second_choice);

        // Return an array of the choices
        return new int[]{ first_choice, second_choice };
    }

    /**
     * A method to shuffle the complete deck
     *
     * @param iterations Number of iterations to swap indices
     */
    public static void ShuffleDeck(int iterations) {
        for (int i = 0; i < iterations; i++) {
            // Retrive two random indices
            int[] choiceIndex = pickTwoIndices(deck.length);
            int first_choice = choiceIndex[0];
            int second_choice = choiceIndex[1];

            // Swapping card positions in the deck
            int[] temp = deck[first_choice];
            deck[first_choice] = deck[second_choice];
            deck[second_choice] = temp;
        }
    }

    public static void ShuffleCard(int card_id, int iterations) {
        int[] card = deck[card_id];

        for (int i = 0; i < iterations; i++) {
            // Get the wchoices
            int[] choiceIndex = pickTwoIndices(card.length);
            int first_choice = choiceIndex[0];
            int second_choice = choiceIndex[1];

            // Swapping symbols in the card
            int temp = card[first_choice];
            card[first_choice] = card[second_choice];
            card[second_choice] = temp;
        }
    }

    /**
     * Shuffles the whole deck including each card
     */
    public static void ShuffleWholeDeck() {
        int ArbitrayIterations = 20;
        ShuffleDeck(ArbitrayIterations);
        for (int i = 0;i < deck.length;i++) {
            ShuffleCard(i, ArbitrayIterations);
        }
    }

    /**
     * Generates the deck given the images per card and a list of images
     * For now it will only produce a deck of cards with 3 images per card
     */
    public static void GenerateDeck(int images_per_card, String[] items) {

        // Check if the number of images is 1 more than a prime number
        if (!isPrime(images_per_card - 1)) {
            throw new RuntimeException("Number of symbols is not 1 more than a prime number");
        }

        int num_of_cards = getNumberOfImages(images_per_card);

        // Check if the number of given images is less than required
        if (items.length < num_of_cards) {
            throw new RuntimeException("Number of cards is less than required");
        }

        setEntries(items);

        /* Custom Spot it Generation Algorithm Implementation */
        // Inspired by https://www.101computing.net/the-dobble-algorithm/
        /* Algorithm Explanation
            TODO: fill in this
        */

        // Initialize new deck size
        deck = new int[num_of_cards][images_per_card];
        int primeN = images_per_card - 1;

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

    /**
     * Finds the common element between two cards
     *
     * @param firstCard Index of the first card in the deck
     * @param secondCard Index of the second card in the deck
     */
    public static int FindCommonElement(int firstCard, int secondCard) {
        /*
            Algorithm Description
            Initialize an array of booleans with a length of the number of symbols in the deck

            We iterate through each index of each card and since the deck/card
            contains an index from 0...(# of Symbols - 1) we can set the corresponding index in the array of booleans
            to true, when we have to set a value that is already true, that means we've already encountered this symbol.
            This algorithm will always return the first instance of a common match and given that there's only ever one match between
            cards in spot it, what could possibly go wrong!
        */
        boolean[] images = new boolean[deck.length];
        int images_per_card = deck[firstCard].length;

        for (int i = 0; i < images_per_card; i++) {
            int firstImage = deck[firstCard][i];
            int secondImage = deck[secondCard][i];

            // Check if we've encountered this symbol before
            if (images[firstImage]) {
                return firstImage;
            }
            images[firstImage] = true; // set this index to true indicating that we have encountered it

            if (images[secondImage]) {
                return secondImage;
            }
            images[secondImage] = true;
        }

        // statement for resolving control flow warnings
        return -1;
    }

    /**
     * Prompt user for an int and perform basic input validation checks which include
     * input mismatch and whether the value is non-positive or non-negative
     *
     * @param prompt Prompt to provide the user
     * @param checkPositive If true will prompt the user for only positive values
     *
     */
    public static int readIntWithInputValidation(String prompt, boolean checkPositive) {
        int num;

        do {
            // try/catch statement to catch InputMismatchExceptions from the scanner
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
        } while (true); // I know this is bad practice...

        return num;
    }

    /* Game Mode Methods */
    // TODO: Add more game modes
    // TODO: Add difficulty settings
    // TODO: Add score calculations

    /**
     *  Normal Mode
     */
    public static void normal_mode(int rounds) {
        int score = 0;

        for (int i = 0; i < rounds; i++) {
            int[] choiceIndex = pickTwoIndices(deck.length);
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

        System.out.printf("You ran out of time! You got a score of %d\n", score);
    }

    /* Menu Methods */

    public static void view_score() {

    }

    /**
     * Menu Method for Game Modes
     */
    public static void start_game() {
        String command;

        do {
            // Print Menu Options
            System.out.println(WHITE_UNDERLINED + WHITE_BOLD_BRIGHT + "Game Modes" + RESET);
            System.out.println(RED_BACKGROUND + WHITE_BOLD_BRIGHT + "Normal Mode [1]" + RESET);
            System.out.println(BLUE_BACKGROUND + WHITE_BOLD_BRIGHT + "Endless Mode [2]" + RESET);
            System.out.println(YELLOW_BACKGROUND + WHITE_BOLD_BRIGHT + "Timed Variant [3]" + RESET);
            System.out.println("Quit to previous [Q]");
            System.out.println();

            // Recieve input
            command = input.nextLine();
            switch (command.toLowerCase()) {
                case "1":
                    int rounds = readIntWithInputValidation(CYAN_BACKGROUND + "How many rounds do you wish to play? ", true);
                    normal_mode(rounds);
                    break;
                case "2":
                    endless_mode();
                    break;
                case "3":
                    timed_variant(20);
                    break;

                // Continue if the command is an exit
                case "quit", "q":
                    continue;

                // Handle default case
                default:
                    System.out.println("Unknown Command");
                    break;
            }

            // Clear console after every match
            clear();

        // End when the command matches "quit"
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
                case "2":

                    break;
                case "3":
                    clear();
                    start_game();
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
}