package cardealer.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class XmlParserImpl<T> implements XmlParser {

    public XmlParserImpl() {
    }

    @Override
    public <T> T fromFile(Class<T> klass, String path) throws JAXBException, FileNotFoundException {
        JAXBContext context = JAXBContext.newInstance(klass);
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path))));
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (T) unmarshaller.unmarshal(reader);
    }

    @Override
    public <T> void toFile(T obj, Class<T> klass, String path) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(klass);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, StandardCharsets.UTF_8.displayName());
        marshaller.marshal(obj, new File(path));
    }

}
