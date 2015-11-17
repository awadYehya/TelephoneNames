/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telephonenames;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.FileNotFoundException;
import java.lang.SecurityException;
import java.util.Formatter;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 *
 * @author yha5009
 */
public class TelephoneNames {
    private static Formatter phoneOutput;
    private static String phoneNumber;
    private static char[] telchars = new char[8];
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       initFile();
       readInput();
       writeTelephoneWords();
    }
    
    // Initializes the file
    private static void initFile() {
        try {
           phoneOutput = new Formatter("phone.txt");
       } catch (SecurityException e){
           System.err.println("Error: Write permission denied. Terminating.");
           System.exit(1);
       } catch (FileNotFoundException e) {
           System.err.println("Error: File not found. Terminating.");
           System.exit(1);
       }
    }
    
    // Reads input from the user, and validates it.
    private static void readInput() {
        Scanner input = new Scanner(System.in);
        try {
            System.out.print("\nEnter a phonenumber (only digits above 1): ");
            phoneNumber = input.next("\\d{7}"); // pattern check
        } catch (NoSuchElementException e) {
            invalidInputDetected();
            return;
        }
        if (phoneNumber.contains("0") || phoneNumber.contains("1")){
            invalidInputDetected();
        }
    }
    
    // Called when input is invalid.
    private static void invalidInputDetected() {
        System.out.print("\nThis input is invalid, try again.");
        readInput();
    }
    
    private static void writeTelephoneWords() {
        int[] number = new int[7];
        
        System.out.print("Your input: ");
        // place all phonenumbers into int array.
        for (int i = 0; i < phoneNumber.length(); i++) {
            number[i] = Integer.parseInt(phoneNumber.substring(i, i+1));
            System.out.print(number[i]);
        }
        System.out.println();
        
        // Initialize telephone chars array
        telchars[0] = 'A';
        telchars[1] = 'D';
        telchars[2] = 'G';
        telchars[3] = 'J';
        telchars[4] = 'M';
        telchars[5] = 'P';
        telchars[6] = 'T';
        telchars[7] = 'W';
        
        /** This is an int array that has values ranging from 0-2
        * If for example the first number is 2 corresponding to letters A,B,C
        * Then 
        * startword[0] = 0 corresponds to A
        * startword[0] = 1 corresponds to B
        * startword[0] = 2 corresponds to C
        */
        int [] startword = {0,0,0,0,0,0,0};
        
        phoneOutput.format("Phonenumber: %s\n\n",phoneNumber);
        generateAllPhoneWords(number,startword, 0);
        phoneOutput.close();
    }
    
    public static int printcount = 0;
    
    // Recursive Algorithm to print all arrangements
    private static void generateAllPhoneWords(int [] phn, int [] phnword, int spot){
        // Printing the current string in the recursion
        if (spot == 7) { // base case
            for (int i = 0; i < 7; i++) {
                int digit = phn[i];
                int telcharsindx = digit - 2;
                char curChar = telchars[telcharsindx];
                curChar += phnword[i];
                if (printcount < 6) { // to print only a few
                    System.out.print(curChar);
                }
                phoneOutput.format("%s", curChar);
            }
            if (printcount < 6) { // to print only a few
                System.out.println();
            }
            phoneOutput.format("\n");
            printcount++;
            return;
        }
        
        // This removes the Q from the possible numbers, as Q is not in Fig 1.
        boolean qfactor = false;
        if (phn[spot] == 7){ // fix for the digit table
            qfactor = true;
        }
        
        // Recursive calls
        for (int i = 0; i < 3; i++) {
            phnword[spot] = i;
            if (qfactor && i > 0) phnword[spot] += 1;
            generateAllPhoneWords(phn, phnword, spot+1);
        }
    }
}