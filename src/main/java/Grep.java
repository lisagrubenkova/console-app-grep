import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Grep {

    private Pattern filterParameter;

    private final Boolean invert;

    private final Boolean ignoreRegister;

    public Grep(Pattern regex, Boolean invert, Boolean ignoreRegister) {
        this.filterParameter = regex;
        this.invert = invert;
        this.ignoreRegister = ignoreRegister;
    }

    public void filtrate(String inputName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputName))) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("B:\\console-app-grep\\output\\output.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (invert) {
                        if (ignoreRegister) {
                            Matcher matcher = filterParameter.matcher(line.toLowerCase());
                            if (!matcher.find()) {
                                System.out.println(line);
                                writer.write(line + "\n");
                            }
                        } else {
                            Matcher matcher = filterParameter.matcher(line);
                            if (!matcher.find()) {
                                System.out.println(line);
                                writer.write(line + "\n");
                            }
                        }
                    } else if (ignoreRegister) {
                        Matcher matcher = filterParameter.matcher(line.toLowerCase());
                        if (matcher.find()) {
                            System.out.println(line);
                            writer.write(line + "\n");
                        }
                    } else {
                        Matcher matcher = filterParameter.matcher(line);
                        if (matcher.find()) {
                            System.out.println(line);
                            writer.write(line + "\n");
                        }
                    }
                }
            }
        }
    }
}
