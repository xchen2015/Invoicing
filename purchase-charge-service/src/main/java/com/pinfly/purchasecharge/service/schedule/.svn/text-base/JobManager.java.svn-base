package com.pinfly.purchasecharge.service.schedule;

import java.text.ParseException;

import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.pinfly.purchasecharge.dal.BackupAndRestore;
import com.pinfly.purchasecharge.service.LogService;
import com.pinfly.purchasecharge.service.PersistenceService;
import com.pinfly.purchasecharge.service.QueryService;

public class JobManager
{
    private static final Logger logger = Logger.getLogger (JobManager.class);

    private SchedulerFactory schFactory = null;
    private Scheduler sch = null;
    private JobDetail finishOrderJobDetail;
    private Trigger finishOrderJobTrigger;
    private JobDetail deleteLogJobDetail;
    private Trigger deleteLogJobTrigger;
    private JobDetail backupDataJobDetail;
    private Trigger backupDataJobTrigger;
    private JobDetail sendReportMailJobDetail;
    private Trigger sendReportMailJobTrigger;

    private String finishOrderSchedulerExpression = "10 * 12 * * ?";
    private int finishOrderHoursAgo = 24;
    private String deleteLogSchedulerExpression = "10 * 13 * * ?";
    private int deleteLogDaysAgo = 180;
    private String backupDataSchedulerExpression = "10 * 10 * * ?";
    private String sendReportMailSchedulerExpression = "10 * 11 * * ?";

    private QueryService queryService;
    private PersistenceService persistenceService;
    private LogService logService;
    private BackupAndRestore dataBackupAndLoad;

    public void init ()
    {
        logger.info ("JobManager start initiating");
        schFactory = new StdSchedulerFactory ();
        try
        {
            finishOrderJobDetail = JobBuilder.newJob (FinishOrderJob.class).withIdentity ("finishOrderJob").build ();
            finishOrderJobTrigger = createTrigger ("finishOrderJobTrigger", finishOrderSchedulerExpression,
                                                   finishOrderJobDetail);
            deleteLogJobDetail = JobBuilder.newJob (DeleteLogJob.class).withIdentity ("deleteLogJob").build ();
            deleteLogJobTrigger = createTrigger ("deleteLogJobTrigger", deleteLogSchedulerExpression,
                                                 deleteLogJobDetail);
            backupDataJobDetail = JobBuilder.newJob (BackupDataJob.class).withIdentity ("backupDataJob").build ();
            backupDataJobTrigger = createTrigger ("backupDataJobTrigger", backupDataSchedulerExpression,
                                                  backupDataJobDetail);
            sendReportMailJobDetail = JobBuilder.newJob (SendReportMailJob.class).withIdentity ("sendReportMailJob")
                                                .build ();
            sendReportMailJobTrigger = createTrigger ("sendReportMailJobTrigger", sendReportMailSchedulerExpression,
                                                      sendReportMailJobDetail);

            sch = schFactory.getScheduler ();
            sch.scheduleJob (finishOrderJobDetail, finishOrderJobTrigger);
            sch.scheduleJob (deleteLogJobDetail, deleteLogJobTrigger);
            sch.scheduleJob (backupDataJobDetail, backupDataJobTrigger);
            sch.scheduleJob (sendReportMailJobDetail, sendReportMailJobTrigger);

            sch.getContext ().put ("queryService", queryService);
            sch.getContext ().put ("persistenceService", persistenceService);
            sch.getContext ().put ("logService", logService);
            sch.getContext ().put ("dataBackupAndLoad", dataBackupAndLoad);
            sch.getContext ().put ("finishOrderHoursAgo", finishOrderHoursAgo);
            sch.getContext ().put ("deleteLogDaysAgo", deleteLogDaysAgo);
            sch.getContext ().put ("jobManager", this);
            sch.start ();
            logger.info ("JobManager end initiating");
        }
        catch (SchedulerException e)
        {
            logger.warn ("Exception attempting to create scheduler.", e);
        }
        catch (ParseException e)
        {
            logger.warn ("Exception attempting to parse schedule expression.", e);
        }
        catch (Exception e)
        {
            logger.warn ("Initiate job context failed.", e);
        }
    }

    private Trigger createTrigger (String triggerIdentity, String cronExpression, JobDetail jobDetail)
                                                                                                      throws ParseException
    {
        return TriggerBuilder.newTrigger ().withIdentity (triggerIdentity)
                             .withSchedule (CronScheduleBuilder.cronSchedule (cronExpression)).forJob (jobDetail)
                             .build ();
    }

    public QueryService getQueryService ()
    {
        return queryService;
    }

    public void setQueryService (QueryService queryService)
    {
        this.queryService = queryService;
    }

    public PersistenceService getPersistenceService ()
    {
        return persistenceService;
    }

    public void setPersistenceService (PersistenceService persistenceService)
    {
        this.persistenceService = persistenceService;
    }

    public LogService getLogService ()
    {
        return logService;
    }

    public void setLogService (LogService logService)
    {
        this.logService = logService;
    }

    public String getFinishOrderSchedulerExpression ()
    {
        return finishOrderSchedulerExpression;
    }

    public void setFinishOrderSchedulerExpression (String finishOrderSchedulerExpression)
    {
        this.finishOrderSchedulerExpression = finishOrderSchedulerExpression;
    }

    public String getDeleteLogSchedulerExpression ()
    {
        return deleteLogSchedulerExpression;
    }

    public void setDeleteLogSchedulerExpression (String deleteLogSchedulerExpression)
    {
        this.deleteLogSchedulerExpression = deleteLogSchedulerExpression;
    }

    public int getFinishOrderHoursAgo ()
    {
        return finishOrderHoursAgo;
    }

    public void setFinishOrderHoursAgo (int finishOrderHoursAgo)
    {
        this.finishOrderHoursAgo = finishOrderHoursAgo;
    }

    public int getDeleteLogDaysAgo ()
    {
        return deleteLogDaysAgo;
    }

    public void setDeleteLogDaysAgo (int deleteLogDaysAgo)
    {
        this.deleteLogDaysAgo = deleteLogDaysAgo;
    }

    public void setBackupDataSchedulerExpression (String backupDataSchedulerExpression)
    {
        this.backupDataSchedulerExpression = backupDataSchedulerExpression;
    }

    public void setSendReportMailSchedulerExpression (String sendReportMailSchedulerExpression)
    {
        this.sendReportMailSchedulerExpression = sendReportMailSchedulerExpression;
    }

    public void setDataBackupAndLoad (BackupAndRestore dataBackupAndLoad)
    {
        this.dataBackupAndLoad = dataBackupAndLoad;
    }
}
