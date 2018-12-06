package cardealer.util;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

public interface XmlParser {

     <T> T fromFile(Class<T> klass, String path) throws JAXBException, FileNotFoundException;

     <T> void toFile(T obj, Class<T> klass, String path) throws JAXBException;
}
