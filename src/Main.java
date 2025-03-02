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

    // text colors
//    BLACK	\u001B[30m	BLACK_BACKGROUND	\u001B[40m
//    RED	\u001B[31m	RED_BACKGROUND	\u001B[41m
//    GREEN	\u001B[32m	GREEN_BACKGROUND	\u001B[42m
//    YELLOW	\u001B[33m	YELLOW_BACKGROUND	\u001B[43m
//    BLUE	\u001B[34m	BLUE_BACKGROUND	\u001B[44m
//    PURPLE	\u001B[35m	PURPLE_BACKGROUND	\u001B[45m
//    CYAN	\u001B[36m	CYAN_BACKGROUND	\u001B[46m
//    WHITE	\u001B[37m	WHITE_BACKGROUND	\u001B[47m

    public static final String WHITE = "\u001B[37m";
    public static final String BRIGHT_YELLOW = "\u001B[33;1m";
    public static final String BRIGHT_BLUE = "\u001B[34;1m";
    public static final String RED = "\u001B[1;91m";
    public static final String CYAN = "\u001B[0;96m";
    public static final String GREEN = "\u001B[1;92m";
    public static final String BRIGHT_WHITE = "\u001B[37;1m";
    public static final String BRIGHT_MAGENTA = "\u001B[1;95m";

    // background colors
    public static final String BLACK_BACKGROUND = "\u001B[40;1m";
    public static final String RED_BACKGROUND = "\u001B[41;1m";
    public static final String YELLOW_BACKGROUND = "\u001B[43m";
    public static final String WHITE_BACKGROUND = "\u001B[47m";
    public static final String CYAN_BACKGROUND= "\u001B[46m";

    public static void main(String[] args) {
        input = new Scanner(System.in);

        /* Generate a default deck of cards */
        String[] DefaultItems = {"0", "1", "2", "3", "4", "5", "6"};
        GenerateDeck(3, DefaultItems);

        System.out.println(findNthPrime(10));

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
     *
     * @param length:   Number of Icons
     * @param interval: Interval between icon loading
     *
     * @description My take on the progress bar inspried by https://stackoverflow.com/questions/852665/command-line-progress-bar-in-java
     */
    public static void print_progressbar(int length, long interval) {
        char incomplete = '░'; // U+2591 Unicode Character
        char complete = '█'; // U+2588 Unicode Character

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

//    public static void print_card(int[] elements) {
//
//        int length = elements.length;
//        double side = Math.sqrt(length);
//
//        int row = (int) Math.ceil(side);
//        int col = (int) Math.round(side);
//
//        int[][] grid = new int[row][col];
//        int maxLength = 0;
//
//        for (int i = 0; i < row; i++) {
//            int sum = 0;
//            for (int j = 0; j < col; j++) {
//                int index = i * row + col;
//
//                int entryIndex = elements[index];
//                grid[i][j] = entryIndex;
//
//                sum += entries[entryIndex].length();
//            }
//
//            maxLength = Math.max(maxLength, sum);
//        }
//    }

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

        setEntries(items);
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
                System.out.println(GREEN + "Correct! +1" + WHITE);
                score++;
            } else {
                System.out.printf(RED + "Not correct :( The correct answer was %s\n" + WHITE, correct_answer);
            }
        }

        System.out.printf(GREEN + "%d / %d Correct\n" + WHITE, score, rounds);
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
            System.out.print("1... ");
            Thread.sleep(1000);
            System.out.print("Go!");
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
                System.out.println("Correct!");
            } else {
                System.out.printf("Wrong, the correct answer was %s\n", correct_answer);
            }
        } while (true);
    }

    // This is how the user is going to be able to run the spot-it game
    public static void start_game() {
        String command;

        do {
            System.out.println("Game Modes");
            System.out.println("Normal Mode [1]");
            System.out.println("Endless Mode [2]");
            System.out.println("Timed Variant [3]");
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
}