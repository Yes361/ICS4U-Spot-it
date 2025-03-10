/*
    Authors: Raiyan Islam and Ahnaf Masud
    Date: 03/07/2025
    Program Name: Spot it
    Description: This program is a digital recreation of the popular card game known as Spot It and also Doppler,
    in which players attempt to find a common element between two cards as quickly as possible
*/

import java.util.*;

public class Main {
    /* Declarations for variables related to the Deck */

    // the Deck is a static 2d array of ints, each int symbolizes a symbol which is stored in entries
    static int[][] deck;
    static String[] symbols;

    /* Declarations for variables related to Score */

    // Default initialized to -1 to represent that a score wasn't obtained
    static double normalModeHighscore = -1;
    static double endlessModeHighscore = -1;
    static double timedVariantHighscore = -1;
    static double fastestCompleted = -1;

    /* Declarations for Stopwatch Variables */

    static long stopwatch_start_time;
    static long lapTime;
    static long pauseTime;
    static long pauseOffset;
    static boolean paused;

    /* Declaration of Objects */
    static Scanner input;

    public enum Difficulty {
        SUPER_HARD,
        HARD,
        INTERMEDIATE,
        EASY,
        CUSTOM,
        RANDOM,
    }

    static int ARBITRARY_SIZE_LIMIT = 8; // Arbitrarily chosen because I can and I am the God of this program

    static int current_theme_selector = 0; // points to default

    // this list was compiled by chatgpt, because I can't think of themes
    static String[] random_tools_theme = {
            "Hammer", "Wrench", "Saw", "Drill", "Nail",
            "Screw", "Bolt", "Pliers", "Tape", "Clamp",
            "Brush", "Chisel", "Ruler", "Level", "Hook",
            "Chain", "Glove", "Wheel", "Ladder", "Rope",
            "Tape", "Key", "Lock", "Bell", "Cane",
            "Box", "Book", "Dice", "Card", "Coin",
            "Mug", "Cup", "Plate", "Fork", "Spoon",
            "Bowl", "Lid", "Knife", "Broom", "Mop",
            "Lamp", "Torch", "Bulb", "Fan", "Radio",
            "Phone", "Clock", "Watch", "Pen", "Pencil",
            "Eraser", "Paper", "Brush", "Bag", "Hat",
            "Sock", "Boot", "Ring", "Chain"
    };

    /* Declaration for Console Colors */
    // Copied from https://www.w3schools.blog/ansi-colors-java

    public static final String RESET = "\033[0m";  // Text Reset
    // Bold
    public static final String WHITE_BOLD = "\033[1;37m";  // WHITE
    // Underline
    public static final String RED_UNDERLINED = "\033[4;31m";    // RED
    public static final String YELLOW_UNDERLINED = "\033[4;33m"; // YELLOW
    public static final String BLUE_UNDERLINED = "\033[4;34m";   // BLUE
    public static final String WHITE_UNDERLINED = "\033[4;37m";  // WHITE
    // Background
    public static final String RED_BACKGROUND = "\033[41m";    // RED
    public static final String GREEN_BACKGROUND = "\033[42m";  // GREEN
    public static final String YELLOW_BACKGROUND = "\033[43m"; // YELLOW
    public static final String BLUE_BACKGROUND = "\033[44m";   // BLUE
    public static final String PURPLE_BACKGROUND = "\033[45m"; // PURPLE
    public static final String CYAN_BACKGROUND = "\033[46m";   // CYAN
    // High Intensity
    public static final String CYAN_BRIGHT = "\033[0;96m";   // CYAN
    // Bold High Intensity
    public static final String RED_BOLD_BRIGHT = "\033[1;91m";   // RED
    public static final String GREEN_BOLD_BRIGHT = "\033[1;92m"; // GREEN
    public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";// YELLOW
    public static final String BLUE_BOLD_BRIGHT = "\033[1;94m";  // BLUE
    public static final String WHITE_BOLD_BRIGHT = "\033[1;97m"; // WHITE

    // Storing the game rules here
    static String game_rules = """
    Spot it in its simplest form is a game where you compare two cards from a deck to identify what
    common element you have. For instance, if you have two cards with four elements each as shown before:
    \s
    Card 1: a, b, c, d
    Card 2: e, f, d, g
    \s
    Common element : d
    \s
    There are a few game mode in this, the various game modes include:
    \s
    Normal Mode: The traditional rules of the game, play a set number of rounds, and for each round you get correctly, you earn a point
    Endless Mode: Keep going until failure! This gamemode continues on until the player gets an incorrect score, in which the game exits!
    Timed Variant: Given a set amount of time, you must complete as many rounds as possible until the timer is up!
    \s
    The difficulty modes include
    \s
    Easy: Small sized deck, longer time limits
    Intermediate: Moderate sized deck, balanced time limits
    Hard: Larger deck size, stricter time limits
    Super Hard: Maximum difficult, very large deck, minimal time limit
    Random: Randomly generates a deck size and or time limit
    Custom: You are in control of the deck size and or time limit!\s
    \s
    For every round you get correct you get a point and at the end you might get a time bonus.
    \s
    Good luck and have fun!
    \s""";

