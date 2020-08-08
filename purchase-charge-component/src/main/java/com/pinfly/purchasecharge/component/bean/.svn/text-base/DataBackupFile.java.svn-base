package com.pinfly.purchasecharge.component.bean;

import java.util.Date;

import com.pinfly.purchasecharge.core.util.DateUtils;
import com.pinfly.purchasecharge.core.util.PurchaseChargeConstants;

public class DataBackupFile extends BaseBean implements Comparable <DataBackupFile>
{
    private static final long serialVersionUID = 1L;

    private String fileName;
    private long fileSize;
    private String createdDate;

    public String getFileName ()
    {
        return fileName;
    }

    public void setFileName (String fileName)
    {
        this.fileName = fileName;
    }

    public long getFileSize ()
    {
        return fileSize;
    }

    public void setFileSize (long fileSize)
    {
        this.fileSize = fileSize;
    }

    public String getCreatedDate ()
    {
        return createdDate;
    }

    public void setCreatedDate (String createdDate)
    {
        this.createdDate = createdDate;
    }

    @Override
    public int compareTo (DataBackupFile o)
    {
        if (null != o)
        {
            Date d1 = DateUtils.string2Date (this.id, PurchaseChargeConstants.FILE_NAME_DATE_TIME);
            Date d2 = DateUtils.string2Date (o.id, PurchaseChargeConstants.FILE_NAME_DATE_TIME);
            if (null != d1 && null != d2)
            {
                return d2.compareTo (d1);
            }
        }
        return 0;
    }
}
