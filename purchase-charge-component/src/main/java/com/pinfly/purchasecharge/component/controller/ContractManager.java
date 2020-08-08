package com.pinfly.purchasecharge.component.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pinfly.purchasecharge.component.bean.ActionResult;
import com.pinfly.purchasecharge.component.bean.ActionResult.ActionResultStatus;
import com.pinfly.purchasecharge.component.bean.ContractBean;
import com.pinfly.purchasecharge.component.bean.DataGridRequestForm;
import com.pinfly.purchasecharge.component.bean.SearchRequestForm;
import com.pinfly.purchasecharge.component.bean.auditlog.LogEventName;
import com.pinfly.purchasecharge.component.utils.AjaxUtils;
import com.pinfly.purchasecharge.component.utils.ComponentMessage;
import com.pinfly.purchasecharge.component.utils.LogUtil;
import com.pinfly.purchasecharge.core.model.ContractTypeCode;
import com.pinfly.purchasecharge.core.model.LoginUser;
import com.pinfly.purchasecharge.core.model.persistence.Contract;
import com.pinfly.purchasecharge.core.model.persistence.in.Provider;
import com.pinfly.purchasecharge.core.model.persistence.out.Customer;
import com.pinfly.purchasecharge.core.model.persistence.usersecurity.User;
import com.pinfly.purchasecharge.core.util.DateUtils;
import com.pinfly.purchasecharge.dal.ContractDao;
import com.pinfly.purchasecharge.dal.DaoContext;
import com.pinfly.purchasecharge.service.exception.PcServiceException;

@Controller
@RequestMapping ("/contract")
public class ContractManager extends GenericController <ContractBean>
{
    private static final Logger logger = Logger.getLogger (ContractManager.class);
    private static String contractMessage = ComponentMessage.createMessage ("CONTRACT", "CONTRACT")
                                                            .getI18nMessageCode ();
    private static ContractDao contractDao = DaoContext.getContractDao ();

    @Override
    protected String getViewName (HttpServletRequest request)
    {
        return "contractManagement";
    }

    @Override
    protected String checkExist (@RequestParam String contractId, @RequestParam String contractName,
                                 HttpServletRequest request)
    {
        if (StringUtils.isBlank (contractId))
        {
            // new
            Contract obj = contractDao.findByName (contractName);
            if (null != obj)
            {
                return "false";
            }
        }
        else
        {
            // edit
            long id = Long.parseLong (contractId);
            Contract obj = contractDao.findByName (contractName);
            if (null != obj && obj.getId () != id)
            {
                return "false";
            }
        }

        return "true";
    }

    @Override
    protected String getModelBySearchForm (DataGridRequestForm dataGridRequestForm,
                                           SearchRequestForm searchRequestForm, HttpServletRequest request)
    {
        List <Contract> contracts = (List <Contract>) contractDao.findAll ();
        List <ContractBean> contractBeans = new ArrayList <ContractBean> ();
        for (Contract contract : contracts)
        {
            contractBeans.add (convert (contract));
        }

        JSONArray jsonArr = JSONArray.fromObject (contractBeans);
        return jsonArr.toString ();
    }

    @Override
    protected String getModelById (String id, HttpServletRequest request)
    {
        // TODO Auto-generated method stub
        return super.getModelById (id, request);
    }