    // Main Method !!!
    public static void main(String[] args) {
        input = new Scanner(System.in); // Initialize input

        // Generate Default deck
        GenerateDeck(3);

        /* Main Menu */
        String command; // Command

        do {
            clearWithHeader();

            // Main menu Options
            System.out.println(WHITE_BOLD_BRIGHT + WHITE_UNDERLINED + "Menu Options" + RESET);
            System.out.println(GREEN_BACKGROUND + "New Game [1]" + RESET);
            System.out.println(BLUE_BACKGROUND + "Scores [2]" + RESET);
            System.out.println(RED_BACKGROUND + "Rules [3]" + RESET);
            System.out.println(YELLOW_BACKGROUND + "Choose Deck Theme [4]" + RESET);
            System.out.println(CYAN_BACKGROUND + "Debug [D]" + RESET);
            System.out.println(PURPLE_BACKGROUND + "Quit [Q]" + RESET);
            System.out.println();

            // Read in the command
            command = readLine("(Enter the Command) ");

            // Check command against the available options
            switch (command.toLowerCase()) {
                case "1":
                    start_game(); // Goes to the gamemode menu
                    break;
                case "2":
                    view_score(); // Goes to the highscore screen
                    readLine("(Press enter to continue) ");
                    break;
                case "3":
                    // Rules section
                    clearWithHeader();
                    System.out.println(WHITE_UNDERLINED + WHITE_BOLD_BRIGHT + "Rules\n" + RESET + game_rules);

                    readLine("(Press enter to continue) ");
                    break;
                case "4":
                    // Choosing a theme !
                    String[] theme_list = {"default", "tools"}; // list of availabe themes

                    // Print current theme
                    System.out.printf("The current theme is" + WHITE_BOLD_BRIGHT + " %s\n\n" + RESET, theme_list[current_theme_selector]);

                    // Prompt the user to select from the list of options
                    current_theme_selector = select_option("(Select a theme) ", theme_list);

                    String current_theme = theme_list[current_theme_selector];
                    System.out.println();

                    // Set theme
                    if (current_theme.equals("tools")) {
                        symbols = random_tools_theme;
                    }

                    // Restate the new theme
                    System.out.printf("Theme set to" + WHITE_BOLD_BRIGHT + " %s\n" + RESET, current_theme);
                    readLine("(Press enter to continue) ");
                    break;
                case "d":
                    debug_console(); // Goes to the debug console
                    break;
                case "q":
                    System.out.println("Why you leave :(");
                    return;
                default:
                    // Handles unknown command
                    readLine("Unknown Command (Press enter to continue) ");
                    break;
            }

        } while (!command.equalsIgnoreCase("q")); // Exits when the command is "q" or "Q"
    }

    public static void view_score() {
        clearWithHeader();
        String highscore = "Highscore: " + GREEN_BOLD_BRIGHT + "%.2f\n" + RESET; // Highscore string template

        /* Normal mode highscore */
        System.out.println(RED_BOLD_BRIGHT + RED_UNDERLINED + "Normal Mode" + RESET);
        if (normalModeHighscore < 0) {
            System.out.println("You don't have a highscore for normal mode yet!"); // Handles when highscore hasn't been set
        } else {
            System.out.printf(highscore, normalModeHighscore); // Prints highscore
        }
        System.out.println();

        /* Endless mode highscore */
        System.out.println(BLUE_BOLD_BRIGHT + BLUE_UNDERLINED + "Endless Mode" + RESET);
        if (endlessModeHighscore < 0) {
            System.out.println("You don't have a highscore for endless mode yet!");
        } else {
            System.out.printf(highscore, endlessModeHighscore);
        }
        System.out.println();

        /* Timed variant highscore */
        System.out.println(YELLOW_BOLD_BRIGHT + YELLOW_UNDERLINED + "Time Out Mode" + RESET);
        if (timedVariantHighscore < 0) {
            System.out.println("You don't have a highscore for variant mode yet!");
        } else {
            System.out.printf(highscore, timedVariantHighscore);
            // Special stat for timed variant "fastest" completed
            System.out.printf("Fastest completed" + GREEN_BOLD_BRIGHT + " %.1f\n" + RESET, fastestCompleted);
        }
        System.out.println();
    }

