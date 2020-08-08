package com.pinfly.purchasecharge.service.schedule;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;

import com.pinfly.purchasecharge.core.model.persistence.auditlog.Log;
import com.pinfly.purchasecharge.core.util.DateUtils;
import com.pinfly.purchasecharge.service.LogService;

public class DeleteLogJob implements Job
{
    private static final Logger logger = Logger.getLogger (DeleteLogJob.class);

    private LogService logService;
    private int deleteLogDaysAgo;

    @Override
    public void execute (JobExecutionContext context) throws JobExecutionException
    {
        logger.info ("DeleteLogJob start executing");
        try
        {
            if (null == logService)
            {
                this.logService = (LogService) context.getScheduler ().getContext ().get ("logService");
            }
            if (0 == deleteLogDaysAgo)
            {
                this.deleteLogDaysAgo = (Integer) context.getScheduler ().getContext ().get ("deleteLogDaysAgo");
            }

            Date d = DateUtils.getDateBefore (new Date (), this.deleteLogDaysAgo);
            List <Log> oldLogs = logService.getOldLog (d);
            if (CollectionUtils.isNotEmpty (oldLogs))
            {
                try
                {
                    logService.deleteLog (oldLogs);
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage ());
                }
            }
        }
        catch (SchedulerException e)
        {
            logger.warn (e.getMessage ());
        }
    }

}
