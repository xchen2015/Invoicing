package com.pinfly.purchasecharge.core.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class JAXBUtil
{
    /**
     * @param obj
     * @return
     */
    public static String marshal (Object jaxbObj) throws Exception
    {
        if (jaxbObj == null)
        {
            throw new IllegalArgumentException ("jaxbObj argument can not be null!");
        }

        StringWriter sw = null;
        try
        {
            sw = new StringWriter ();
            JAXBContext context = JAXBContext.newInstance (jaxbObj.getClass ());
            Marshaller marshaller = context.createMarshaller ();
            marshaller.setProperty (Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal (jaxbObj, sw);
            return sw.toString ();
        }
        catch (JAXBException e)
        {
            throw new Exception ("Failed to convert JAXB object to a String.", e);
        }
        finally
        {
            try
            {
                if (sw != null)
                {
                    sw.close ();
                }
            }
            catch (IOException ioe)
            {
            }
        }
    }

    /**
     * 
     */
    @SuppressWarnings ("rawtypes")
    public static Object unmarshal (Class clazz, String xmlStr) throws Exception
    {
        if (xmlStr == null || xmlStr.length () == 0)
        {
            throw new IllegalArgumentException ("XML can not be null or blank!");
        }

        StringReader sr = null;
        try
        {
            JAXBContext context = JAXBContext.newInstance (clazz);
            Unmarshaller unmarshaller = context.createUnmarshaller ();
            sr = new StringReader (xmlStr);
            Object xmlObject = unmarshaller.unmarshal (sr);
            return xmlObject;
        }
        catch (JAXBException e)
        {
            throw new Exception ("Failed to convert String object to a JAXB.", e);
        }
        finally
        {
            if (sr != null)
            {
                sr.close ();
            }
        }
    }

}
