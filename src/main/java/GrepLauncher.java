import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.IOException;
import java.util.regex.Pattern;

public class GrepLauncher {
    @Option(name = "-v")
    private Boolean invertation = false;

    @Option(name = "-i")
    private Boolean ignoreRegister = false;

    @Option(name = "-r", forbids = {"-word"})
    private Pattern regexFilter;

    @Option(name = "-word", forbids = {"-r"})
    private String wordFilter;

    @Argument(required = true, metaVar = "InputName", usage = "Input file name")
    private String inputFileName;

    public static void main(String[] args) throws IOException {
        new GrepLauncher().launch(args);
    }

    private void launch(String[] args) throws IOException {
        CmdLineParser parser = new CmdLineParser(this);

        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("java -jar Grep.jar -v -i (-r regexFilter | -word wordFilter) InputName");
            parser.printUsage(System.err);
        }
        if (ignoreRegister) { wordFilter.toLowerCase(); }
        Pattern filterParameter = null;
        if (regexFilter != null) {
            filterParameter = regexFilter;
        } else if (wordFilter != null) {
            filterParameter = Pattern.compile(".*?\\b" + wordFilter + "\\b.*?");
        } else {
            System.err.println("Error entering arguments (for correct input, see the example)");
        }
        Grep filter = new Grep(filterParameter, invertation, ignoreRegister);
        filter.filtrate(inputFileName);
    }
}