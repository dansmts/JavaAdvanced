package be.voorbeeldexamen;

import be.pxl.poker.Card;
import be.pxl.poker.Hand;
import be.pxl.poker.PokerHand;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        // Variables
        List<PokerHand> hands = new ArrayList();
        Thread printGames = new Thread(() -> printGames(hands));
        Path path = Paths.get("poker.txt");


        // Read file
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line = null;

            while ((line = reader.readLine()) != null) {
                char[] lineArray = line.toCharArray();
                Card[] cards = new Card[5];
                int count = 0;
                for (int i = 0; i < lineArray.length; i+=3) {
                    Card card = new Card(lineArray[i], lineArray[i + 1]);
                    if (count < 5) {
                        cards[count] = card;
                        count++;
                    } else {
                        PokerHand hand = new PokerHand(cards);
                        hands.add(hand);
                        count = 0;
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Print de games
        printGames.run();

        // Print het aantal hands met een aas
        int countHandsWithAce = (int) hands.stream().filter(pokerHand -> pokerHand.toString().contains("A")).count();
        System.out.println(String.format("Er zijn %d handen die een aas bevatten:", countHandsWithAce));
        hands.stream().filter(pokerHand -> pokerHand.toString().contains("A")).forEach(System.out::println);

        // Schrijf de 5 waardevolste handen weg
        Collections.sort(hands, Collections.reverseOrder()); // Standaard sortering is van laag naar hoog
        List<PokerHand> bestHands = hands.subList(0, 5);

        try (FileWriter file = new FileWriter("best.txt"))
        {
            file.write("De 5 waardevolste handen:");
            for (PokerHand hand : bestHands) {
                file.write(String.format("\nHand1: %s value: %d", hand, hand.getCurrentHandValue()));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        // Print het aantal hands met Straight
        int aantalStraights = (int) hands.stream().filter(pokerHand -> pokerHand.getValue() == PokerHand.ValueType.STRAIGHT).count();
        System.out.println(String.format("Er zijn %d handen die een straight voorstellen", aantalStraights));


    }

    private static void printGames(List<PokerHand> hands) {

        // Print de hands en winnaars
        int aantalWinsSpeler1 = 0;
        for (int i = 0; i < hands.size(); i+=2) {
            PokerHand handSpeler1 = hands.get(i);
            PokerHand handSpeler2 = hands.get(i+1);

            int winnaar = 0;

            switch (handSpeler1.compareTo(handSpeler2)) {
                case 1:
                    winnaar = 1;
                    aantalWinsSpeler1++;
                    break;
                case -1:
                    winnaar = 2;
                    break;
                case 0:
                    winnaar = 0;
                    break;
            }

            System.out.println(String.format("Hand1: %s vs. Hand2: %s - Winnaar: %d", handSpeler1, handSpeler2, winnaar));

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                System.out.println("You've got interrupted2");
            }
        }
        System.out.println(String.format("Speler 1 wint %d keer.", aantalWinsSpeler1));
    }
    
}
