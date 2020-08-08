package com.pinfly.purchasecharge.core.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

/**
 * Generates unique, secure session IDs.
 */
public class SessionGenerator
{
    private static final Logger logger = Logger.getLogger (SessionGenerator.class);

    protected static final String DEFAULT_ALGORITHM = "MD5";
    protected static int sessionIdLength = 16;
    protected static int duplicates = 0;
    protected static MessageDigest digest;
    protected static Random random;
    protected static java.util.Map <String, String> sessions = new ConcurrentHashMap <String, String> ();

    public static synchronized String generateSessionId ()
    {
        byte random[] = new byte[16];
        String result = null;

        StringBuffer buffer = new StringBuffer ();
        do
        {
            int resultLenBytes = 0;
            if (result != null)
            {
                buffer = new StringBuffer ();
                duplicates++;
            }

            while (resultLenBytes < SessionGenerator.sessionIdLength)
            {
                getRandomBytes (random);
                random = getDigest ().digest (random);
                for (int j = 0; j < random.length && resultLenBytes < SessionGenerator.sessionIdLength; j++)
                {
                    byte b1 = (byte) ((random[j] & 0xf0) >> 4);
                    byte b2 = (byte) (random[j] & 0x0f);
                    if (b1 < 10)
                        buffer.append ((char) ('0' + b1));
                    else
                        buffer.append ((char) ('A' + (b1 - 10)));
                    if (b2 < 10)
                        buffer.append ((char) ('0' + b2));
                    else
                        buffer.append ((char) ('A' + (b2 - 10)));
                    resultLenBytes++;
                }
            }

            result = buffer.toString ();
        }
        while (sessions.containsKey (result));
        return (result);
    }

    protected static synchronized MessageDigest getDigest ()
    {
        if (SessionGenerator.digest == null)
        {
            try
            {
                digest = MessageDigest.getInstance (DEFAULT_ALGORITHM);
            }
            catch (NoSuchAlgorithmException e)
            {
                logger.error ("Unable to obtain a message digest algorithm: " + e, e);
                throw new RuntimeException ("Unable to obtain a message digest algorithm: " + e.getMessage ());
            }
        }
        return digest;
    }

    protected static void getRandomBytes (byte bytes[])
    {
        getRandom ().nextBytes (bytes);
    }

    public static Random getRandom ()
    {
        if (random == null)
        {
            // Calculate the new random number generator seed
            long seed = System.currentTimeMillis ();

            random = new java.util.Random ();
            random.setSeed (seed);
        }
        return (SessionGenerator.random);
    }
}
