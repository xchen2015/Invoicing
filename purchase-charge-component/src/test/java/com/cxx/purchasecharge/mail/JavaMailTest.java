package com.cxx.purchasecharge.mail;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.junit.Test;

import com.pinfly.common.config.PfyPropertiesFactory;
import com.pinfly.common.util.Mail;

public class JavaMailTest
{
    static
    {
        // System.setProperty ("pinfly.props.dir",
        // "C:/ChenXiangxiao/Cxx/Common/properties");
        System.setProperty ("pinfly.props.dir", "C:/cxx/Carefx/Common/properties");
    }

    @Test
    public void test1 () throws Exception
    {
        String POST_MASTER = "mail.postmaster";
        String PASSWORD = "mail.password";
        Properties props = PfyPropertiesFactory.getProperties ("email");
        final String postmaster = props.getProperty (POST_MASTER, "");
        final String password = props.getProperty (PASSWORD, "");
        Authenticator authenticator = new javax.mail.Authenticator ()
        {
            protected PasswordAuthentication getPasswordAuthentication ()
            {
                return new PasswordAuthentication (postmaster, password);
            }
        };
        Session session = Session.getDefaultInstance (props, authenticator);

        String toAddr = "379041884@qq.com";
        String subject = "中文标题测试";
        String text = "中文正文测试<table width='100%'><col align='left' width='25%' /><col align='left' width='25%' /><col align='left' width='25%' /><col align='left' width='25%' /><thead><tr><th id='sumOrderReceivable@' align='left' style='border-bottom:1px solid'>销售额</th><th id='sumProfit@' align='left' style='border-bottom:1px solid'>毛利润</th><th id='sumCustomerUnPay@' align='left' style='border-bottom:1px solid'>客户欠款</th><th id='sumProviderUnPay@' align='left' style='border-bottom:1px solid'>供应商欠款</th></tr></thead><tbody><tr><td>52611.64</td><td>10195.18</td><td>52069.43</td><td>21754.67</td></tr></tbody></table>";
        try
        {
            // create the message
            Message msg = new MimeMessage (session);

            msg.setFrom (new InternetAddress (postmaster));
            InternetAddress[] toAddrs = new InternetAddress[]
            { new InternetAddress (toAddr) };
            msg.setRecipients (Message.RecipientType.TO, toAddrs);
            // msg.setSubject (MimeUtility.encodeText (subject, "utf-8", "B"));
            msg.setSubject (subject);
            msg.setSentDate (new Date ());
            // 设置优先级(1:紧急 3:普通 5:低)
            msg.addHeader ("X-Priority", "5");
            // msg.setText (text);
            Multipart contentMultipart = new MimeMultipart ();
            MimeBodyPart messageBodyPart = new MimeBodyPart ();
            messageBodyPart.setContent (text, "text/html;charset=utf-8");
            // messageBodyPart.setContent (text, "text/plain;charset=utf-8");
            // messageBodyPart.setHeader ("Content-Transfer-Encoding",
            // "base64");
            contentMultipart.addBodyPart (messageBodyPart);
            msg.setContent (contentMultipart);

            // send the message
            Transport.send (msg);

        }
        catch (Exception ae)
        {
        }

        // Mail.sendMessageToOne (toAddr, subject, text);
        System.out.println ("Sent message successfully....");
    }

    @Test
    public void test2 ()
    {
        String[] toAddrs =
        { "379041884@qq.com" };
        String subject = "中文标题测试";
        // String text =
        // "中文正文测试<table width='100%'><col align='left' width='25%' /><col align='left' width='25%' /><col align='left' width='25%' /><col align='left' width='25%' /><thead><tr><th id='sumOrderReceivable@' align='left' style='border-bottom:1px solid'>销售额</th><th id='sumProfit@' align='left' style='border-bottom:1px solid'>毛利润</th><th id='sumCustomerUnPay@' align='left' style='border-bottom:1px solid'>客户欠款</th><th id='sumProviderUnPay@' align='left' style='border-bottom:1px solid'>供应商欠款</th></tr></thead><tbody><tr><td>52611.64</td><td>10195.18</td><td>52069.43</td><td>21754.67</td></tr></tbody></table>";
        String text = "中文正文测试";
        Mail.sendMessage (toAddrs, subject, text, "src/test/java/com/cxx/purchasecharge/mail/mailAttach.txt");
        System.out.println ("Sent message successfully....");
    }

}
