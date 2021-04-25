import java.io.*;
import java.util.regex.Pattern;

public class Filter {

    private Pattern regex;

    private String word;

    private final Boolean invert;

    private final Boolean ignoreRegister;

    public Filter(Pattern regex, Boolean invert, Boolean ignoreRegister) {
        this.regex = regex;
        this.invert = invert;
        this.ignoreRegister = ignoreRegister;
    }
    public Filter(String word, Boolean invert, Boolean ignoreRegister) {
        this.word = word;
        this.invert = invert;
        this.ignoreRegister = ignoreRegister;
    }

    public void filtrate(String inputName, String outputName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputName))) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputName))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (invert) {
                        if (ignoreRegister) {
                            if (!line.toLowerCase().contains(word.toLowerCase())) {
                                writer.write(line);
                            }
                        } else {
                            if (!line.contains(word)) {
                                writer.write(line);
                            }
                        }
                    } else if (ignoreRegister) {
                        if (line.toLowerCase().contains(word.toLowerCase())) {
                            writer.write(line);
                        }
                    } else {
                        if (line.contains(word)) {
                            writer.write(line);
                        }
                    }
                }
            }
        }
    }
}
