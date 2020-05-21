package com.my.parking.myparking.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author siddharthdwivedi
 */
@Component
public class Utility {
    private static Logger log = LoggerFactory
            .getLogger(Utility.class);

    static Map<Object, Object> sessionMap = new HashMap<>();

    Console console = System.console();
    static Scanner scanner = new Scanner(System.in).useDelimiter("\\n");

    public static void print(Object o) {
        System.out.println(o);
    }

    public static void printHeading(Object o) {
        printStar();
        System.out.println(o);
        printStar();
    }

    public static void printSubHeading(Object o) {
        System.out.println(o);
        printDash();

    }

    private static void printDash() {
        System.out.println("------------------------------------------------");
    }

    public static void print(Object... o) {
        Arrays.stream(o).forEach(System.out::println);
    }

    public static void printStar() {
        System.out.println("*********************************************************");
    }

    public static boolean isStrEmpty(String str) {
        return str.isEmpty();
    }

    public static <T> boolean isObjectEmpty(T t) {
        return t == null;
    }

    public static BufferedReader getBufferReaderFromFilePath(String path) throws FileNotFoundException {
        File file = ResourceUtils.getFile(path);
        return new BufferedReader(new FileReader(file));
    }

    public int acceptIntInput() {
        if (scanner.hasNextInt()) {
            return scanner.nextInt();
        } else {
            log.error("Invalid Input... Detected.");
        }
        return 0;
    }

    public int acceptIntInput(Integer token) {
        if (scanner.hasNextInt(token)) {
            return scanner.nextInt();
        } else {
            log.error("Invalid Input... Detected.");
        }
        return 0;
    }

    public String acceptStringInput() {
        if (scanner.hasNext()) {
            return scanner.next();
        } else {
            log.error("Invalid Input... Detected.");
        }
        return "exit";
    }

    public String acceptStringReadLine() {
        return scanner.next();
        //return "exit";
    }

    public String acceptStringInputWithoutSpace() {
        return scanner.next();
    }

    public char[] acceptPassword() {
        return console.readPassword();
    }

    public static <K, V> void storeInSession(K k, V v) {
        sessionMap.put(k, v);
    }

    public static <K> Object getFromSession(K k) {
        return sessionMap.get(k);
    }

    public static <K> Object ifKeyExists(K k) {
        return sessionMap.containsKey(k);
    }

    public static <K> Object deleteKeyIfExists(K k) {
        return sessionMap.remove(k);
    }

    public boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
