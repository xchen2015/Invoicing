package com.cxx.purchasecharge.dal;

import java.io.IOException;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.jdbc.conf.JDBCConfigurationImpl;
import org.apache.openjpa.jdbc.schema.SchemaTool;
import org.apache.openjpa.lib.util.Options;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = "classpath:META-INF/spring/purchase-charge-dao2.xml")
public class GenerateTableTest extends GenericTest
{
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Test
    public void generateTables ()
    {
        EntityManager em = entityManagerFactory.createEntityManager ();
        System.out.println (em);
        em.close ();
    }

    @Test
    public void deleteTables () throws IOException, SQLException
    {
        JDBCConfiguration conf = new JDBCConfigurationImpl ();
        conf.setConnectionURL ("jdbc:mysql://localhost:3306/test");
        conf.setConnectionDriverName ("com.mysql.jdbc.Driver");
        conf.setConnectionUserName ("root");
        conf.setConnectionPassword ("root");
        // String[] args = {MappingTool.ACTION_DROP};
        // Options opts = new Options ();
        // MappingTool.run (conf, args, opts);
        // MappingTool mappingTool = new MappingTool (conf,
        // MappingTool.ACTION_DROP, false);
        // mappingTool.run (CustomerDao.class);

        // SchemaTool schemaTool = new SchemaTool (conf);
        // Table table = new Table ();
        // schemaTool.dropTable (table);

        String[] args =
        { "-a dropDB -f dropTable.sql" };
        SchemaTool.run (conf, args, new Options ());
    }

}
