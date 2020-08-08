package com.cxx.purchasecharge.core;

import com.pinfly.purchasecharge.core.config.PurchaseChargeProperties;
import com.pinfly.purchasecharge.core.util.PurchaseChargeConstants;

public class PurchaseChargePropertiesTest
{
    static
    {
        System.setProperty (PurchaseChargeConstants.PINFLY_PROPS_DIR, "C:/ChenXiangxiao/Cxx/Common/properties");
        System.setProperty (PurchaseChargeConstants.COMMON_PROPERTY_DIR, "C:/ChenXiangxiao/Cxx/Common");
        // System.setProperty (CoreConstants.PINFLY_PROPS_DIR,
        // "C:/cxx/Carefx/Common/properties");
        // System.setProperty (CoreConstants.COMMON_PROPERTY_DIR,
        // "C:/cxx/Carefx/Common");
    }

    public static void main (String[] args) throws Exception
    {
        System.out.println (PurchaseChargeProperties.getDbDriver ());
        // System.out.println (PurchaseChargeProperties.getDbUrl ());
        // System.out.println (PurchaseChargeProperties.getDbInstance ());
        // System.out.println (PurchaseChargeProperties.getDbUser ());
        // System.out.println (PurchaseChargeProperties.getDbPassword ());
        // System.out.println (PurchaseChargeProperties.getMockAdmin ());
        // System.out.println (PurchaseChargeProperties.getMockUser ());
        // System.out.println (PurchaseChargeProperties.getMockAdminRole ());
        // System.out.println (PurchaseChargeProperties.getMockUserRole ());
        // System.out.println (PurchaseChargeProperties.getInitUserFlag ());
        // System.out.println (PurchaseChargeProperties.getDefaultPassword ());
        // System.out.println (PurchaseChargeProperties.getAddInorderPaidComment
        // ());
        // System.out.println (PurchaseChargeProperties.getDelInorderPaidComment
        // ());
        // System.out.println
        // (PurchaseChargeProperties.getAddOutorderPaidComment ());
        // System.out.println
        // (PurchaseChargeProperties.getDelOutorderPaidComment ());
        // System.out.println
        // (PurchaseChargeProperties.getViewPaidDaysForSalesman ());

        System.out.println (PurchaseChargeProperties.getInstance (PurchaseChargeProperties.PURCHASE_CHARGE_PROPERTIES_FILE)
                                                    .getConfig (PurchaseChargeConstants.PRINT_ORDER_HEADER));
        System.out.println (PurchaseChargeProperties.getInstance (PurchaseChargeProperties.PURCHASE_CHARGE_PROPERTIES_FILE)
                                                    .getConfig (PurchaseChargeConstants.PRINT_ORDER_FOOTER));
        System.out.println (PurchaseChargeProperties.getInstance ().getConfig (PurchaseChargeConstants.HOST_PAGE));

        //
        // Date end = new Date ();
        // System.out.println ("end: " + end);
        // Date start = new Date (end.getTime () - (90 * 24 * 3600
        // * 1000));
        // System.out.println ("start: " + start);
        //
        // Calendar c = Calendar.getInstance ();
        // Date end2 = c.getTime ();
        // System.out.println (end2);
        // Calendar c2 = Calendar.getInstance ();
        // c2.setTimeInMillis (end2.getTime () - (90 * 24 * 3600
        // * 1000));
        // Date start2 = c2.getTime ();
        // System.out.println (start2);
        //
        // System.out.println("CxxPropertiesFactory---------" +
        // CxxPropertiesFactory.getInstance().getProperties("pc-core-config").getProperty("pc.testPassword"));

        // String orderPrintTemplate =
        // FileSystemUtil.readFileToString("orderTableTemplate.html");
        // System.out.println(orderPrintTemplate.replace("{:title}",
        // "Order Print Template"));
        // //String thStr =
        // orderPrintTemplate.substring(orderPrintTemplate.indexOf("<th "),
        // orderPrintTemplate.lastIndexOf("</th>") + 5);
        // Pattern pattern = Pattern.compile("<th id=\".*@\"");
        // Matcher matcher = pattern.matcher(orderPrintTemplate);
        // List<String> headers = new ArrayList<String>();
        // boolean found = false;
        // while (matcher.find()) {
        // String th = matcher.group();
        // //System.out.println(th);
        // Pattern pattern2 = Pattern.compile("\".*@");
        // Matcher matcher2 = pattern2.matcher(th);
        // while(matcher2.find())
        // {
        // String ths = matcher2.group();
        // //System.out.println(matcher2.group());
        // headers.add(ths.substring(1, ths.lastIndexOf("@")));
        // found = true;
        // }
        // }
        // if (!found) {
        // System.out.println("No match found.");
        // }
        // System.out.println(headers);
    }
}
