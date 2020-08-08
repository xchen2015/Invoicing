package com.pinfly.purchasecharge.component.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pinfly.purchasecharge.component.bean.ActionResult;
import com.pinfly.purchasecharge.component.bean.ActionResult.ActionResultStatus;
import com.pinfly.purchasecharge.component.bean.RegionBean;
import com.pinfly.purchasecharge.component.utils.AjaxUtils;
import com.pinfly.purchasecharge.component.utils.ComponentMessage;
import com.pinfly.purchasecharge.core.model.persistence.Region;
import com.pinfly.purchasecharge.dal.RegionDao;

@Controller
@RequestMapping ("/region")
public class RegionManager extends GenericController <RegionBean>
{
    private static final Logger logger = Logger.getLogger (RegionManager.class);

    private String regionMessage = ComponentMessage.createMessage ("REGION", "REGION").getI18nMessageCode ();
    private RegionDao regionDao;

    @Override
    protected String getViewName (HttpServletRequest request)
    {
        return "regionManagement";
    }

    @Override
    protected String getJsName ()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String getCssName ()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String checkExist (@RequestParam String regionId, @RequestParam String name, HttpServletRequest request)
    {
        if (StringUtils.isBlank (regionId))
        {
            // new
            Region obj = regionDao.findByName (name);
            if (null != obj)
            {
                return "false";
            }
        }
        else
        {
            // edit
            long id = Long.parseLong (regionId);
            Region region = regionDao.findByName (name);
            if (null != region && region.getId () != id)
            {
                return "false";
            }
        }

        return "true";
    }

    @Override
    public String getAllModel (HttpServletRequest request)
    {
        List <Region> regions = (List <Region>) regionDao.findAll ();
        List <RegionBean> regionBeans = new ArrayList <RegionBean> ();
        if (null != regions)
        {
            for (Region region : regions)
            {
                if (null == region.getParent ())
                {
                    RegionBean regionBean = region2RegionBean (region);
                    regionBeans.add (regionBean);
                }
            }
        }
        JSONArray jsonObject = JSONArray.fromObject (regionBeans);
        return jsonObject.toString ();
    }

    @Override
    public void addModelWithResponse (RegionBean bean, BindingResult bindingResult, HttpServletRequest request,
                                      HttpServletResponse response) throws Exception
    {
        response.setContentType ("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter ();
        if (bindingResult.hasErrors ())
        {
            logger.warn (bindingResult.getAllErrors ());
            writer.print (createBadRequestResult (null));
        }
        else
        {
            Region region = new Region (bean.getText ());
            String id = bean.getId ();
            if (null != id && !"".equals (id))
            {
                region.setId (Long.parseLong (id));
            }
            String parentId = bean.getParentId ();
            if (!StringUtils.isBlank (parentId))
            {
                Region parentRegion = new Region ();
                parentRegion.setId (Long.parseLong (parentId));
                region.setParent (parentRegion);
            }

            try
            {
                Region dbRegion = regionDao.save (region);
                JSONObject regionJson = JSONObject.fromObject (dbRegion);
                writer.print (regionJson.toString ());
            }
            catch (Exception e)
            {
                logger.warn (e.getMessage (), e);
                writer.print (createAddFailedResultString (regionMessage));
            }
        }
    }

    @Override
    public void updateModelWithResponse (RegionBean bean, BindingResult bindingResult, HttpServletRequest request,
                                         HttpServletResponse response) throws Exception
    {
        response.setContentType ("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter ();
        if (bindingResult.hasErrors ())
        {
            logger.warn (bindingResult.getAllErrors ());
            writer.print (createBadRequestResult (null));
        }
        else
        {
            Region region = new Region (bean.getText ());
            String id = bean.getId ();
            if (null != id && !"".equals (id))
            {
                region.setId (Long.parseLong (id));
            }
            String parentId = bean.getParentId ();
            if (!StringUtils.isBlank (parentId))
            {
                Region parentRegion = new Region ();
                parentRegion.setId (Long.parseLong (parentId));
                region.setParent (parentRegion);
            }

            try
            {
                Region dbRegion = regionDao.save (region);
                JSONObject regionJson = JSONObject.fromObject (dbRegion);
                writer.print (regionJson.toString ());
            }
            catch (Exception e)
            {
                logger.warn (e.getMessage (), e);
                writer.print (createUpdateFailedResultString (regionMessage));
            }
        }
    }

    @Override
    public String deleteModels (@RequestParam String ids, HttpServletRequest request)
    {
        if (null != ids && ids.trim ().length () > 0)
        {
            ActionResult ar = ActionResult.createActionResult ().build ();
            if (ids.indexOf (";") != -1)
            {
                String[] idArr = ids.split (";");
                List <Region> types = new ArrayList <Region> ();
                Region type;
                for (String typeId : idArr)
                {
                    type = new Region ();
                    type.setId (Long.parseLong (typeId));
                    types.add (type);
                }
                try
                {
                    regionDao.delete (types);
                    ar = createDeleteSuccessResult (regionMessage);
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createDeleteFailedResult (regionMessage);
                }
            }
            else
            {
                try
                {
                    regionDao.delete (Long.parseLong (ids));
                    deleteChildrenRegion (Long.parseLong (ids));
                    ar = createDeleteSuccessResult (regionMessage);
                }
                catch (Exception e)
                {
                    logger.warn (e.getMessage (), e);
                    ar = createDeleteFailedResult (regionMessage);
                }
            }
            return AjaxUtils.getJsonObject (ar);
        }
        else
        {
            logger.warn (ActionResultStatus.BAD_REQUEST);
            return createBadRequestResult (null);
        }
    }

    private RegionBean region2RegionBean (Region region)
    {
        RegionBean regionBean = new RegionBean ();
        if (null != region.getParent ())
        {
            regionBean.setId (region.getId () + "");
            regionBean.setText (region.getName ());
            List <RegionBean> children = new ArrayList <RegionBean> ();
            List <Region> list = regionDao.findByParent (region.getId ());
            for (Region type : list)
            {
                children.add (region2RegionBean (type));
            }
            regionBean.setChildren (children);
        }
        else
        {
            regionBean.setId (region.getId () + "");
            regionBean.setText (region.getName ());
            List <RegionBean> children = new ArrayList <RegionBean> ();
            List <Region> list = regionDao.findByParent (region.getId ());
            for (Region type : list)
            {
                children.add (region2RegionBean (type));
            }
            regionBean.setChildren (children);
        }
        return regionBean;
    }

    private void deleteChildrenRegion (long id)
    {
        List <Region> childRegions = regionDao.findByParent (id);
        regionDao.delete (childRegions);
        for (Region type : childRegions)
        {
            deleteChildrenRegion (type.getId ());
        }
    }

    @Override
    public void afterPropertiesSet () throws Exception
    {
        // TODO Auto-generated method stub

    }

}
