package ua.net.yason.corpus.utils;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 *
 * @author yason
 */
public class XmlUtils {

    public static void serializeAsXml(String fileName, Object data) {
        try (XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(fileName)))) {
            encoder.writeObject(data);
        } catch (FileNotFoundException ex) {
            System.err.println("Fail to save object as xml file" + ex.getMessage());
        }
    }

    public static <T> void saveAsXml(String fileName, Class<T> classs, T data) {
        try(OutputStream outstr = new BufferedOutputStream(new FileOutputStream(fileName))) {
            JAXBContext jaxbContext = JAXBContext.newInstance(classs);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(data, outstr);
        } catch (JAXBException | FileNotFoundException ex) {
            Logger.getLogger(XmlUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XmlUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
