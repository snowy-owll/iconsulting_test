package ee.iconsulting.test;

import ee.iconsulting.pathfinder.Application;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class E2ETest extends TestBase {
    @Test
    @DisplayName("e2e with real input file and no search input")
    void e2eNoSearch() {
        File xmlFile = getResourceFile("test-files-pdf.xml");

        String[] params = {
                "-f", getRelativePath(xmlFile)
        };

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Application.main(params);

        String actualResult = new String(out.toByteArray());
        String expectedResult = String.join("\n",
                "/file-776194140.xml",
                "/dir-880176375/file-1073842118.java",
                "/dir-880176375/dir-2145307015/file-1498940214.xhtml");

        assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("e2e with real input file and simple search input")
    void e2eSimpleSearch() {
        File xmlFile = getResourceFile("test-files.xml");

        String[] params = {
                "-f", getRelativePath(xmlFile),
                "-s", "file-1437276269.java"
        };

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Application.main(params);

        String actualResult = new String(out.toByteArray());
        String expectedResult = "/dir-1637357383/dir-740182544/file-1437276269.java";

        assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("e2e with real input file and simple search with mask input")
    void e2eSimpleSearchMask() {
        File xmlFile = getResourceFile("test-files-pdf.xml");

        String[] params = {
                "-f", getRelativePath(xmlFile),
                "-s", "'*.java'"
        };

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Application.main(params);

        String actualResult = new String(out.toByteArray());
        String expectedResult = "/dir-880176375/file-1073842118.java";

        assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("e2e with real input file and simple search input")
    void e2eExtendedSearch() {
        File xmlFile = getResourceFile("test-files.xml");

        String[] params = {
                "-f", getRelativePath(xmlFile),
                "-S", "'.*?[a-z]{4}-7\\d+70\\.[a-z]+'"
        };

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Application.main(params);

        String actualResult = new String(out.toByteArray());
        String expectedResult = String.join("\n",
                "/dir-2096609034/dir-772906113/dir-668533023/file-74072070.txt",
                "/dir-448307493/dir-1235278049/dir-1683691667/file-728918970.xhtml",
                "/dir-1637357383/dir-740182544/dir-735358292/file-701516470.xhtml");

        assertEquals(expectedResult, actualResult);
    }

    private String getRelativePath(File file) {
        return new File("").toURI().relativize(file.toURI()).getPath();
    }
}