    /* Timer Helper Methods
    *
    * Methods that help with keeping track of time, they allow the end user to pause, unpause
    * record time laps, and the total time elapsed. In this code, time lap resembles the "lap" feature
    * in a smartphone stopwatch app. Aside from the start time, a lap allows the user to mark a special point
    * in time.
    *
    * stopwatch_start_time - This variable keeps track of when the stopwatch was set
    * lapTime              - The start time of a lap
    * pauseTime            - The time when the timer is paused
    * pauseOffset          - The total duration for which the stopwatch has been paused
    *
    * */

    /**
     * Starts the stopwatch by setting the current time and lap time
     * Resets pauseTime and pauseOffset
     */
    public static void StartStopwatch() {
        lapTime = stopwatch_start_time = System.currentTimeMillis();
        pauseTime = 0;
        pauseOffset = 0;
    }

    public static void pauseStopWatch() {
        pauseTime = System.currentTimeMillis();
        paused = true;
    }

    public static void unpauseStopWatch() {
        // Adds the total amount of time for which the stopwatch was paused to pauseOffset
        pauseOffset = System.currentTimeMillis() - pauseTime;
        paused = false;
    }

    public static void ResetLap() {
        lapTime = System.currentTimeMillis();
    }

    /**
     * Gets the total time elapsed since the stopwatch started and accounts for paused time segments.
     * If the stopwatch is currently paused, it returns the elapsed time up to the moment it was paused.
     *
     * @return elapsed time in milliseconds since the stopwatch started
     */
    public static long GetTimeElapsed() {
        // If the time is currently paused, set the current time to the pause_time
        // else use the current system time
        long currentTime = paused ? pauseTime : System.currentTimeMillis();

        // Accounts for pause by subtracting pauseOffset
        return currentTime - stopwatch_start_time - pauseOffset;
    }

    /**
     * Returns the time elapsed in seconds
     */
    public static double GetTimeElapsedSeconds() {
        return (double) GetTimeElapsed() / 1000; // conversion from millis -> seconds
    }

    /**
     * This method calculates the time since the last lap
     */
    public static long GetTimeSinceLastLap() {
        long difference = System.currentTimeMillis() - lapTime;
        return lapTime < pauseTime ? difference - pauseOffset : difference;
    }

    /* Miscellaneous Helper Methods */

