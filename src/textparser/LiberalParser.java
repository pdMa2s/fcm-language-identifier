package textparser;

import textparser.ContextFileParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class LiberalParser implements ContextFileParser {
    @Override
    public String parse(File file) {
        String content = "";
        try (Scanner scanner = new Scanner(new FileInputStream(file), "UTF-8")) {
            StringBuilder sb = new StringBuilder();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                sb.append(line.trim());
                sb.append("\n");
            }
            sb.setLength(sb.length() - 1);
            content = sb.toString();
        } catch (IOException e) {
            System.err.println("ERROR while reading file");
            System.exit(2);
        }
        return content;
    }
}
