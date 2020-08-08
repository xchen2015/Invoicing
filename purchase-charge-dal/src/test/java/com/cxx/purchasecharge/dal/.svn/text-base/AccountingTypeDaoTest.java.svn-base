package com.cxx.purchasecharge.dal;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pinfly.purchasecharge.core.model.AccountingModeCode;
import com.pinfly.purchasecharge.core.model.persistence.AccountingType;
import com.pinfly.purchasecharge.dal.AccountingTypeDao;

@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = "classpath:META-INF/spring/purchase-charge-dao.xml")
public class AccountingTypeDaoTest extends GenericTest
{
    @Autowired
    AccountingTypeDao accountTypeDao;

    @Test
    public void testSave ()
    {
        String name = "boc02";
        AccountingType accountType = new AccountingType ();
        accountType.setName (name);
        accountType.setAccountingMode (AccountingModeCode.IN_COME);
        accountTypeDao.save (accountType);

        Assert.assertNotNull (accountTypeDao.findByName (name));
    }

    @Test
    public void testDelete ()
    {
        long id = 301L;
        accountTypeDao.delete (id);

        Assert.assertNull (accountTypeDao.findOne (id));
    }

    @Test
    public void testFindByMode ()
    {
        AccountingModeCode mode = AccountingModeCode.IN_COME;
        System.out.println (accountTypeDao.findByMode (mode));
    }

    @Test
    public void testGetAll ()
    {
        System.out.println (accountTypeDao.findAll ());
    }

}
