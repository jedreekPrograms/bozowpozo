package util;

import java.io.FileWriter;
import java.io.IOException;

public class CSVWriter {

    public static void append(String fileName, String line) {
        try (FileWriter fw = new FileWriter(fileName, true)) {
            fw.write(line + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}