package com.pinfly.purchasecharge.service.schedule;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;

import com.pinfly.purchasecharge.core.util.PurchaseChargeUtils;
import com.pinfly.purchasecharge.dal.BackupAndRestore;

public class BackupDataJob implements Job
{
    private static final Logger logger = Logger.getLogger (BackupDataJob.class);

    private BackupAndRestore dataBackupAndLoad;

    @Override
    public void execute (JobExecutionContext context) throws JobExecutionException
    {
        logger.info ("BackupDataJob execute start");
        try
        {
            if (null == dataBackupAndLoad)
            {
                this.dataBackupAndLoad = (BackupAndRestore) context.getScheduler ().getContext ()
                                                                   .get ("dataBackupAndLoad");
            }
        }
        catch (SchedulerException e)
        {
            logger.warn (e.getMessage ());
        }

        try
        {
            dataBackupAndLoad.backup (PurchaseChargeUtils.getDataBackupStoreDirectory ());
        }
        catch (Exception e)
        {
            logger.warn (e.getMessage ());
        }
    }

}
