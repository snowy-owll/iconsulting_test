package ee.iconsulting.pathfinder.xml;

import ee.iconsulting.pathfinder.search.ISearchHelper;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;

public class XMLProcessor {
    private final Element root;
    private final ISearchHelper searcher;
    private final List<String> found = new ArrayList<>();

    public XMLProcessor(Element root, ISearchHelper searcher) {
        this.root = root;
        this.searcher = searcher;
    }

    public List<String> processSearch() throws XMLInvalidException {
        found.clear();
        process(this.root, "");
        return found;
    }

    private void process(Element element, String path) throws XMLInvalidException {
        String tagName = element.getNodeName();
        if ("child".equals(tagName) || "node".equals(tagName)) {
            if ("false".equals(element.getAttribute("is-file"))) {
                processFileElement(element, path, tagName);
            } else if ("true".equals(element.getAttribute("is-file"))) {
                processNonFileElement(element, path);
            }
        }
    }

    private void processNonFileElement(Element element, String path) throws XMLInvalidException {
        Element fileName = getChildElementByName(element, "name");
        if (fileName == null) {
            throw new XMLInvalidException("Node 'name' not found or empty. Current path: " + path);
        }
        path += fileName.getTextContent();
        if (searcher.match(path)) {
            found.add(path);
        }
    }

    private void processFileElement(Element element, String path, String tagName) throws XMLInvalidException {
        Element pathName = getChildElementByName(element, "name");
        if (pathName == null || pathName.getTextContent() == null) {
            throw new XMLInvalidException("Node 'name' not found or empty. Current path: " + path);
        }
        path += pathName.getTextContent() + ((!"node".equals(tagName)) ? "/" : "");
        Element children = getChildElementByName(element, "children");
        if (children == null || !children.hasChildNodes()) {
            throw new XMLInvalidException("Node 'children' not found or empty. Current path: " + path);
        }
        for (Node el = children.getFirstChild(); el != null; el = el.getNextSibling()) {
            if (el.getNodeType() == Node.ELEMENT_NODE) {
                process((Element) el, path);
            }
        }
    }

    private Element getChildElementByName(Element parent, String name) {
        for (Node el = parent.getFirstChild(); el != null; el = el.getNextSibling()) {
            if (el instanceof Element && name.equals(el.getNodeName())) {
                return (Element) el;
            }
        }
        return null;
    }
}
