package com.pinfly.purchasecharge.framework.print;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;

public class ChineseFont
{
    private final static Logger LOGGER = Logger.getLogger (ChineseFont.class);

    public static BaseFont baseFont;

    /** The normal type face in small size. */
    public static final Font SMALL;
    /** The normal type face in mediume size */
    public static final Font MEDIUM;
    /** The normal type face in medium-large size */
    public static final Font MEDIUM_LARGE;
    /** The normal type face in large size */
    public static final Font LARGE;
    /** The bold type face in small size */
    public static final Font SMALL_BOLD;
    /** The bold type face in medium size */
    public static final Font MEDIUM_BOLD;
    /** The bold type face in medium-large size */
    public static final Font MEDIUM_LARGE_BOLD;
    /** The bold type face in large size */
    public static final Font LARGE_BOLD;
    /** The monospace type face in small size */
    public static final Font SMALL_MONO;
    /** The monospace type face in medium size */
    public static final Font MEDIUM_MONO;
    /** The monospace type face in medium-large size */
    public static final Font MEDIUM_LARGE_MONO;
    /** The monospace type face in large size */
    public static final Font LARGE_MONO;

    // The sizes
    private static final float SMALL_SIZE = 8f;
    private static final float MEDIUM_SIZE = 10f;
    private static final float MEDIUM_LARGE_SIZE = 12f;
    private static final float LARGE_SIZE = 14f;

    static
    {
        InputStream is = null;
        Properties pro = null;
        try
        {
            is = ChineseFont.class.getResourceAsStream ("fontSrc.properties");
            pro = new Properties ();
            pro.load (is);
        }
        catch (FileNotFoundException e)
        {
            LOGGER.warn (e.getMessage ());
        }
        catch (IOException e)
        {
            LOGGER.warn (e.getMessage ());
        }
        finally
        {
            try
            {
                if (null != is)
                {
                    is.close ();
                }
            }
            catch (IOException e)
            {
                LOGGER.warn (e.getMessage ());
            }
        }

        try
        {
            baseFont = BaseFont.createFont (pro.getProperty ("fontSrc") + ":\\windows\\fonts\\simsun.ttc,1",
                                            BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        }
        catch (Exception e)
        {
            LOGGER.warn (e.getMessage ());
        }

        Font s = null;
        Font m = null;
        Font ml = null;
        Font l = null;
        Font s_b = null;
        Font m_b = null;
        Font ml_b = null;
        Font l_b = null;
        Font s_m = null;
        Font m_m = null;
        Font ml_m = null;
        Font l_m = null;
        try
        {
            // The normal fonts
            s = new Font (baseFont, SMALL_SIZE);
            m = new Font (baseFont, MEDIUM_SIZE);
            ml = new Font (baseFont, MEDIUM_LARGE_SIZE);
            l = new Font (baseFont, LARGE_SIZE);

            // The bold fonts
            (s_b = new Font (baseFont, SMALL_SIZE)).setStyle (Font.BOLD);
            (m_b = new Font (baseFont, MEDIUM_SIZE)).setStyle (Font.BOLD);
            (ml_b = new Font (baseFont, MEDIUM_LARGE_SIZE)).setStyle (Font.BOLD);
            (l_b = new Font (baseFont, 14f)).setStyle (Font.BOLD);

            // The mono-space fonts
            s_m = new Font (baseFont, SMALL_SIZE);
            m_m = new Font (baseFont, MEDIUM_SIZE);
            ml_m = new Font (baseFont, MEDIUM_LARGE_SIZE);
            l_m = new Font (baseFont, LARGE_SIZE);
        }
        catch (Exception e)
        {
            e.printStackTrace ();
        }

        SMALL = s;
        MEDIUM = m;
        MEDIUM_LARGE = ml;
        LARGE = l;

        SMALL_BOLD = s_b;
        MEDIUM_BOLD = m_b;
        MEDIUM_LARGE_BOLD = ml_b;
        LARGE_BOLD = l_b;

        SMALL_MONO = s_m;
        MEDIUM_MONO = m_m;
        MEDIUM_LARGE_MONO = ml_m;
        LARGE_MONO = l_m;
    }

    private ChineseFont ()
    {
    }

}
