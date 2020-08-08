package com.pinfly.purchasecharge.component.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinfly.purchasecharge.component.bean.ActionResult;
import com.pinfly.purchasecharge.component.bean.BaseBean;
import com.pinfly.purchasecharge.component.bean.DataBackupFile;
import com.pinfly.purchasecharge.component.bean.auditlog.LogEventName;
import com.pinfly.purchasecharge.component.utils.AjaxUtils;
import com.pinfly.purchasecharge.component.utils.ComponentMessage;
import com.pinfly.purchasecharge.component.utils.FileLoadUtils;
import com.pinfly.purchasecharge.core.model.LoginUser;
import com.pinfly.purchasecharge.core.util.DateUtils;
import com.pinfly.purchasecharge.core.util.PurchaseChargeConstants;
import com.pinfly.purchasecharge.core.util.PurchaseChargeUtils;
import com.pinfly.purchasecharge.dal.BackupAndRestore;

@Controller
@RequestMapping ("/dataBackupAndRestore")
public class BackupAndRestoreManager extends GenericController <BaseBean>
{
    private static final Logger LOGGER = Logger.getLogger (BackupAndRestoreManager.class);
    private String dataBackupMessage = ComponentMessage.createMessage ("DATA_BACKUP", "DATA_BACKUP")
                                                       .getI18nMessageCode ();

    private String viewName = "backupAndRestoreManagement";
    @Autowired
    private BackupAndRestore dataBackupAndLoad;

    public String getViewName (HttpServletRequest request)
    {
        return viewName;
    }
    
    @RequestMapping (value = "/getResetSystemViewName")
    protected String getResetSystemViewName ()
    {
        return "resetSystem";
    }

    @Override
    protected String getAllModel (HttpServletRequest request)
    {
        List <DataBackupFile> dataBackupFiles = getAllBackupFile ();
        JSONArray jsonArray = JSONArray.fromObject (dataBackupFiles);
        return jsonArray.toString ();
    }

    @Override
    protected String deleteModels (@RequestParam String ids, HttpServletRequest request)
    {
        ActionResult ar = ActionResult.createActionResult ().build ();
        LoginUser loginUser = componentContext.getLoginUser (request);
        if (null != loginUser && StringUtils.isNotBlank (ids))
        {
            try
            {
                if (ids.indexOf (";") != -1)
                {
                    String[] idArr = ids.split (";");
                    for (String id : idArr)
                    {
                        File file = null;
                        List <DataBackupFile> dataBackupFiles = getAllBackupFile ();
                        for (DataBackupFile dataFile : dataBackupFiles)
                        {
                            if (dataFile.getId ().equals (id))
                            {
                                file = new File (PurchaseChargeUtils.getDataBackupStoreDirectory () + File.separator
                                                 + dataFile.getFileName ());
                                break;
                            }
                        }
                        
                        if (null != file)
                        {
                            file.delete ();
                        }
                    }
                }
                ar = createDeleteSuccessResult (dataBackupMessage);
            }
            catch (Exception e)
            {
                LOGGER.warn (e.getMessage (), e);
                ar = createDeleteFailedResult (dataBackupMessage);
            }

            if (ActionResult.ActionResultStatus.OK.equals (ar.getStatus ()))
            {
                try
                {
                    String logEventName = LogEventName.createEventName ("RemoveBackupData",
                                                                        "LogEvent.RemoveBackupData",
                                                                        request.getLocale ());
                    componentContext.getLogService ().log (logEventName, loginUser.getUid (), logEventName + ": " + ids);
                }
                catch (Exception e)
                {
                }
            }
        }
        return AjaxUtils.getJsonObject (ar);
    }

    @Override
    protected String addModel (BaseBean bean, BindingResult bindingResult, HttpServletRequest request)
    {
        ActionResult ar = ActionResult.createActionResult ().build ();
        LoginUser loginUser = componentContext.getLoginUser (request);
        if (null != loginUser)
        {
            File file = null;
            try
            {
                file = dataBackupAndLoad.backup (PurchaseChargeUtils.getDataBackupStoreDirectory ());
                ar = createServerOkMessageResult ("pc.data.backup.success", true);
            }
            catch (Exception e)
            {
                LOGGER.warn ("Backup data failed", e);
                ar = createServerErrorMessageResult ("pc.data.backup.fail", true);
            }

            if (ActionResult.ActionResultStatus.OK.equals (ar.getStatus ()))
            {
                try
                {
                    String logEventName = LogEventName.createEventName ("BackupSystemData",
                                                                        "LogEvent.BackupSystemData",
                                                                        request.getLocale ());
                    componentContext.getLogService ().log (logEventName, loginUser.getUid (),
                                                           logEventName + ": " + file.getName ());
                }
                catch (Exception e)
                {
                }
            }
        }
        return AjaxUtils.getJsonObject (ar);
    }

