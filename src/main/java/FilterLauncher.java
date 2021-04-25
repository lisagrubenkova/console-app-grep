import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.IOException;
import java.util.regex.Pattern;

public class FilterLauncher {
    @Option(name = "-word", forbids = {"-r"})
    private String wordFilter;

    @Option(name = "-r", forbids = {"-word"})
    private Pattern regexFilter;

    @Option(name = "-v")
    private Boolean invertation = false;

    @Option(name = "-i")
    private Boolean ignoreRegister = false;

    @Argument(required = true, metaVar = "InputName", usage = "Input file name")
    private String inputFileName;

    @Argument(required = true, metaVar = "OutputName", index = 1, usage = "Output file name")
    private String outputFileName;

    public static void main(String[] args) throws IOException {
        new FilterLauncher().launch(args);
    }

    private void launch(String[] args) throws IOException {
        CmdLineParser parser = new CmdLineParser(this);

        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("java -jar Recoder.jar -ie EncodingI -oe EncodingO InputName OutputName");
            parser.printUsage(System.err);
            throw new IllegalArgumentException("");
        }
        Filter filter = null;
        if (regexFilter != null) filter = new Filter(regexFilter, invertation, ignoreRegister);
                else if (wordFilter != null) filter = new Filter(wordFilter, invertation, ignoreRegister);
                else { System.err.println("Error entering arguments (for correct input, see the example)");
        System.err.println("pack-rle [options...] arguments...");
        parser.printUsage(System.err);
        System.err.println("\nExample: pack-rle [-u|-z] [-out outputname.txt] inputname.txt");
        //кидаем исключение для того, чтобы мы могли сделать тесты
        throw new IllegalArgumentException(""); }
        assert filter != null;
        filter.filtrate(inputFileName, outputFileName);
    }
}