/*
Authors: Raiyan Islam and Ahnaf Masud
Date: 03/07/2025
Program Name: Spot it
Description:
Spot it is a skibidi game

*/

import java.util.*;

public class Main {
    /* Declarations for variables related to the Deck */

    // the Deck is a static 2d array of ints, each int symbolizes a symbol which is stored in entries
    static int[][] deck;
    static String[] entries;

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
    // High Intensity
    public static final String CYAN_BRIGHT = "\033[0;96m";   // CYAN
    // Bold High Intensity
    public static final String RED_BOLD_BRIGHT = "\033[1;91m";   // RED
    public static final String GREEN_BOLD_BRIGHT = "\033[1;92m"; // GREEN
    public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";// YELLOW
    public static final String BLUE_BOLD_BRIGHT = "\033[1;94m";  // BLUE
    public static final String WHITE_BOLD_BRIGHT = "\033[1;97m"; // WHITE
    public static void main(String[] args) {
        input = new Scanner(System.in);

        // Default deck
        GenerateDeck(3);

        /* Main Menu Options */
        String command;

        clearWithHeader();
        do {

            // Main menu Options
            System.out.println(WHITE_BOLD_BRIGHT + WHITE_UNDERLINED + "Menu Options" + RESET);
            System.out.println(GREEN_BACKGROUND + "New Game [1]" + RESET);
            System.out.println(BLUE_BACKGROUND + "Scores [2]" + RESET);
            System.out.println(RED_BACKGROUND + "Rules [3]" + RESET);
            System.out.println(PURPLE_BACKGROUND + "Quit [Q]" + RESET);
            System.out.println();

            // Read in the command
            command = readLine("(Enter the Command) ");

            // Check command against the available options
            switch (command) {
                case "1":
                    start_game();
                    break;
                case "2":
                    view_score();
                    readLine("(Press enter to continue) ");
                    break;
                case "3":
                    clearWithHeader();
                    System.out.println(
                        WHITE_UNDERLINED + WHITE_BOLD_BRIGHT + "Rules\n" + RESET +
                        """
                        Spot it is a very skibidi game
                        """
                    );
                    readLine("(Press enter to continue) ");
                    break;
                case "q":
                    System.out.println("Why you leave :(");
                    readLine("Press Enter to finish ");
                    break;
                default:
                    readLine("Unknown Command (Press enter to continue) ");
                    break;
            }

            clearWithHeader();
        } while (!command.equalsIgnoreCase("q"));
    }

    /* Timer Helper Methods
    *
    * Methods that help with keeping track of time
    *
    * */

    /**
     *
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
        pauseOffset = System.currentTimeMillis() - pauseTime;
        paused = false;
    }

    /**
     */
    public static void ResetLap() {
        lapTime = System.currentTimeMillis();
    }
    /**
     */
    public static long GetTimeElapsed() {
        return System.currentTimeMillis() - stopwatch_start_time - pauseOffset;
    }

    /**
     */
    public static double GetTimeElapsedSeconds() {
        return (double) GetTimeElapsed() / 1000;
    }

    /**
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
        if (n < 2) {
            return false;
        }

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

    public static void repeat(String type, int repetition) {
        for (int i = 0;i < repetition;i++) {
            System.out.print(type);
        }
    }

    public static void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Clear Screen with header
     */
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