    /**
     * Helper method to determine whether a given value is prime
     *
     * @param n Given value
     *
     * @return Boolean value indicating whether n is prime
     */
    public static boolean isPrime(int n) {
        // If the number is less than 2, it is composite (including negative numbers suprisingly)
        if (n < 2) return false;

        // iterate from 2...n - 1, checking if n is divisible by it
        for (int i = 2;i < n;i++) {
            // return false if n is divisible by i (a number that is not 1 or n itself)
            if (n % i == 0) return false;
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

    /**
     * Repeats a character a given number of times
     *
     * @param type The character/string to repeat
     * @param repetition Number of times to repeat
     */
    public static void repeat(String type, int repetition) {
        for (int i = 0;i < repetition;i++) {
            System.out.print(type);
        }
    }

    // Clears theconsole
    public static void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // Clear Screen with header
    public static void clearWithHeader() {
        clear();
        System.out.println(
                """
                  _________              __    .__  __ \s
                 /   _____/_____   _____/  |_  |__|/  |_\s
                 \\_____  \\\\____ \\ /  _ \\   __\\ |  \\   __\\
                 /        \\  |_> >  <_> )  |   |  ||  | \s
                /_______  /   __/ \\____/|__|   |__||__| \s
                        \\/|__|                          \s
                __________________________________________\s
                By Raiyan Islam and Ahnaf Masud
               \s"""
        );
    }

    // Resets the color, background, underline, bold of the text
    public static void ResetText() {
        System.out.print(RESET);
    }

    /**
     * Prompts the user for an int
     *
     * @param prompt The prompt to display to the user
     *
     * @return The integer inputted by the user
     */
    public static int readInt(String prompt) {
        System.out.print(prompt);
        return input.nextInt();
    }

    /**
     * Prompts the user for a String
     *
     * @param prompt The prompt to display to the user
     *
     * @return The string inputted by the user
     */
    public static String readLine(String prompt) {
        System.out.print(prompt);
        return input.nextLine();
    }

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
     * Returns a random number in a specified range
     *
     * @param min The minimum of the range (inclusive)
     * @param max The maximum of the range (inclusive)
     *
     * @return A random number within the range
     */
    public static int randomInt(int min, int max) {
        // To convert the output of Math.random() to a range of [min, max]
        // we employ the usage of math !
        // Math.random() returns a number in the range [0, 1]
        // Mutliply [0, 1] by (max - min) to get [0, (max - min)]
        // add the min to this range to get [min, max]
        return min + (int) (Math.random() * (max - min));
    }

    /* Game functionality Methods */

    /**
     * Picks two random unique indices
     *
     * @param length The length of the array to choose indices from
     *
     * @return An array of ints [0] - first choice and [1] - second choice
     */
    public static int[] pickTwoIndices(int length) {
        // Determine the first choice
        int first_choice = randomInt(0, length);
        int second_choice;

        // Determine the second choice but only if it doesn't match the first choice
        do {
            second_choice = randomInt(0, length);
        } while (first_choice == second_choice);

        // Return an array of the choices
        return new int[]{ first_choice, second_choice };
    }

    // A method shuffle cards in the deck
    public static void ShuffleDeck() {
        // The number of swaps made is equivalent to the size of the deck
        for (int i = 0; i < deck.length; i++) {
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

    /**
     * Shuffles the elements in a card
     *
     * @param card_index The index of the card in the deck to shuffle
     */
    public static void ShuffleCard(int card_index) {
        int[] card = deck[card_index];
        int iterations = card.length / 2;

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

    // Shuffles the whole deck including each card
    public static void ShuffleWholeDeck() {
        ShuffleDeck();

        // Shuffling each card
        for (int i = 0;i < deck.length;i++) {
            ShuffleCard(i);
        }
    }

    /**
     * Print out a card from the deck
     *
     * @param idx The index of the card within the deck
     */
    public static void print_card(int idx) {
        int[] card = deck[idx];
        int last = card.length - 1; // index pointing to the last element in the card

        for (int index = 0;index < last;index++) {
            System.out.printf("%s, ", symbols[card[index]]);
        }
        // Print the last element out individually with no extra commas
        System.out.println(symbols[card[last]]);
    }

    /**
     * Generates a list of images that are just numbers
     *
     * @param images_per_card Symbols per card
     *
     */
    public static void GenerateDefaultImageList(int images_per_card) {
        // Initialize entries with a length of the total number of unqiue symbols
        int num_of_images = getNumberOfImages(images_per_card);
        symbols = new String[num_of_images];

        // Create a list of integers
        for (int i = 0;i < num_of_images;i++) {
            symbols[i] = String.valueOf(i);
        }
    }

    /**
     * Generates the deck given the images per card
     *
     * @param images_per_card Symbols per card
     */
    public static void GenerateDeck(int images_per_card) {

        // Check if the number of images is 1 more than a prime number
        if (!isPrime(images_per_card - 1)) {
            // Throw an error if that is not the case
            throw new RuntimeException("Number of symbols is not 1 more than a prime number");
        }

        int num_of_cards = getNumberOfImages(images_per_card);

        // If the current_theme_selector is 0, which is "default"
        // it will fill entries with numbers
        if (current_theme_selector == 0) {
            GenerateDefaultImageList(images_per_card);
        }

        /* Custom Spot it Generation Algorithm Implementation */
        // Inspired by https://www.101computing.net/the-dobble-algorithm/

        // Initialize new deck size
        deck = new int[num_of_cards][images_per_card];
        int primeN = images_per_card - 1;

        // This handles the first n cards
        // i.e. in a deck of 3 cards
        // a b c
        // a     d e
        // a         f g

        for (int i = 0;i < primeN + 1;i++) {
            deck[i][0] = 0; // Set the first element in each card to 0

            // Fills in the n - 1 spots in each card
            for (int j = 0;j < primeN;j++) {
                deck[i][j + 1] = (j + 1) + i * primeN;
            }
        }

        // Fills in the rest of the cards
        for (int i = 0;i < primeN;i++) {
            for (int j = 0;j < primeN;j++) {
                // Gets the index of the current card
                int idx = primeN * (i + 1) + j + 1;

                // Sets the first element in the card idx
                deck[idx][0] = i + 1;

                // Performs some calculation to determine the next elements to fill in the current card index
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
                return firstImage; // return the index that we've encountered before
            }
            images[firstImage] = true; // set this index to true indicating that we have encountered it

            if (images[secondImage]) {
                return secondImage;
            }
            images[secondImage] = true;
        }

        // Default value to return although it is
        // technically unreachable since all cards will always have
        // one similarity, thus it is a
        // statement for resolving control flow warnings
        return -1;
    }

    /**
     * Prompt user for an int and perform basic input validation checks which include
     * input mismatch and whether the value is non-positive or non-negative
     * The urge to add a callback as a parameter...
     *
     * @param prompt Prompt to provide the user
     * @param checkPositive If true will prompt the user for only positive values
     *
     */
    public static int readIntWithInputValidation(String prompt, boolean checkPositive) {
        int num = 0;
        boolean isInputValid = false;

        do {
            // try/catch statement to catch InputMismatchExceptions from the scanner
            try {
                num = readInt(prompt);
                input.nextLine();

                // Checks if the input is valid based on the checkPositive flag
                // It will only validate int input if checkPositive is false
                // or if checkPositive is true and the num inputted is positive
                isInputValid = !checkPositive || num > 0;

                if (!isInputValid) {
                    System.out.println("Please input a positive integer"); // Error message for non-positive values
                }

            } catch (InputMismatchException error) {
                System.out.println("Must be an integer");
                input.nextLine();
            }
        } while (!isInputValid);

        return num;
    }

    /* Game  Methods */

    /**
     * Chooses two cards at random prints them
     *
     * @return Returns the common element between the cards
     */
    public static String printTwoRandomCards() {
        // Retrieves two random card indices from the deck
        int[] choiceIndex = pickTwoIndices(deck.length - 1);
        int first_choice = choiceIndex[0];
        int second_choice = choiceIndex[1];

        // Shuffling
        ShuffleCard(first_choice);
        ShuffleCard(second_choice);

        print_card(first_choice);
        print_card(second_choice);

        // Returns the common element between the two cards
        return symbols[FindCommonElement(first_choice, second_choice)];
    }

    // Prints the time in which a question was completed
    public static void print_time_completion() {
        double seconds = (double) GetTimeSinceLastLap() / 1000;
        System.out.printf("Completed in" + GREEN_BOLD_BRIGHT + " %.2fs. " + RESET, seconds);

        ResetLap(); // Resets the lap, because then GetTimeSinceLastLap() will get the time since the last question
    }

    /**
     * Confirm exit will prompt the user if they wish to quit the gamemode early. It will pause the timers
     *
     * @return returns a boolean indicating if whether the user wants to quit the round early
     */
    public static boolean confirm_exit() {
        pauseStopWatch(); // Pause stop watch
        // prompts the user if they wish to quit early
        String confirm = readLine("Quit round early? (y/n) ");

        // Returns true if the user wants to quit early
        if (confirm.equalsIgnoreCase("y")) return true;

        unpauseStopWatch(); // unpause the stop watch !!!
        return false;
    }


    /**
     * Determines if the answer is correct and outputs the result to the console
     *
     * @param guess The guess inputted by the user
     * @param correct_answer The correct answer
     */
    public static boolean is_answer_correct(String guess, String correct_answer) {
        boolean isCorrect = guess.equalsIgnoreCase(correct_answer); // Checks if they are equal via comparison

        // Prints the result to the screen
        if (isCorrect) {
            System.out.println(GREEN_BOLD_BRIGHT + "Correct! +1");
        } else {
            System.out.printf(RED_BOLD_BRIGHT + "Not correct :( The correct answer was %s\n", correct_answer);
        }

        // returns whether the answer was correct or not
        return isCorrect;
    }

    /**
     *  Normal Mode Game Mode
     *
     * @param difficulty The difficulty mode
     */
    public static void normal_mode(Difficulty difficulty) {
        // I do not take credit for this, this was simplification was thanks to intelliji
        // Handles setting number of rounds based on difficulty level
        int total_rounds = switch (difficulty) {
            case SUPER_HARD -> 10;
            case HARD -> 15;
            case INTERMEDIATE -> 20;
            case EASY -> 25;
            // Prompt the user for the number of rounds
            case CUSTOM ->
                    readIntWithInputValidation(WHITE_BOLD_BRIGHT + "How many rounds do you wish to play? " + RESET, true);
            // Chooses a random number of rounds from 2 -> 7
            case RANDOM -> randomInt(2, 7);
        };

        // Printing number of rounds applied
        System.out.printf(WHITE_BOLD + "%d round(s)\nGo!\n" + RESET, total_rounds);

        int score = 0;
        StartStopwatch(); // Starting stop watch (sets current time)

        // A for loop for incrementing through the rounds
        for (int round = 0; round < total_rounds; round++) {
            String correct_answer = printTwoRandomCards(); // Prints two cards and prints their common element

            // Prompt the user for the correct element
            System.out.printf(WHITE_BOLD_BRIGHT + "Round %d / %d: What's the common element? " + RESET, round + 1, total_rounds);
            String guess = input.nextLine().trim();

            // If the guess is empty prompt the user if they want to exit early
            if (guess.isEmpty() && confirm_exit()) return;

            // Handle answer checking
            if (is_answer_correct(guess, correct_answer)) {
                score++;
            }

            ResetText();
            print_time_completion();
            System.out.print("\n\n");
        }

        /* Stats page for normal mode */

        System.out.printf(GREEN_BOLD_BRIGHT + "%d / %d Correct\n" + RESET, score, total_rounds);

        // Stats page header
        repeat("-", 20);
        System.out.println();
        System.out.print(CYAN_BRIGHT);
        System.out.println("Stats");

        double percentage = (double) score / total_rounds; // Percentage of correct answers

        /* Time calculations
           The current time bonus calculation is...
        */
        double totalTime = GetTimeElapsedSeconds();
        double averageTime = totalTime / total_rounds;
        double timeBonus = score / totalTime * 3;

        // Printing out statistics
        System.out.printf("%.2f%% Percentage Correct\n", percentage * 100);
        System.out.printf("Total time %.2fs\n", totalTime);
        System.out.printf("Average time %.2fs\n", averageTime);
        System.out.printf("Time Bonus: %.2f\n", timeBonus);
        System.out.println();

        double score_calculation = percentage * 10 + timeBonus;

        /* Setting new highscores */

        if (normalModeHighscore < 0 || normalModeHighscore < score_calculation) {
            System.out.println("New Highscore !");
            normalModeHighscore = score_calculation;
        }

        System.out.printf("Score: %.2f" + RESET, score_calculation);
        System.out.println();
    }

    /**
     * Endless Game mode
     */
    public static void endless_mode() {
        // Declarations
        int score = 0;
        int currentRounds = 0;
        String guess, correct_answer;

        StartStopwatch(); // Set current time

        do {
            correct_answer = printTwoRandomCards(); // Print two cards and get the common element between them

            // Prompt user
            System.out.printf(WHITE_BOLD_BRIGHT + "Round %d : What's the common element? " + RESET, currentRounds);
            guess = input.nextLine().trim();

            // If the guess is empty prompt the user if they want to exit early
            if (guess.isEmpty() && confirm_exit()) return;

            // Handle right/wrong answer
            if (is_answer_correct(guess, correct_answer)) {
                score++;
            }

            ResetText();
            print_time_completion(); // print the time in which the question was completed
            System.out.print("\n\n");

            currentRounds++; // Increment rounds
        } while (guess.equalsIgnoreCase(correct_answer));

        // Displays stats facts
        System.out.println();
        System.out.printf("You got %d correct! Completed in" + GREEN_BOLD_BRIGHT + " %.2f seconds\n" + RESET, score, GetTimeElapsedSeconds());

        /* Setting new highscores */

        if (endlessModeHighscore < 0 || endlessModeHighscore < score) {
            System.out.printf("New Highscore!" + GREEN_BOLD_BRIGHT + " %.2f\n" + RESET, (double) score);
            endlessModeHighscore = score;
        }
    }

    /**
     * Timed variant Gamemode
     *
     * @param difficulty Difficult mode !!!
     */
    public static void timed_variant(Difficulty difficulty) {

        // Determine amount of time for each setting
        int time_allotted = switch (difficulty) {
            case SUPER_HARD -> 60;
            case HARD -> 45;
            case INTERMEDIATE -> 30;
            case EASY -> 15;
            // Prompts user for amount of time to play with
            case CUSTOM -> readIntWithInputValidation("How long do you want? ", true);
            case RANDOM -> randomInt(20, 120); // Random amount of time
        };

        // Declarations
        int score = 0;
        int rounds = 0;
        double timeElapsed;

        // Countdown
        System.out.print(WHITE_BOLD_BRIGHT);
        for (int i = 3;i > 0;i--) {
            System.out.printf("%d... ", i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
                System.out.println("Something Unexpected happened! Quitting game");
                return;
            }
        }

        System.out.println();
        System.out.printf("Go! You have %d seconds\n", time_allotted);

        ResetText();
        StartStopwatch(); // pause stop watch

        do {
            // print two random cards and store the correct result
            String correct_answer = printTwoRandomCards();

            // prompt the user for the answer
            String guess = readLine(WHITE_BOLD_BRIGHT + "What's the common element? " + RESET).trim();

            // If the result is empty prompt the user if they wish to return early
            if (guess.isEmpty() && confirm_exit()) return;

            timeElapsed = GetTimeElapsedSeconds();

            if (timeElapsed < time_allotted) {
                if (is_answer_correct(guess, correct_answer)) {
                    score++;
                }
                rounds++;

                ResetText();
                print_time_completion();

                // Print time remaining
                System.out.printf("Remaining time" + WHITE_BOLD_BRIGHT + " %.2fs\n\n" + RESET, time_allotted - timeElapsed);
            }
        } while (timeElapsed < time_allotted);

        // Stats page header
        repeat("-", 20);
        System.out.println();
        System.out.print(CYAN_BRIGHT);
        System.out.println("Stats");

        double totalTime = GetTimeElapsedSeconds();
        double percentage = (double) score / rounds;
        double averageTime = totalTime / rounds;
        double timeBonus = score / totalTime * 3;

        System.out.println();
        System.out.printf("You ran out of time! You got a score of %.2f\n", score);
        System.out.printf("Total rounds %d\n", rounds);
        System.out.printf("%.2f%% Percentage Correct\n", percentage * 100);
        System.out.printf("Total time %.2fs\n", totalTime);
        System.out.printf("Average time %.2fs\n", averageTime);
        System.out.printf("Time Bonus: %.2f\n", timeBonus);

        /* Setting new highscores */

        if (timedVariantHighscore < 0 || timedVariantHighscore < score) {
            System.out.printf("New Highscore !" + GREEN_BOLD_BRIGHT + " %.2f\n" + RESET, score);
            timedVariantHighscore = score;
        }

        if (fastestCompleted < 0 || fastestCompleted < totalTime) {
            System.out.printf("New Fastest Completed !" + GREEN_BOLD_BRIGHT +  " %.2f\n" + RESET, totalTime);
            fastestCompleted = totalTime;
        }
    }

    /**
     * Prints out a selection of numbered options and prompts the user to pick one
     * Will accept the value in the prompt or the number
     *
     * @param prompt What to prompt the user with
     * @param options A list of options
     *
     * @return The index of the option they choose
     */
    public static int select_option(String prompt, String... options) {
        ResetText();
        do {
            // Print the options side by side with numbers i.e. 1) option1 2) option2 ... 5) option5
            for (int i = 0;i < options.length;i++) {
                System.out.printf("%d)" + WHITE_BOLD_BRIGHT + " %s " + RESET, i + 1, options[i]);
            }
            System.out.println();

            // Prompt the user
            String result = readLine(prompt);

            int chosen_option = -1; // the index of the option

            // try catch block to parse the input
            try {
                // Attempt to parse the input as an integer
                chosen_option = Integer.parseInt(result) - 1;

            } catch (NumberFormatException e) {
                // if a NumberFormatException is caught we attempt to parse it as a word
                // checking it against the list of options

                for (int index = 0;index < options.length;index++) {
                    // Check if the option matches
                    if (result.equalsIgnoreCase(options[index])) {
                        chosen_option = index;
                        break; // break early since we've found it
                    }
                }
            }

            // if our attempts to parse it don't work it means the user hasn't selected one of the options
            if (0 <= chosen_option && chosen_option < options.length) {
                return chosen_option; // Return the option if it passes ALL the checks
            } else {
                System.out.println(RED_BOLD_BRIGHT + "Please Select one of the options" + RESET);
                System.out.println();
            }
        } while (true); // I know this is bad practice but in this case it should be fine right?
    }

    // Prompt the user to choose a diffuclty mode
    public static Difficulty handleDifficultySelection() {

        // Gets all the difficulty mode as enum values
        Difficulty[] modes = Difficulty.values();

        int chosen_option = select_option("(Select a difficulty) ", "Super Hard", "Hard", "Intermediate", "Easy", "Custom", "Random");
        System.out.println();

        // Index into the list of enums to get the enum
        // that matches the difficulty selection the user chose
        Difficulty difficulty = modes[chosen_option];

        int size = switch (difficulty) {
            case SUPER_HARD -> 8;
            case HARD -> 6;
            case INTERMEDIATE -> 4;
            case EASY -> 3;

            case CUSTOM -> {
                int deckSize;

                // Prompting the user for the deck size
                do {
                    deckSize = readIntWithInputValidation("Custom Deck Size (Must be one more than a prime): ", true);

                    // Limit deck size otherwise printing all those nums will not be pretty
                    if (deckSize > ARBITRARY_SIZE_LIMIT) {
                        System.out.printf("Hahaha no. Number must be less than %d\n", ARBITRARY_SIZE_LIMIT);
                    }

                // Keep prompting until the deckSize is 1 more than a prime and less than the deck size
                } while (!(isPrime(deckSize - 1) && deckSize <= ARBITRARY_SIZE_LIMIT));

                yield deckSize;
            }

            case RANDOM -> {
                int randomPrime = randomInt(2, 5);

                yield findNthPrime(randomPrime) + 1;
            }
        };

        GenerateDeck(size);

        return difficulty;
    }

    // Game Modes Menu
    public static void start_game() {
        clearWithHeader();
        String command;

        do {
            // Print Menu Options
            System.out.println(WHITE_UNDERLINED + WHITE_BOLD_BRIGHT + "Game Modes" + RESET);
            System.out.println(RED_BACKGROUND + "Normal Mode [1]" + RESET);
            System.out.println(BLUE_BACKGROUND + "Endless Mode [2]" + RESET);
            System.out.println(YELLOW_BACKGROUND + "Timed Variant [3]" + RESET);
            System.out.println(WHITE_BOLD_BRIGHT + "Quit to previous [Q]");
            System.out.println();

            // Recieve input
            command = readLine("(Enter the Command) ");

            // Shuffle the whole deck cause why not?
            ShuffleWholeDeck();

            System.out.println();
            Difficulty mode;

            switch (command.toLowerCase()) {
                case "1":
                    clearWithHeader();

                    // Prompt user to select a difficulty
                    mode = handleDifficultySelection();
                    normal_mode(mode); // Run normal mode based on difficulty

                    readLine("(Press enter to continue) ");
                    break;
                case "2":
                    clearWithHeader();

                    handleDifficultySelection();
                    endless_mode();

                    readLine("(Press enter to continue) ");
                    break;
                case "3":
                    clearWithHeader();

                    mode = handleDifficultySelection();
                    timed_variant(mode);

                    readLine("(Press enter to continue) ");
                    break;
                case "q":
                    continue;
                // Handle default case
                default:
                    readLine("Unknown Command (Press enter to continue) ");
                    break;
            }

            // Clear console after every match
            clearWithHeader();

        // End when the command matches "quit"
        } while (!command.equalsIgnoreCase("q"));
    }

    /* Debug Utilities */

    public static void print_deck() {
        for (int card = 0;card < deck.length;card++) {
            print_card(card);
        }
    }

    public static void debug_console() {
        clear();

        System.out.println(
            """        
            ________  _____________________ ____ ___  ________\s
            \\______ \\ \\_   _____/\\______   \\    |   \\/  _____/\s
             |    |  \\ |    __)_  |    |  _/    |   /   \\  ___\s
             |    `   \\|        \\ |    |   \\    |  /\\    \\_\\  \\
            /_______  /_______  / |______  /______/  \\______  /
                    \\/        \\/         \\/                 \\/\s
            --------------------------------------------------------
            By Raiyan Islam and Ahnaf Masud
            
            Entering Debug Console...
            """
        );

        String command;
        do {

            // Debug console options
            System.out.println(WHITE_BOLD_BRIGHT + WHITE_UNDERLINED + "Debug Options" + RESET);
            System.out.println("Generate Deck [gen, 1]");
            System.out.println("Shuffle Card [shuffle, 2]");
            System.out.println("Print [print, 3]");
            System.out.println("Start stopwatch [4]");
            System.out.println("Get time [5]");
            System.out.println("pause stopwatch [6]");
            System.out.println("unpause time [7]");
            System.out.println("Quit [Q]");
            System.out.println();

            // Read in the command
            command = readLine("(Enter the Command) ");

            // Check command against the available options
            switch (command.toLowerCase()) {
                case "gen", "1":
                    // Prompt user for a deck
                    int deckSize = readIntWithInputValidation("Custom Deck Size (Must be one more than a prime): ", true);
                    GenerateDeck(deckSize);

                    print_deck();
                    break;
                case "2":
                    // Shuffling a card
                    int card_idx = readIntWithInputValidation("Card number (1-indexed): ", true) - 1;

                    if (card_idx >= deck.length) {
                        System.out.println("Exceeds deck size returning to debug :(");
                        break;
                    }

                    ShuffleCard(card_idx);
                    print_deck();
                    break;
                case "print", "3":
                    print_deck();
                    break;
                case "4":
                    StartStopwatch();
                    break;
                case "5":
                    System.out.println(GetTimeElapsedSeconds());
                    break;
                case "6":
                    pauseStopWatch();
                    break;
                case "7":
                    unpauseStopWatch();
                    break;
                case "q":
                    System.out.println("Exiting Debug Console...");
                    readLine("Press enter to confirm ");
                    break;
                default:
                    System.out.println("Unknown command");
                    break;
            }

            System.out.println();
        } while (!command.equalsIgnoreCase("q")); // Stop if the command is q or "Q"

    }
}