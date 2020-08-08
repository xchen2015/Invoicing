package com.pinfly.purchasecharge.component.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinfly.purchasecharge.component.bean.ActionResult;
import com.pinfly.purchasecharge.component.bean.BaseBean;
import com.pinfly.purchasecharge.component.bean.ComponentContext;
import com.pinfly.purchasecharge.component.bean.DataGridRequestForm;
import com.pinfly.purchasecharge.component.bean.SearchRequestForm;
import com.pinfly.purchasecharge.component.utils.AjaxUtils;
import com.pinfly.purchasecharge.core.util.PurchaseChargeConstants;

@Controller
public abstract class GenericController <B extends BaseBean> implements InitializingBean
{
    @SuppressWarnings ("unused")
    private static final Logger logger = Logger.getLogger (GenericController.class);

    @Autowired
    protected ComponentContext componentContext;

    protected static final String REQUESTMAPPING_CHECK_EXIST = "/checkExist";
    protected static final String REQUESTMAPPING_GET_ALL_MODEL = "/getAllModel";
    protected static final String REQUESTMAPPING_GET_PAGED_MODEL = "/getPagedModel";
    protected static final String REQUESTMAPPING_GET_MODEL_BY_SEARCH_FORM = "/getModelBySearchForm";
    protected static final String REQUESTMAPPING_GET_MODEL_BY_ID = "/getModelById";
    protected static final String REQUESTMAPPING_GET_MODEL_BY_ID_WITH_RESPONSE = "/getModelByIdWithResponse";
    protected static final String REQUESTMAPPING_ADD_MODEL = "/addModel";
    protected static final String REQUESTMAPPING_ADD_MODEL_WITH_RESPONSE = "/addModelWithResponse";
    protected static final String REQUESTMAPPING_UPDATE_MODEL = "/updateModel";
    protected static final String REQUESTMAPPING_UPDATE_MODEL_WITH_RESPONSE = "/updateModelWithResponse";
    protected static final String REQUESTMAPPING_DELETE_MODEL = "/deleteModel";
    protected static final String REQUESTMAPPING_DELETE_MODELS = "/deleteModels";

    @RequestMapping (value = REQUESTMAPPING_CHECK_EXIST, method = RequestMethod.POST)
    protected @ResponseBody
    String checkExist (@RequestParam String id, @RequestParam String value, HttpServletRequest request)
    {
        if (StringUtils.isBlank (id))
        {
            // new
        }
        else
        {
            // edit
        }

        return "true";
    }

    @RequestMapping (value = REQUESTMAPPING_GET_ALL_MODEL, method = RequestMethod.POST)
    protected @ResponseBody
    String getAllModel (HttpServletRequest request)
    {
        return "";
    }

    @RequestMapping (value = REQUESTMAPPING_GET_PAGED_MODEL, method = RequestMethod.POST)
    protected @ResponseBody
    String getPagedModel (DataGridRequestForm dataGridRequestForm, HttpServletRequest request)
    {
        return "";
    }

    @RequestMapping (value = REQUESTMAPPING_GET_MODEL_BY_SEARCH_FORM, method = RequestMethod.POST)
    protected @ResponseBody
    String getModelBySearchForm (DataGridRequestForm dataGridRequestForm, SearchRequestForm searchRequestForm,
                                 HttpServletRequest request)
    {
        return "";
    }

    @RequestMapping (value = REQUESTMAPPING_GET_MODEL_BY_ID, method = RequestMethod.POST)
    protected @ResponseBody
    String getModelById (@RequestParam String id, HttpServletRequest request)
    {
        return "";
    }

    @RequestMapping (value = REQUESTMAPPING_GET_MODEL_BY_ID_WITH_RESPONSE, method = RequestMethod.POST)
    protected void getModelByIdWithResponse (@RequestParam String id, HttpServletResponse response) throws Exception
    {
        throw new Exception ("Not implemented");
    }

    @RequestMapping (value = REQUESTMAPPING_ADD_MODEL, method = RequestMethod.POST)
    protected @ResponseBody
    String addModel (B bean, BindingResult bindingResult, HttpServletRequest request)
    {
        return AjaxUtils.getJsonObject (ActionResult.createActionResult ().build ());
    }