    @Override
    protected String addModel (@Valid ContractBean bean, BindingResult bindingResult, HttpServletRequest request)
    {
        if (bindingResult.hasErrors ())
        {
            logger.warn (bindingResult.getAllErrors ());
            return createBadRequestResult (null);
        }
        else
        {
            ActionResult ar = ActionResult.createActionResult ().build ();

            LoginUser loginUser = componentContext.getLoginUser (request);
            if (null != loginUser)
            {
                bean.setUserCreatedBy (loginUser.getUserId ());
                Contract contract = convert (bean);
                try
                {
                    contract = contractDao.save (contract);
                    ar = createAddSuccessResult (contractMessage);
                }
                catch (Exception e)
                {
                    ar = createAddFailedResult (contractMessage);
                    logger.warn (e.getMessage (), e);
                }

                if (ActionResult.ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventMsg = LogEventName.createEventName ("AddContract", "LogEvent.AddContract",
                                                                           request.getLocale ());
                        componentContext.getLogService ().log (logEventMsg,
                                                               loginUser.getUid (),
                                                               logEventMsg + ": "
                                                                       + LogUtil.createLogComment (contract));
                    }
                    catch (Exception e)
                    {
                    }
                }
            }

            return AjaxUtils.getJsonObject (ar);
        }
    }

    @Override
    protected String updateModel (@Valid ContractBean bean, BindingResult bindingResult, HttpServletRequest request)
    {
        if (bindingResult.hasErrors ())
        {
            logger.warn (bindingResult.getAllErrors ());
            return createBadRequestResult (null);
        }
        else
        {
            ActionResult ar = ActionResult.createActionResult ().build ();

            LoginUser loginUser = componentContext.getLoginUser (request);
            if (null != loginUser)
            {
                bean.setUserCreatedBy (loginUser.getUserId ());
                Contract contract = convert (bean);
                Contract oldContract = contractDao.findOne (contract.getId ());
                try
                {
                    contract = contractDao.save (contract);
                    ar = createUpdateSuccessResult (contractMessage);
                }
                catch (Exception e)
                {
                    ar = createUpdateFailedResult (contractMessage);
                    logger.warn (e.getMessage (), e);
                }

                if (ActionResult.ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventMsg = LogEventName.createEventName ("UpdateContract", "LogEvent.UpdateContract",
                                                                           request.getLocale ());
                        componentContext.getLogService ()
                                        .log (logEventMsg,
                                              loginUser.getUid (),
                                              logEventMsg + ": "
                                                      + LogUtil.createLogComment (oldContract, contract));
                    }
                    catch (Exception e)
                    {
                    }
                }
            }

            return AjaxUtils.getJsonObject (ar);
        }
    }

    @Override
    protected String deleteModels (@RequestParam String ids, HttpServletRequest request)
    {
        if (null != ids && ids.trim ().length () > 0)
        {
            List <Contract> contracts = new ArrayList <Contract> ();
            Contract contract;
            if (ids.indexOf (";") != -1)
            {
                String[] idArr = ids.split (";");
                for (String typeId : idArr)
                {
                    contract = new Contract ();
                    long contractId = Long.parseLong (typeId);
                    contract.setId (contractId);
                    contracts.add (contract);
                }
            }
            else
            {
                contract = new Contract ();
                long contractId = Long.parseLong (ids);
                contract.setId (contractId);
                contracts.add (contract);
            }

            ActionResult ar = ActionResult.createActionResult ().build ();
            LoginUser loginUser = componentContext.getLoginUser (request);
            if (null != loginUser)
            {
                try
                {
                    contractDao.delete (contracts);
                    ar = createDeleteSuccessResult (contractMessage);
                }
                catch (PcServiceException e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createServerErrorMessageResult (e.getMessage ());
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createDeleteFailedResult (contractMessage);
                }

                if (ActionResult.ActionResultStatus.OK.equals (ar.getStatus ()))
                {
                    try
                    {
                        String logEventMsg = LogEventName.createEventName ("DeleteContract", "LogEvent.DeleteContract",
                                                                           request.getLocale ());
                        componentContext.getLogService ().log (logEventMsg, loginUser.getUid (),
                                                               logEventMsg + ": " + ids);
                    }
                    catch (Exception e)
                    {
                    }
                }
            }

            return AjaxUtils.getJsonObject (ar);
        }
        else
        {
            logger.warn (ActionResultStatus.BAD_REQUEST + " ids=" + ids + " when delete contract");
            return createBadRequestResult (null);
        }
    }

    public static Contract convert (ContractBean bean)
    {
        if (null != bean)
        {
            Contract contract = new Contract ();
            long contractId = 0;
            if (StringUtils.isNotBlank (bean.getId ()))
            {
                try
                {
                    contractId = Long.parseLong (bean.getId ());
                }
                catch (NumberFormatException e)
                {
                    logger.warn (e.getMessage ());
                }
            }
            contract.setId (contractId);
            contract.setName (bean.getName ());
            contract.setSource (bean.getSource ());
            contract.setTypeCode (bean.getTypeCode ());
            contract.setDealMoney (bean.getDealMoney ());
            contract.setDateCreated (DateUtils.string2Date (bean.getDateCreated (), DateUtils.DATE_PATTERN));
            contract.setUserCreatedBy (DaoContext.getUserDao ().getUniqueIdByUserId (bean.getUserCreatedBy ()));
            contract.setComment (bean.getComment ());
            return contract;
        }
        return null;
    }

    public static ContractBean convert (Contract bean)
    {
        if (null != bean)
        {
            ContractBean contract = new ContractBean ();
            contract.setId (bean.getId () + "");
            contract.setName (bean.getName ());
            contract.setSource (bean.getSource ());
            if (null != bean.getTypeCode ())
            {
                if (ContractTypeCode.ORDER_IN.equals (bean.getTypeCode ()))
                {
                    Provider provider = DaoContext.getProviderDao ().findOne (Long.parseLong (bean.getSource ()));
                    if (null != provider)
                    {
                        contract.setSourceName (provider.getShortName ());
                    }
                }
                else
                {
                    Customer customer = DaoContext.getCustomerDao ().findOne (Long.parseLong (bean.getSource ()));
                    if (null != customer)
                    {
                        contract.setSourceName (customer.getShortName ());
                    }
                }
            }
            contract.setTypeCode (bean.getTypeCode ());
            contract.setDealMoney (bean.getDealMoney ());
            contract.setDateCreated (DateUtils.date2String (bean.getDateCreated (), DateUtils.DATE_PATTERN));
            User user = DaoContext.getUserDao ().findOne (bean.getUserCreatedBy ());
            if (null != user)
            {
                contract.setUserCreatedBy (user.getUserId ());
            }
            contract.setComment (bean.getComment ());
            return contract;
        }
        return null;
    }

}