    public static void resetText() {
        System.out.print(RESET);
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

    public static int randomInt(int min, int max) {
        return min + (int) (Math.random() * (max - min));
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
     */
    public static void ShuffleDeck() {
        int iterations = deck.length / 2;
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

    public static void ShuffleCard(int card_id) {
        int[] card = deck[card_id];
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

    /**
     * Shuffles the whole deck including each card
     */
    public static void ShuffleWholeDeck() {
        ShuffleDeck();
        for (int i = 0;i < deck.length;i++) {
            ShuffleCard(i);
        }
    }

    /**
     * Print out a card
     */
    public static void print_card(int idx) {
        int[] card = deck[idx];
        int last = card.length - 1;
        for (int index = 0;index < last;index++) {
            System.out.printf("%s, ", entries[card[index]]);
        }
        System.out.println(entries[card[last]]);
    }

    /**
     * Generates a list of images that are just numbers
     *
     * @param images_per_card Symbols per card
     *
     */
    public static void GenerateDefaultImageList(int images_per_card) {
        int num_of_images = getNumberOfImages(images_per_card);
        String[] images = new String[num_of_images];

        for (int i = 0;i < num_of_images;i++) {
            images[i] = String.valueOf(i);
        }

        entries = images;
    }

    /**
     * Generates the deck given the images per card
     *
     * @param images_per_card Symbols per card
     */
    public static void GenerateDeck(int images_per_card) {

        // Check if the number of images is 1 more than a prime number
        if (!isPrime(images_per_card - 1)) {
            throw new RuntimeException("Number of symbols is not 1 more than a prime number");
        }

        int num_of_cards = getNumberOfImages(images_per_card);

        GenerateDefaultImageList(images_per_card);

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
     * The urge to add a callback as a parameter...
     *
     * @param prompt Prompt to provide the user
     * @param checkPositive If true will prompt the user for only positive values
     *
     */
    public static int readIntWithInputValidation(String prompt, boolean checkPositive) {
        int num;

        String error_msg = "Please input a positive integer";

        do {
            // try/catch statement to catch InputMismatchExceptions from the scanner
            try {
                num = readInt(prompt);
                input.nextLine();

                if (!checkPositive || num > 0) {
                    break;
                }

                System.out.println(error_msg);

            } catch (InputMismatchException error) {
                System.out.println(error_msg);
                input.nextLine();
            }
        } while (true); // I know this is bad practice...

        return num;
    }

    /* Game Mode Methods */
    /**
     * Chooses two cards at random prints them
     *
     * @return Returns the common element between the cards
     */
    public static String printTwoRandomCards() {
        int[] choiceIndex = pickTwoIndices(deck.length - 1);
        int first_choice = choiceIndex[0];
        int second_choice = choiceIndex[1];

        ShuffleCard(first_choice);
        ShuffleCard(second_choice);

        print_card(first_choice);
        print_card(second_choice);

        return entries[FindCommonElement(first_choice, second_choice)];
    }

    public static void print_time_completion() {
        double seconds = (double) GetTimeSinceLastLap() / 1000;
        System.out.printf("Completed in" + GREEN_BOLD_BRIGHT + " %.2fs. " + RESET, seconds);
        ResetLap(); // Resets the lap allowing to get the time elapsed since the previous question
    }

    public static boolean confirm_exit() {
        pauseStopWatch();
        String confirm = readLine("Quit round early? (y/n) ");

        if (confirm.equalsIgnoreCase("y")) return true;
        unpauseStopWatch();

        return false;
    }

    /**
     *  Normal Mode
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

        for (int round = 0; round < total_rounds; round++) {
            String correct_answer = printTwoRandomCards(); // Prints two cards and prints their common element

            // Prompt the user for the correct element
            System.out.printf(WHITE_BOLD_BRIGHT + "Round %d / %d: What's the common element? " + RESET, round + 1, total_rounds);
            String guess = input.nextLine().trim();

            if (guess.isEmpty() && confirm_exit()) return;

            // Handle answer
            if (guess.equals(correct_answer)) {
                System.out.println(GREEN_BOLD_BRIGHT + "Correct! +1");
                score++;
            } else {
                System.out.printf(RED_BOLD_BRIGHT + "Not correct :( The correct answer was %s\n", correct_answer);
            }

            resetText();
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

        // Sets highscore
        if (normalModeHighscore < 0 || normalModeHighscore < score_calculation) {
            System.out.println("New Highscore !");
            normalModeHighscore = score_calculation;
        }

        System.out.printf("Score: %.2f" + RESET, score_calculation);
        System.out.println();
    }

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

            if (guess.isEmpty() && confirm_exit()) return;

            // Handle right/wrong answer
            if (guess.equals(correct_answer)) {
                score++;
                System.out.println(GREEN_BOLD_BRIGHT + "Correct!");
            } else {
                System.out.printf(RED_BOLD_BRIGHT + "Wrong, the correct answer was %s\n", correct_answer);
            }

            resetText();
            print_time_completion();
            System.out.print("\n\n");

            currentRounds++; // Increment rounds
        } while (guess.equals(correct_answer));

        // Displays stats facts
        System.out.println();
        System.out.printf("You got %d correct! Completed in" + GREEN_BOLD_BRIGHT + " %.2f seconds\n" + RESET, score, GetTimeElapsedSeconds());

        // Updates highscore
        if (endlessModeHighscore < 0 || endlessModeHighscore < score) {
            System.out.printf("New Highscore!" + GREEN_BOLD_BRIGHT + " %.2f\n" + RESET, (double) score);
            endlessModeHighscore = score;
        }
    }

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

        int score = 0;
        int rounds = 0;
        String guess, correct_answer;
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

        resetText();
        StartStopwatch();

        do {
            correct_answer = printTwoRandomCards();
            guess = readLine(WHITE_BOLD_BRIGHT + "What's the common element? " + RESET).trim();

            if (guess.isEmpty() && confirm_exit()) return;

            timeElapsed = GetTimeElapsedSeconds();

            if (timeElapsed < time_allotted) {
                if (guess.equals(correct_answer)) {
                    score++;
                    System.out.println(GREEN_BOLD_BRIGHT + "Correct!");
                } else {
                    System.out.printf(RED_BOLD_BRIGHT + "Wrong, the correct answer was %s\n", correct_answer);
                }

                resetText();
                print_time_completion();
                System.out.printf("Remaining time" + WHITE_BOLD_BRIGHT + " %.2fs\n\n" + RESET, time_allotted - timeElapsed);

                rounds++;
            }
        } while (timeElapsed < time_allotted);

        // Stats page header
        repeat("-", 20);
        System.out.println();
        System.out.print(CYAN_BRIGHT);
        System.out.println("Stats");

        double totalTime = (double) GetTimeSinceLastLap() / 1000;
        double percentage = (double) score / rounds;
        double averageTime = totalTime / rounds;
        double score_calculation = score;
        double timeBonus = score / totalTime * 3;

        System.out.println();
        System.out.printf("You ran out of time! You got a score of %.2f\n", score_calculation);
        System.out.printf("%.2f%% Percentage Correct\n", percentage * 100);
        System.out.printf("Total time %.2fs\n", totalTime);
        System.out.printf("Average time %.2fs\n", timeBonus);
        System.out.printf("Time Bonus: %.2f\n", averageTime);

        if (timedVariantHighscore < 0 || timedVariantHighscore < score) {
            System.out.printf("New Highscore !" + GREEN_BOLD_BRIGHT + " %.2f\n" + RESET, score_calculation);
            timedVariantHighscore = score;
        }

        if (fastestCompleted < 0 || fastestCompleted < totalTime) {
            System.out.printf("New Fastest Completed !" + GREEN_BOLD_BRIGHT +  " %.2f\n" + RESET, totalTime);
            fastestCompleted = totalTime;
        }
    }

    /* Menu Methods */

    public static void view_score() {
        clearWithHeader();

        String highscore_prompt = "Highscore: " + GREEN_BOLD_BRIGHT + "%.2f\n" + RESET;
        System.out.println(RED_BOLD_BRIGHT + RED_UNDERLINED + "Normal Mode" + RESET);
        if (normalModeHighscore < 0) {
            System.out.println("You don't have a highscore for normal mode yet!");
        } else {
            System.out.printf(highscore_prompt, normalModeHighscore);
        }
        System.out.println();

        System.out.println(BLUE_BOLD_BRIGHT + BLUE_UNDERLINED + "Endless Mode" + RESET);
        if (endlessModeHighscore < 0) {
            System.out.println("You don't have a highscore for endless mode yet!");
        } else {
            System.out.printf(highscore_prompt, endlessModeHighscore);
        }
        System.out.println();

        System.out.println(YELLOW_BOLD_BRIGHT + YELLOW_UNDERLINED + "Time Out Mode" + RESET);
        if (timedVariantHighscore < 0) {
            System.out.println("You don't have a highscore for variant mode yet!");
        } else {
            System.out.printf(highscore_prompt, timedVariantHighscore);
            System.out.printf("Fastest completed" + GREEN_BOLD_BRIGHT + " %.1f\n" + RESET, fastestCompleted);
        }
        System.out.println();
    }

    public static Difficulty handleDifficultySelection() {

        int chosen_option;
        Difficulty[] modes = Difficulty.values();
        int total_modes = modes.length;

        do {
            System.out.println(WHITE_BOLD_BRIGHT + "Difficulty Mode" + RESET);
            System.out.printf("1)%s Super Hard%s ", WHITE_BOLD_BRIGHT, RESET);
            System.out.printf("2)%s Hard%s ", WHITE_BOLD_BRIGHT, RESET);
            System.out.printf("3)%s Intermediate%s ", WHITE_BOLD_BRIGHT, RESET);
            System.out.printf("4)%s Easy%s ", WHITE_BOLD_BRIGHT, RESET);
            System.out.printf("5)%s Custom%s ", WHITE_BOLD_BRIGHT, RESET);
            System.out.printf("6)%s Random%s", WHITE_BOLD_BRIGHT, RESET);
            System.out.println();

            chosen_option = readIntWithInputValidation("(Select a difficulty) ", false);

            if (1 <= chosen_option && chosen_option <= total_modes) {
                break;
            } else {
                System.out.println(RED_BOLD_BRIGHT + "Please Select one of the options" + RESET);
                System.out.println();
            }
        } while (true);
        System.out.println();

        Difficulty difficulty = modes[chosen_option - 1];

        int size = switch (difficulty) {
            case SUPER_HARD -> 8;
            case HARD -> 6;
            case INTERMEDIATE -> 4;
            case EASY -> 3;

            case CUSTOM -> {
                int deckSize;
                do {
                    deckSize = readIntWithInputValidation("Custom Deck Size (Must be one more than a prime): ", true);
                } while (!isPrime(deckSize - 1));

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

    /**
     * Menu Method for Game Modes
     */
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
                    mode = handleDifficultySelection();
                    normal_mode(mode);
                    readLine("(Press enter to continue) ");
                    break;
                case "2":
                    handleDifficultySelection();
                    endless_mode();
                    readLine("(Press enter to continue) ");
                    break;
                case "3":
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
}