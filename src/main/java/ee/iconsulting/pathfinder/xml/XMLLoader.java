package ee.iconsulting.pathfinder.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;

import static java.nio.charset.StandardCharsets.UTF_8;

public class XMLLoader {
    public static Element loadXML(File file) throws IOException, ParserConfigurationException, SAXException {
        InputStream inputStream = new FileInputStream(file);
        Reader reader = new InputStreamReader(inputStream, UTF_8);
        return loadXML(new InputSource(reader));
    }

    public static Element loadXML(String str) throws IOException, ParserConfigurationException, SAXException {
        Reader reader = new StringReader(str);
        return loadXML(new InputSource(reader));
    }

    private static Element loadXML(InputSource source) throws IOException, ParserConfigurationException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(source);
        document.getDocumentElement().normalize();
        return document.getDocumentElement();
    }
}
