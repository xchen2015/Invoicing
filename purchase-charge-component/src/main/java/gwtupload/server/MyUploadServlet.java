/*
 * Copyright 2010 Manuel Carrasco Moñino. (manolo at apache/org) 
 * http://code.google.com/p/gwtupload
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package gwtupload.server;

import gwtupload.server.exceptions.UploadActionException;
import gwtupload.shared.UConsts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.pinfly.purchasecharge.core.model.persistence.goods.Goods;
import com.pinfly.purchasecharge.core.model.persistence.goods.GoodsPicture;
import com.pinfly.purchasecharge.core.util.PurchaseChargeUtils;
import com.pinfly.purchasecharge.dal.goods.GoodsPictureDao;

/**
 * This is an example of how to use UploadAction class.
 * 
 * This servlet saves all received files in a temporary folder, and deletes them
 * when the user sends a remove request.
 * 
 */
public class MyUploadServlet extends UploadAction
{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger (MyUploadServlet.class);

    /**
     * Maintain a list with received files and their content types.
     */
    private Hashtable <String, File> receivedFiles = new Hashtable <String, File> ();
    private Hashtable <String, String> receivedContentTypes = new Hashtable <String, String> ();

    public static final String GOODS_ID = "gid";

    private GoodsPictureDao goodsPictureDao;

    @Override
    public void init (ServletConfig config) throws ServletException
    {
        super.init (config);
        goodsPictureDao = WebApplicationContextUtils.getWebApplicationContext (config.getServletContext ())
                                                    .getBean (GoodsPictureDao.class);
    }

    /**
     * Override executeAction to save the received files in a custom place and
     * delete this items from session.
     */
    @Override
    public String executeAction (HttpServletRequest request, List <FileItem> sessionFiles) throws UploadActionException
    {
        String response = "";

        String goodsId = (String) request.getSession (false).getAttribute (GOODS_ID);
        if (StringUtils.isNotBlank (goodsId))
        {
            String randomFileName = null;
            String realName = null;
            File uploadAfterFile = null;
            for (FileItem item : sessionFiles)
            {
                if (false == item.isFormField ())
                {
                    try
                    {
                        String dir = PurchaseChargeUtils.getGoodsImageStoreDirectory ();
                        File filesDir = new File (dir);
                        if (!filesDir.exists ())
                        {
                            filesDir.mkdir ();
                        }

                        realName = item.getName ();
                        // randomFileName = UUID.randomUUID ().toString () +
                        // realName.substring (realName.lastIndexOf ('.'));
                        randomFileName = item.getFieldName () + realName.substring (realName.lastIndexOf ('.'));
                        uploadAfterFile = new File (filesDir, randomFileName);
                        item.write (uploadAfterFile);

                        String fieldName = item.getFieldName ();
                        String contentType = item.getContentType ();
                        // Save a list with the received files
                        receivedFiles.put (fieldName, uploadAfterFile);
                        receivedContentTypes.put (fieldName, contentType);

                        if (null == goodsPictureDao.findByFileNameAndContentType (fieldName, contentType))
                        {
                            long gid = Long.parseLong (goodsId);
                            Goods goods = new Goods ();
                            goods.setId (gid);
                            GoodsPicture gp = new GoodsPicture (fieldName, contentType);
                            gp.setGoods (goods);
                            goodsPictureDao.save (gp);
                        }
                    }
                    catch (Exception e)
                    {
                        logger.warn (e.getMessage (), e);
                        throw new UploadActionException (e);
                    }
                }
            }

            // Remove files from session because we have a copy of them
            removeSessionFileItems (request);
        }

        // Send information of the received files to the client.
        return "<response>\n" + response + "</response>\n";
    }

    /**
     * Get the content of an uploaded file.
     */
    @Override
    public void getUploadedFile (HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String fieldName = request.getParameter (UConsts.PARAM_SHOW);
        String contentType = request.getParameter (UConsts.PARAM_CTYPE);
        if (StringUtils.isBlank (contentType))
        {
            contentType = receivedContentTypes.get (fieldName);
        }
        File f = receivedFiles.get (fieldName);

        if (f == null || StringUtils.isBlank (contentType))
        {
            if (null != goodsPictureDao)
            {
                try
                {
                    GoodsPicture pic = goodsPictureDao.findByFileNameAndContentType (fieldName, contentType);
                    if (null != pic)
                    {
                        f = new File (PurchaseChargeUtils.getGoodsImageStoreDirectory (),
                                      pic.getFileName () + getFileExtension (pic.getContentType ()));
                    }
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                }
            }
        }

        if (f != null)
        {
            response.setContentType (contentType);
            try
            {
                FileInputStream is = new FileInputStream (f);
                copyFromInputStreamToOutputStream (is, response.getOutputStream ());
            }
            catch (FileNotFoundException e)
            {
                renderXmlResponse (request, response, XML_ERROR_ITEM_NOT_FOUND);
            }
        }
        else
        {
            renderXmlResponse (request, response, XML_ERROR_ITEM_NOT_FOUND);
        }
    }

    /**
     * Remove a file when the user sends a delete request.
     */
    @Override
    public void removeItem (HttpServletRequest request, String fieldName) throws UploadActionException
    {
        File file = receivedFiles.get (fieldName);
        String contentType = receivedContentTypes.get (fieldName);
        GoodsPicture gp = null;
        if (StringUtils.isBlank (contentType))
        {
            contentType = request.getParameter (UConsts.PARAM_CTYPE);
        }
        gp = goodsPictureDao.findByFileNameAndContentType (fieldName, contentType);
        if (null != gp)
        {
            goodsPictureDao.delete (gp);
            file = new File (PurchaseChargeUtils.getGoodsImageStoreDirectory (),
                             gp.getFileName () + getFileExtension (gp.getContentType ()));
        }

        if (file != null)
        {
            file.delete ();
            logger.debug ("MyUpload-SERVLET (" + request.getSession ().getId () + ") removed file "
                          + file.getAbsolutePath ());
        }
        receivedFiles.remove (fieldName);
        receivedContentTypes.remove (fieldName);
    }

    private static String getFileExtension (String contentType)
    {
        String fileExtension = "";
        if (StringUtils.isNotBlank (contentType))
        {
            if ("image/jpeg".equalsIgnoreCase (contentType))
            {
                fileExtension = ".jpg";
            }
            else if ("image/png".equalsIgnoreCase (contentType))
            {
                fileExtension = ".png";
            }
            else if ("image/gif".equalsIgnoreCase (contentType))
            {
                fileExtension = ".gif";
            }
        }
        return fileExtension;
    }

}