    @RequestMapping (value = REQUESTMAPPING_ADD_MODEL_WITH_RESPONSE, method = RequestMethod.POST)
    protected void addModelWithResponse (B bean, BindingResult bindingResult, HttpServletRequest request,
                                         HttpServletResponse response) throws Exception
    {
        throw new Exception ("Not implemented");
    }

    @RequestMapping (value = REQUESTMAPPING_UPDATE_MODEL, method = RequestMethod.POST)
    protected @ResponseBody
    String updateModel (B bean, BindingResult bindingResult, HttpServletRequest request)
    {
        return AjaxUtils.getJsonObject (ActionResult.createActionResult ().build ());
    }

    @RequestMapping (value = REQUESTMAPPING_UPDATE_MODEL_WITH_RESPONSE, method = RequestMethod.POST)
    protected void updateModelWithResponse (B bean, BindingResult bindingResult, HttpServletRequest request,
                                            HttpServletResponse response) throws Exception
    {
        throw new Exception ("Not implemented");
    }

    @RequestMapping (value = REQUESTMAPPING_DELETE_MODEL, method = RequestMethod.POST)
    protected @ResponseBody
    String deleteModel (@RequestParam String id, HttpServletRequest request)
    {
        return AjaxUtils.getJsonObject (ActionResult.createActionResult ().build ());
    }

    @RequestMapping (value = REQUESTMAPPING_DELETE_MODELS, method = RequestMethod.POST)
    protected @ResponseBody
    String deleteModels (@RequestParam String ids, HttpServletRequest request)
    {
        return AjaxUtils.getJsonObject (ActionResult.createActionResult ().build ());
    }

    protected String createBadRequestResult (String badRequestString)
    {
        String badRequestMessage = componentContext.getMessage (PurchaseChargeConstants.GENERIC_MSG_REQUEST_ARGS_ERROR);
        if (StringUtils.isNotBlank (badRequestString))
        {
            badRequestMessage = badRequestString;
        }
        ActionResult ar = ActionResult.badRequest ().withMessage (badRequestMessage).build ();
        return AjaxUtils.getJsonObject (ar);
    }

    protected ActionResult createAddSuccessResult (String messageKey)
    {
        String okMessage = componentContext.getMessage (PurchaseChargeConstants.GENERIC_MSG_ADD_SUCCESS);
        if (StringUtils.isNotBlank (messageKey))
        {
            String objectMessage = componentContext.getMessage (messageKey);
            okMessage = componentContext.getMessage (PurchaseChargeConstants.GENERIC_MSG_ADD_SUCCESS2, new String[]
            { objectMessage });
        }
        return ActionResult.ok ().withMessage (okMessage).build ();
    }

    protected String createAddSuccessResultString (String messageKey)
    {
        return AjaxUtils.getJsonObject (createAddSuccessResult (messageKey));
    }

    protected ActionResult createAddFailedResult (String messageKey)
    {
        String okMessage = componentContext.getMessage (PurchaseChargeConstants.GENERIC_MSG_ADD_FAIL);
        if (StringUtils.isNotBlank (messageKey))
        {
            String objectMessage = componentContext.getMessage (messageKey);
            okMessage = componentContext.getMessage (PurchaseChargeConstants.GENERIC_MSG_ADD_FAIL2, new String[]
            { objectMessage });
        }
        return ActionResult.serverError ().withMessage (okMessage).build ();
    }

    protected String createAddFailedResultString (String messageKey)
    {
        return AjaxUtils.getJsonObject (createAddFailedResult (messageKey));
    }

    protected ActionResult createUpdateSuccessResult (String messageKey)
    {
        String okMessage = componentContext.getMessage (PurchaseChargeConstants.GENERIC_MSG_UPDATE_SUCCESS);
        if (StringUtils.isNotBlank (messageKey))
        {
            String objectMessage = componentContext.getMessage (messageKey);
            okMessage = componentContext.getMessage (PurchaseChargeConstants.GENERIC_MSG_UPDATE_SUCCESS2, new String[]
            { objectMessage });
        }
        return ActionResult.ok ().withMessage (okMessage).build ();
    }

