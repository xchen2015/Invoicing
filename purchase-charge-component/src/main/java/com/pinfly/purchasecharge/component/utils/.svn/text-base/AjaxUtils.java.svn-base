package com.pinfly.purchasecharge.component.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.StringTokenizer;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.web.context.request.WebRequest;

import com.pinfly.purchasecharge.component.bean.ActionResult;

public class AjaxUtils
{
    private AjaxUtils ()
    {
    }

    public static boolean isAjaxRequest (WebRequest webRequest)
    {
        String requestedWith = webRequest.getHeader ("X-Requested-With");
        return requestedWith != null ? "XMLHttpRequest".equals (requestedWith) : false;
    }

    public static boolean isAjaxUploadRequest (WebRequest webRequest)
    {
        return webRequest.getParameter ("ajaxUpload") != null;
    }

    public static String getJsonObject (ActionResult actionResult)
    {
        JSONObject jsonObject = JSONObject.fromObject (actionResult);
        return jsonObject.toString ();
    }

    @SuppressWarnings ("rawtypes")
    public static String getJsonArray (List list)
    {
        JSONArray jsonArray = JSONArray.fromObject (list);
        return jsonArray.toString ();
    }

    /**
     * Tokenize string into a string array using default delimiter (;,
     * [whitespace])
     * 
     * @param str
     * @return
     */
    public static String[] tokenize (String str)
    {
        return tokenize (str, "; ,");
    }

    /**
     * Tokenize string into a string array using privided delimiter
     * 
     * @param str
     * @param delimiters
     * @return
     */
    public static String[] tokenize (String str, String delimiters)
    {
        StringTokenizer st = new StringTokenizer (str, delimiters);
        String[] array = new String[st.countTokens ()];
        int i = 0;
        while (st.hasMoreTokens ())
        {
            array[i] = st.nextToken ();
            i++;
        }
        return array;
    }

    /**
     * Serialize an object to a file.
     * 
     * @param obj
     * @param fileName
     */
    public static void serialize (Serializable obj, String fileName)
    {
        if (obj == null)
        {
            return;
        }
        ObjectOutputStream oos = null;
        try
        {
            oos = new ObjectOutputStream (new FileOutputStream (fileName));
            oos.writeObject (obj);
            oos.flush ();
            oos.close ();
        }
        catch (Exception e)
        {
            throw new RuntimeException ("Failed to save object to file: " + fileName + ", obj: " + (obj.getClass ())
                                        + ", ERROR: " + e.toString ());
        }
        finally
        {
            try
            {
                if (oos != null)
                {
                    oos.close ();
                }
            }
            catch (Exception e2)
            {

            }
        }
    }

    /**
     * Deserialize an object from a file.
     * 
     * @param fileName
     * @return
     */
    public static Serializable deserialize (String fileName)
    {
        ObjectInputStream ois = null;
        try
        {
            ois = new ObjectInputStream (new FileInputStream (fileName));
            return ois.read ();
        }
        catch (Exception e)
        {
            throw new RuntimeException ("Failed to read object from fileName: " + fileName);
        }
        finally
        {
            try
            {
                if (ois != null)
                {
                    ois.close ();
                }
            }
            catch (Exception e2)
            {

            }
        }
    }

    /**
     * Deep clone a Serializable object.
     * 
     * @param toClone
     * @return
     */
    public static Object deepClone (Serializable toClone)
    {
        if (toClone == null)
            return null;
        try
        {
            Object clone = null;
            ByteArrayOutputStream bout = new ByteArrayOutputStream ();
            ObjectOutputStream out = new ObjectOutputStream (new BufferedOutputStream (bout));
            out.writeObject (toClone);
            out.flush ();
            out.close ();
            ObjectInputStream in = new ObjectInputStream (
                                                          new BufferedInputStream (
                                                                                   new ByteArrayInputStream (
                                                                                                             bout.toByteArray ())));
            clone = in.readObject ();
            in.close ();
            return clone;
        }
        catch (Exception e)
        {
            e.printStackTrace ();
            return null;
        }
    }

    /**
     * Test if two Objects are equivalent of each other. Especially in String,
     * where null is the same as white space.
     * 
     * @param o1
     * @param o2
     * @return
     */
    public static boolean isEquivalent (Object o1, Object o2)
    {
        if (o1 == o2)
        {
            return true;
        }
        if (o1 == null && o2 instanceof String)
        {
            return o2 == null || "".equals (o2);
        }
        if (o2 == null && o1 instanceof String)
        {
            return o1 == null || "".equals (o1);
        }
        return o1 != null && o1.equals (o2);
    }

}