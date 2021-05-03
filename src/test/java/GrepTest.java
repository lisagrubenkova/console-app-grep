import org.junit.Test;

import java.io.*;
import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;

public class GrepTest {

    private boolean assertFileContent(String expectedFileName, String actualFileName) throws IOException {
        try (BufferedReader expected = new BufferedReader(new FileReader(expectedFileName))) {
            try (BufferedReader actual = new BufferedReader(new FileReader(actualFileName))) {
                String expectedLine;
                String actualLine;
                while ((actualLine = actual.readLine()) != null) {
                    expectedLine = expected.readLine();
                    if (!actualLine.contentEquals(expectedLine)) return false;
                }
                if (expected.readLine() != null) return false;
            }
    }
        return true;
    }

    @Test(expected = Exception.class)
    public void parseLineException() throws IOException {
        GrepLauncher launcher = new GrepLauncher();
        launcher.main("-v -i -word B:\\console-app-grep\\input\\input.txt".split(" "));
    }

    @Test
    public void regex() throws IOException {
        Grep testGrep = new Grep(Pattern.compile("\\d{2}"), false, false);
        testGrep.filtrate("B:\\console-app-grep\\input\\input.txt");
        assertTrue(assertFileContent("B:\\console-app-grep\\output\\expectedOutput1.txt", "B:\\console-app-grep\\output\\output.txt"));
    }

    @Test
    public void word() throws IOException {
        GrepLauncher launcher = new GrepLauncher();
        launcher.main("-word это B:\\console-app-grep\\input\\input.txt".split(" "));
        assertTrue(assertFileContent("B:\\console-app-grep\\output\\expectedOutput2.txt", "B:\\console-app-grep\\output\\output.txt"));
    }

    @Test
    public void invertation() throws IOException {
        Grep testGrep = new Grep(Pattern.compile("\\d{2}"), true, false);
        testGrep.filtrate("B:\\console-app-grep\\input\\input.txt");
        assertTrue(assertFileContent("B:\\console-app-grep\\output\\expectedOutput3.txt", "B:\\console-app-grep\\output\\output.txt"));
    }

    @Test
    public void ignoreRegister() throws IOException {
        Grep testGrep = new Grep(Pattern.compile(".*?\\b" + "это" + "\\b.*?"), false, true);
        testGrep.filtrate("B:\\console-app-grep\\input\\input.txt");
        assertTrue(assertFileContent("B:\\console-app-grep\\output\\expectedOutput4.txt", "B:\\console-app-grep\\output\\output.txt"));
    }
}
