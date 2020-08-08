package com.pinfly.purchasecharge.service.schedule;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;

import com.pinfly.purchasecharge.core.config.PurchaseChargeProperties;
import com.pinfly.purchasecharge.core.model.OrderStatusCode;
import com.pinfly.purchasecharge.core.model.persistence.out.OrderOut;
import com.pinfly.purchasecharge.core.util.DateUtils;
import com.pinfly.purchasecharge.dal.DaoContext;
import com.pinfly.purchasecharge.service.PersistenceService;
import com.pinfly.purchasecharge.service.QueryService;

public class FinishOrderJob implements Job
{
    private static final Logger logger = Logger.getLogger (FinishOrderJob.class);

    private QueryService queryService;
    private PersistenceService persistenceService;

    private int finishOrderHoursAgo;

    @Override
    public void execute (JobExecutionContext context) throws JobExecutionException
    {
        logger.info ("FinishOrderJob start executing");
        try
        {
            if (null == queryService)
            {
                this.queryService = (QueryService) context.getScheduler ().getContext ().get ("queryService");
            }
            if (null == persistenceService)
            {
                this.persistenceService = (PersistenceService) context.getScheduler ().getContext ()
                                                                      .get ("persistenceService");
            }
            if (0 == finishOrderHoursAgo)
            {
                this.finishOrderHoursAgo = (Integer) context.getScheduler ().getContext ().get ("finishOrderHoursAgo");
            }

            Date d = DateUtils.getHourBefore (new Date (), this.finishOrderHoursAgo);
            List <OrderOut> orders = DaoContext.getOrderOutDao ().findOldOrder (OrderStatusCode.SHIPPED, DateUtils.date2Timestamp (d));
            if (CollectionUtils.isNotEmpty (orders))
            {
                for (OrderOut order : orders)
                {
                    try
                    {
                        persistenceService.updateOrderOutStatus (order.getId (),
                                                                 OrderStatusCode.COMPLETED,
                                                                 PurchaseChargeProperties.getInstance ()
                                                                                         .getConfigFormatted ("pc.finishOutOrderComment",
                                                                                                              this.finishOrderHoursAgo
                                                                                                                      + ""), null);
                    }
                    catch (Exception e)
                    {
                        logger.warn (e.getMessage ());
                    }
                }
            }
        }
        catch (SchedulerException e)
        {
            logger.warn (e.getMessage ());
        }
    }

}
