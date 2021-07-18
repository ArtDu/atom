package ru.atom.game;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws FileNotFoundException {
        List<String> words = null;
        try {
            words = ResourceReader.readFromResource("dictionary.txt");
        } catch (IOException | NullPointerException e) {
            throw new FileNotFoundException("Something wrong with dictionary.txt");
        }

        Scanner input = new Scanner(System.in);

        while (true) {
            int number = getRandomNumber(0, words.size());
            String word = words.get(number);
            log.info("Secret word is {}", word);

            System.out.println("Welcome to Bulls and Cows game!");
            System.out.println("I offered a " + word.length() + "-letter word, your guess?");

            for (int i = 0; i < 10; i++) {
                System.out.println("Enter the word:");
                String guess;
                try {
                    guess = input.nextLine();
                } catch (NoSuchElementException e) { // EOF
                    return;
                }
                if (guess.equals(word)) {
                    System.out.println("You win!");
                    break;
                } else {
                    getHint(guess, word);
                }
            }
            System.out.println("Wanna play again? Y/N");
            String yes = input.nextLine();
            if (!yes.equals("Y")) {
                break;
            }
        }


    }

    public static void getHint(String guess, String word) {
        int bulls = 0;
        AtomicInteger cows = new AtomicInteger();
        Map<Character, Integer> guessMap = new TreeMap<>();
        Map<Character, Integer> correctMap = new TreeMap<>();
        for (int i = 0; i < Math.min(guess.length(), word.length()); i++) {
            Character guessChar = guess.charAt(i);
            Character correctChar = word.charAt(i);
            if (guessChar == correctChar) {
                bulls++;
            } else {
                Integer tmp = guessMap.get(guessChar);
                guessMap.put(guessChar, tmp == null ? 1 : (tmp + 1));
                tmp = correctMap.get(correctChar);
                correctMap.put(correctChar, tmp == null ? 1 : (tmp + 1));
            }
        }
        correctMap.forEach((ch, cnt) -> {
            Integer tmp = guessMap.get(ch);
            cows.addAndGet(Math.min(cnt, tmp == null ? 0 : tmp));
        });
        System.out.println("Bulls: " + bulls);
        System.out.println("Cows: " + cows);
    }
    
    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }


}
