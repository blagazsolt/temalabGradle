package main.log;

import java.util.Arrays;

/**
 * A logoláshoz használt osztály.
 * A consolra való logoláshoz implementált osztály, amelynek segítségével
 * indentálva lehet logolni a program működését.
 */
public class Logger {

    /**
     * Egy logolni kívánt függvény meghívása előtt kell használni
     * @param information a függvény fejléce, amely kiírásra kerül indentálva
     */
    public static void funStarted(String information)
    {
        StackTraceElement[] th = Thread.currentThread().getStackTrace();
        char[] indentCount = new char[th.length*5-15];
        Arrays.fill(indentCount,' ');
        String indentString = new String(indentCount);
        System.out.println(">" + indentString + "-->" + information);
    }

    /**
     * Egy logolni kívánt függvény meghívása után kell használni
     * @param information a függvény fejléce, amely kiírásra kerül indentálva
     */
    public static void funEnded(String information)
    {
        StackTraceElement[] th =  Thread.currentThread().getStackTrace();
        char[] indentCount = new char[th.length*5-15];
        Arrays.fill(indentCount,' ');
        String indentString = new String(indentCount);
        System.out.println("<" + indentString + "<--" +information);
    }

    /**
     * A felhasználóval való kommunikációt oldja meg
     * @param message a kiírni kívánt szöveg
     */
    public static void log(String message){
        StackTraceElement[] th =  Thread.currentThread().getStackTrace();
        char[] indentCount = new char[(th.length)*5-15];
        Arrays.fill(indentCount,' ');
        String indentString = new String(indentCount);
        System.out.print("?" + indentString + "--?" + message + ", Válasz: ");
    }

}