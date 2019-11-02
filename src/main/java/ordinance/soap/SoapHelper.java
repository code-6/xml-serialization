package ordinance.soap;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;

public class SoapHelper {

    public SoapHelper() throws ParserConfigurationException, SAXException, IOException, TransformerException {
        System.out.println("Hello Mother Fucker!");
        System.out.println(addAttribute(getRequestBody()));
    }

    public String addAttribute(String xml) throws IOException, ParserConfigurationException, SAXException, TransformerException {
        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = db.parse(new ByteArrayInputStream(xml.getBytes()));
        Element e = (Element) document.getFirstChild();

        Attr attr = document.createAttribute("xmlns");
        attr.setValue("http://www.imsglobal.org/xsd/ims_qtiasiv1p2");

        e.setAttributeNode(attr);

        return getStringFromDocument(document);
    }

    public String getRequestBody(){
        return "<Authorization>\n" +
                "\t<docCode>sdjhfiasdfgfd</docCode>\n" +
                "\t<bankCOde>ksdgfakjsfg</bankCOde>\n" +
                "</Authorization>";
    }

    public static String getStringFromDocument(Document doc) throws TransformerException {
        DOMSource domSource = new DOMSource(doc);
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.transform(domSource, result);
        return writer.toString();
    }

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException, TransformerException {
        new SoapHelper();
    }
}
