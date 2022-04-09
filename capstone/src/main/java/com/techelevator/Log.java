package com.techelevator;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Log {
    public static void log(String message) {
        File file = new File("C:\\Users\\zanka\\Desktop\\meritamerica\\repos\\module-1-capstone\\capstone\\log.txt");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        LocalDateTime currentDateTime = LocalDateTime.now();
        try {
            file.createNewFile();
        }
        catch(IOException e) {
            System.err.println("Unable to create log file.");
        }
        try(PrintWriter printWriter = new PrintWriter(new FileOutputStream(file, true))) {
            printWriter.println(dtf.format(currentDateTime) + " " + message);
        } catch (FileNotFoundException e) {
            System.err.println("Unable to write to log file.");
        }
    }
}