    protected String createUpdateSuccessResultString (String messageKey)
    {
        return AjaxUtils.getJsonObject (createUpdateSuccessResult (messageKey));
    }

    protected ActionResult createUpdateFailedResult (String messageKey)
    {
        String okMessage = componentContext.getMessage (PurchaseChargeConstants.GENERIC_MSG_UPDATE_FAIL);
        if (StringUtils.isNotBlank (messageKey))
        {
            String objectMessage = componentContext.getMessage (messageKey);
            okMessage = componentContext.getMessage (PurchaseChargeConstants.GENERIC_MSG_UPDATE_FAIL2, new String[]
            { objectMessage });
        }
        return ActionResult.serverError ().withMessage (okMessage).build ();
    }

    protected String createUpdateFailedResultString (String messageKey)
    {
        return AjaxUtils.getJsonObject (createUpdateFailedResult (messageKey));
    }

    protected ActionResult createDeleteSuccessResult (String messageKey)
    {
        String okMessage = componentContext.getMessage (PurchaseChargeConstants.GENERIC_MSG_DELETE_SUCCESS);
        if (StringUtils.isNotBlank (messageKey))
        {
            String objectMessage = componentContext.getMessage (messageKey);
            okMessage = componentContext.getMessage (PurchaseChargeConstants.GENERIC_MSG_DELETE_SUCCESS2, new String[]
            { objectMessage });
        }
        return ActionResult.ok ().withMessage (okMessage).build ();
    }

    protected String createDeleteSuccessResultString (String messageKey)
    {
        return AjaxUtils.getJsonObject (createDeleteSuccessResult (messageKey));
    }

    protected ActionResult createDeleteFailedResult (String messageKey)
    {
        String okMessage = componentContext.getMessage (PurchaseChargeConstants.GENERIC_MSG_DELETE_FAIL);
        if (StringUtils.isNotBlank (messageKey))
        {
            String objectMessage = componentContext.getMessage (messageKey);
            okMessage = componentContext.getMessage (PurchaseChargeConstants.GENERIC_MSG_DELETE_FAIL2, new String[]
            { objectMessage });
        }
        return ActionResult.serverError ().withMessage (okMessage).build ();
    }

    protected String createDeleteFailedResultString (String messageKey)
    {
        return AjaxUtils.getJsonObject (createDeleteFailedResult (messageKey));
    }

    protected ActionResult createServerErrorMessageResult (String message)
    {
        return createServerErrorMessageResult (message, false);
    }

    protected ActionResult createServerErrorMessageResult (String message, boolean ifMsgCode)
    {
        if (ifMsgCode)
        {
            message = componentContext.getMessage (message);
        }
        return ActionResult.serverError ().withMessage (message).build ();
    }

    protected String createServerErrorMessage (String message, boolean ifMsgCode)
    {
        return AjaxUtils.getJsonObject (createServerErrorMessageResult (message, ifMsgCode));
    }

    protected ActionResult createServerOkMessageResult (String message, boolean ifMsgCode)
    {
        if (ifMsgCode)
        {
            message = componentContext.getMessage (message);
        }
        return ActionResult.ok ().withMessage (message).build ();
    }

    protected ActionResult createServerOkMessageResult (String message)
    {
        return createServerOkMessageResult (message, false);
    }

    protected String createServerOkMessage (String message, boolean ifMsgCode)
    {
        return AjaxUtils.getJsonObject (createServerOkMessageResult (message, ifMsgCode));
    }

    @RequestMapping (value = "/getView")
    protected String getView (HttpServletRequest request)
    {
        return getViewName (request);
    }

    @RequestMapping (value = "/getJs")
    protected String getJs ()
    {
        return getJsName ();
    }

    @RequestMapping (value = "/getCss")
    protected String getCss ()
    {
        return getCssName ();
    }

    protected abstract String getViewName (HttpServletRequest request);

    protected String getJsName ()
    {
        throw new RuntimeException ("Not implemented");
    }

    protected String getCssName ()
    {
        throw new RuntimeException ("Not implemented");
    }

    @Override
    public void afterPropertiesSet () throws Exception
    {
    }

}
