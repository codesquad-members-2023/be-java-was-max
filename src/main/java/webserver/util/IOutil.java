package webserver.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class IOutil {

    public static String readFromInputStream(BufferedReader br) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append("\r\n");
        }
        return sb.toString().trim();
    }

    public static void writeToOutputStream(PrintWriter writer, String inputString) {
        writer.println(inputString);
    }
}
