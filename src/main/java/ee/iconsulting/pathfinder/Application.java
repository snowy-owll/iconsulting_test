package ee.iconsulting.pathfinder;

import ee.iconsulting.pathfinder.search.SearchHelper;
import ee.iconsulting.pathfinder.xml.XMLInvalidException;
import ee.iconsulting.pathfinder.xml.XMLProcessor;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import picocli.CommandLine;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static ee.iconsulting.pathfinder.xml.XMLLoader.loadXML;

@CommandLine.Command(name = "assignment",
        description = "Prints all full paths for the given XML file containing a representation of "
                + "files hierarchy and search string",
        separator = " ")
public class Application implements Runnable {
    @CommandLine.Option(names = {"-f"}, required = true, paramLabel = "<input>",
            description = "Input XML file with files hierarchy")
    private File input = null;

    @CommandLine.Option(names = {"-s"}, paramLabel = "<arg> | '<arg>'",
            description = "Search string to filter paths with. Use single quotes for set search mask")
    private String search = null;

    @CommandLine.Option(names = {"-S"}, paramLabel = "'<arg>'",
            description = "Extended search string to filter paths with")
    private String extendedSearch = null;

    @CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "Display this help message")
    boolean help = false;

    public void run() {
        if (search != null && extendedSearch != null) {
            throw new CommandLine.ParameterException(new CommandLine(this),
                    "Only one of the -s or -S options is allowed");
        }
        try {
            boolean extended = extendedSearch != null;
            Element root = loadXML(input);
            XMLProcessor xmlProcessor = new XMLProcessor(root,
                    new SearchHelper((extended) ? extendedSearch : search, extended));
            List<String> found = xmlProcessor.processSearch();
            if (found.size() == 0) {
                System.out.println("Cannot find files by the specified search string");
                return;
            }
            System.out.print(String.join("\n", found));
        } catch (XMLInvalidException e) {
            throw new CommandLine.ExecutionException(new CommandLine(this), e.getMessage());
        } catch (SAXException | ParserConfigurationException e) {
            throw new CommandLine.ExecutionException(new CommandLine(this),
                    "Cannot parse the XML file: " + e.getMessage());
        } catch (IOException e) {
            throw new CommandLine.ExecutionException(new CommandLine(this),
                    "Cannot open the XML file");
        }
    }

    public static void main(String[] args) {
        CommandLine.run(new Application(), args);
    }
}
