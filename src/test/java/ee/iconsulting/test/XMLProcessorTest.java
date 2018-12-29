package ee.iconsulting.test;


import ee.iconsulting.pathfinder.xml.XMLProcessor;
import ee.iconsulting.test.util.TestSearchHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Element;

import java.util.List;

import static ee.iconsulting.pathfinder.xml.XMLLoader.loadXML;
import static org.junit.jupiter.api.Assertions.assertEquals;

class XMLProcessorTest {
    @Test
    @DisplayName("process xml")
    void processAndCompare() throws Exception {
        String expected = "/dir-880176375/file-1073842118.java";
        String testXml = "<node is-file=\"false\">\n" +
                "    <name>/</name>\n" +
                "    <children>\n" +
                "        <child is-file=\"false\">\n" +
                "            <name>dir-880176375</name>\n" +
                "            <children>\n" +
                "                <child is-file=\"true\">\n" +
                "                    <name>file-1073842118.java</name>\n" +
                "                </child>\n" +
                "            </children>\n" +
                "        </child>\n" +
                "    </children>\n" +
                "</node>";
        Element root = loadXML(testXml);
        XMLProcessor xmlProcessor = new XMLProcessor(root, new TestSearchHelper());
        List<String> found = xmlProcessor.processSearch();
        assertEquals(expected, String.join("\n", found));
    }
}