    @Override
    protected String updateModel (BaseBean bean, BindingResult bindingResult, HttpServletRequest request)
    {
        LoginUser loginUser = componentContext.getLoginUser (request);
        ActionResult ar = ActionResult.createActionResult ().build ();
        if (null != loginUser && StringUtils.isNotBlank (bean.getId ()))
        {
            try
            {
                File file = null;
                List <DataBackupFile> dataBackupFiles = getAllBackupFile ();
                for (DataBackupFile dataFile : dataBackupFiles)
                {
                    if (dataFile.getId ().equals (bean.getId ()))
                    {
                        file = new File (PurchaseChargeUtils.getDataBackupStoreDirectory () + File.separator
                                         + dataFile.getFileName ());
                        break;
                    }
                }

                if (null != file)
                {
                    dataBackupAndLoad.load (file);
                    componentContext.getSecurityResourceMap (true);
                }
                ar = createServerOkMessageResult ("pc.data.restore.success", true);
            }
            catch (Exception e)
            {
                LOGGER.warn ("Load data fail", e);
                ar = createServerErrorMessageResult ("pc.data.restore.fail", true);
            }

            if (ActionResult.ActionResultStatus.OK.equals (ar.getStatus ()))
            {
                try
                {
                    String logEventName = LogEventName.createEventName ("RecoverSystemData",
                                                                        "LogEvent.RecoverSystemData",
                                                                        request.getLocale ());
                    componentContext.getLogService ().log (logEventName, loginUser.getUid (),
                                                           logEventName + ": " + bean.getId ());
                }
                catch (Exception e)
                {
                }
            }
        }
        return AjaxUtils.getJsonObject (ar);
    }

    @RequestMapping (value = "/backup", method = RequestMethod.GET)
    public @ResponseBody
    String backup (HttpServletRequest request, HttpServletResponse response)
    {
        LoginUser loginUser = componentContext.getLoginUser (request);
        if (null != loginUser)
        {
            String dir = FileLoadUtils.getUploadTempDirectory (request);
            File tmpFile = dataBackupAndLoad.backup (dir);
            FileLoadUtils.download (request, response, tmpFile, "");
            // delete file
            tmpFile.delete ();

            try
            {
                String logEventName = LogEventName.createEventName ("BackupSystemData", "LogEvent.BackupSystemData",
                                                                    request.getLocale ());
                componentContext.getLogService ().log (logEventName, loginUser.getUid (), logEventName);
            }
            catch (Exception e)
            {
            }
        }
        return "";
    }

    @RequestMapping (value = "/restore", method = RequestMethod.POST)
    public @ResponseBody
    String restore (HttpServletRequest request, HttpServletResponse response)
    {
        LoginUser loginUser = componentContext.getLoginUser (request);
        ActionResult ar = ActionResult.createActionResult ().build ();
        if (null != loginUser)
        {
            try
            {
                String dir = FileLoadUtils.getUploadTempDirectory (request);
                File tmpFile = FileLoadUtils.upload (request, response, dir);
                dataBackupAndLoad.load (tmpFile);
                ar = createServerOkMessageResult ("");
                // delete file
                tmpFile.delete ();
            }
            catch (Exception e)
            {
                LOGGER.warn ("Load data fail", e);
                ar = createServerErrorMessageResult ("");
            }

            try
            {
                String logEventName = LogEventName.createEventName ("RecoverSystemData", "LogEvent.RecoverSystemData",
                                                                    request.getLocale ());
                componentContext.getLogService ().log (logEventName, loginUser.getUid (), logEventName);
            }
            catch (Exception e)
            {
            }
        }
        return AjaxUtils.getJsonObject (ar);
    }
    
    @RequestMapping (value = "/resetSystem", method = RequestMethod.POST)
    public @ResponseBody
    String resetSystem (HttpServletRequest request, HttpServletResponse response)
    {
        return null;
    }

    @SuppressWarnings ("unchecked")
    private List <DataBackupFile> getAllBackupFile ()
    {
        List <DataBackupFile> dataBackupFiles = new ArrayList <DataBackupFile> ();
        String dataBackupDir = PurchaseChargeUtils.getDataBackupStoreDirectory ();
        if (StringUtils.isNotBlank (dataBackupDir))
        {
            Collection <File> files = FileUtils.listFiles (new File (dataBackupDir), new String[]
            { PurchaseChargeConstants.BACKUP_FILE_EXTENSION.substring (1) }, false);
            if (CollectionUtils.isNotEmpty (files))
            {
                for (File file : files)
                {
                    DataBackupFile backupFile = new DataBackupFile ();
                    String fileNameWithExt = file.getName ();
                    String fileName = fileNameWithExt.substring (0, fileNameWithExt.lastIndexOf ("."));
                    backupFile.setId (fileName);
                    backupFile.setFileName (fileNameWithExt);
                    backupFile.setFileSize (file.length ());
                    backupFile.setCreatedDate (getFileCreationDate (fileName));
                    dataBackupFiles.add (backupFile);
                }
            }
        }
        Collections.sort (dataBackupFiles);
        return dataBackupFiles;
    }

    private String getFileCreationDate (String fileName)
    {
        if (StringUtils.isNotBlank (fileName))
        {
            Date date = DateUtils.string2Date (fileName, PurchaseChargeConstants.FILE_NAME_DATE_TIME);
            if (null != date)
            {
                return DateUtils.date2String (date, DateUtils.DATE_TIME_PATTERN);
            }
        }
        return "";
    }

}
