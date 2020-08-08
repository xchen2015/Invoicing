package com.pinfly.purchasecharge.component.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.pinfly.common.config.BaseConfigUtil;
import com.pinfly.common.util.StringUtil;

/**
 *
 */
@SuppressWarnings ("all")
public class FileLoadUtils
{
    private static final Logger LOGGER = Logger.getLogger (FileLoadUtils.class);

    public static File upload (HttpServletRequest request, HttpServletResponse response, String dir) throws Exception
    {
        request.setCharacterEncoding ("UTF-8");
        response.setContentType ("text/html;charset=UTF-8");
        response.setCharacterEncoding ("UTF-8");
        File filesDir = new File (dir);
        if (!filesDir.exists ())
        {
            filesDir.mkdir ();
        }

        DiskFileItemFactory factory = new DiskFileItemFactory ();
        ServletFileUpload upload = new ServletFileUpload (factory);
        upload.setHeaderEncoding ("UTF-8");

        File uploadAfterFile = null;
        List <FileItem> fileItems = (List <FileItem>) upload.parseRequest (request);
        String fileName = null;
        String realName = null;
        for (int i = 0; i < fileItems.size (); i++)
        {
            FileItem item = (FileItem) fileItems.get (i);
            if (!item.isFormField ())
            {
                realName = item.getName ();
                fileName = UUID.randomUUID ().toString () + realName.substring (realName.lastIndexOf ('.'));
                // uploadAfterFile = File.createTempFile (fileName, fileType,
                // filesDir);
                // uploadAfterFile.deleteOnExit ();
                uploadAfterFile = new File (filesDir, fileName);
                item.write (uploadAfterFile);
            }
        }

        return uploadAfterFile;
    }

    /**
     * 将服务器端生成的文件提供给客户端下载
     * 
     * @param request
     * @param response
     * @param tempFile
     */
    public static void download (HttpServletRequest request, HttpServletResponse response, File tempFile,
                                 String contentType)
    {
        if (tempFile == null)
        {
            return;
        }
        String filenamedisplay = tempFile.getName ();
        try
        {
            filenamedisplay = URLEncoder.encode (filenamedisplay, "UTF-8");
        }
        catch (UnsupportedEncodingException e1)
        {
            LOGGER.warn (e1.getMessage ());
        }
        response.reset ();
        response.setContentType (contentType);
        response.addHeader ("Content-Disposition", "attachment;filename=" + filenamedisplay);
        OutputStream output = null;
        FileInputStream fis = null;
        try
        {
            output = response.getOutputStream ();
            fis = new FileInputStream (tempFile);
            byte[] b = new byte[1024];
            int i = 0;
            while ((i = fis.read (b)) > 0)
            {
                output.write (b, 0, i);
            }
            output.flush ();
        }
        catch (Exception e)
        {
            LOGGER.warn (e.getMessage ());
        }
        finally
        {
            try
            {
                if (fis != null)
                {
                    fis.close ();
                }
                if (output != null)
                {
                    output.close ();
                }
            }
            catch (IOException e)
            {
                LOGGER.warn (e.getMessage ());
            }
        }
    }

    public static String getUploadTempDirectory (HttpServletRequest request)
    {
        return request.getSession ().getServletContext ().getRealPath ("/") + "temp";
    }
}
