package com.cxx.purchasecharge.dal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pinfly.purchasecharge.core.model.persistence.usersecurity.Authority;
import com.pinfly.purchasecharge.core.model.persistence.usersecurity.Role;
import com.pinfly.purchasecharge.core.model.persistence.usersecurity.User;
import com.pinfly.purchasecharge.dal.usersecurity.AuthorityDao;
import com.pinfly.purchasecharge.dal.usersecurity.RoleDao;
import com.pinfly.purchasecharge.dal.usersecurity.UserDao;

@SuppressWarnings ("all")
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = "classpath:META-INF/spring/purchase-charge-dao.xml")
public class UserDaoTest extends GenericTest
{
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private AuthorityDao authorityDao;
    @PersistenceContext
    private EntityManager entityManager;

    String userId = "test01";
    private String pwd = userId;
    private boolean enable = true;

    private String firstName = "firstName01";
    private String lastName = "lastName01";
    private String mobilePhone = "13456461646";
    private long qq = 4654135465L;
    private Date birthday = new Date ();
    private String academy = "湖南第一师范";
    private String birthAddress = "湖南永州东安";
    private String liveAddress = "湖南长沙岳麓";
    private String email = "chenxiangxiao01@163.com";

    private List <Role> roles;

    @Test
    public void testAddUser ()
    {
        String userId = "testUser006";
        User user = createUserData (userId);
        User user2 = userDao.save (user);
        user2 = userDao.findByUserId (userId);
        Assert.assertEquals (userId, user2.getUserId ());
        Assert.assertEquals (pwd, user2.getPwd ());
        Assert.assertEquals (firstName, user2.getFirstName ());
        Assert.assertEquals (lastName, user2.getLastName ());
        Assert.assertEquals (enable, user2.isEnabled ());
        Assert.assertEquals (birthday, user2.getBirthday ());
        Assert.assertEquals (birthAddress, user2.getBirthAddress ());
        Assert.assertEquals (mobilePhone, user2.getMobilePhone ());
        Assert.assertEquals (email, user2.getEmail ());
    }

    @Test
    public void testAddBossUser ()
    {
        User user = new User ("boss", "boss");
        user.setFirstName ("xiang");
        user.setLastName ("chen");
        user.setEnabled (true);
        System.out.println (userDao.save (user));
    }

    @Test
    public void testAddUserListAndPageableLoad ()
    {
        String userIdPrefix = "userId";
        int number = 18;
        List <User> userList = new ArrayList <User> ();
        for (int i = 0; i < number; i++)
        {
            userList.add (createUserData (userIdPrefix + i));
        }
        List <User> userList2 = (List <User>) userDao.save (userList);
        Assert.assertTrue (userList2.size () == number);

        int page = 0;
        int size = 10;
        /*
         * Pageable pageable = new PageRequest (page, size, new Sort
         * ("userId")); Page <User> userPage = userDao.findAll (pageable);
         * Assert.assertTrue (userPage.getContent ().size () == size);
         * Assert.assertTrue (userPage.getNumber () == page); Assert.assertTrue
         * (userPage.getNumberOfElements () == size); Assert.assertTrue
         * (userPage.getSize () == size); int pageRemainder = number % size;
         * Assert.assertTrue (userPage.getTotalPages () == (pageRemainder == 0 ?
         * number / size : number / size + 1)); Assert.assertTrue
         * (userPage.getTotalElements () == number);
         */
    }

    @Test
    public void testUpdateUser ()
    {
        User user = createUserData (userId);
        userDao.save (user);

        user = userDao.findByUserId (userId);
        String phoneNumber = "16464654646";
        long qq = 41654641646L;
        String email = "xchen@cxx.com";
        String firstName = "uFName01";
        String lastName = "uLName01";
        boolean available = false;
        user.setMobilePhone (phoneNumber);
        user.setEmail (email);
        user.setFirstName (firstName);
        user.setLastName (lastName);
        user.setEnabled (available);

        User user2 = userDao.save (user);
        Assert.assertEquals (firstName, user2.getFirstName ());
        Assert.assertEquals (lastName, user2.getLastName ());
        Assert.assertEquals (available, user2.isEnabled ());
        Assert.assertEquals (phoneNumber, user2.getMobilePhone ());
        Assert.assertEquals (email, user2.getEmail ());
    }

    @Test
    public void testGetAllUser ()
    {
        // userDao.save (createUserData ("user01"));
        // userDao.save (createUserData ("auser02"));
        // userDao.save (createUserData ("yuser02"));
        // List <User> userList = (List<User>)userDao.findAll ();

        Query query = entityManager.createQuery ("select u from User u");
        List <User> userList = (List <User>) query.getResultList ();

        // List <User> userList = (List <User>) userDao.findAll (new Sort
        // ("userId"));
        System.out.println (userList.size ());
        // Assert.assertTrue (userList.size () == 3);
        // Assert.assertTrue (userList.get (0).getUserId ().equals ("auser02"));
        // Assert.assertTrue (userList.get (1).getUserId ().equals ("user01"));
        // Assert.assertTrue (userList.get (2).getUserId ().equals ("yuser02"));
    }

    @Test
    public void testFindByName ()
    {
        // userDao.save (createUserData ("test01"));
        // userDao.save (createUserData ("test02"));
        // userDao.save (createUserData ("test03"));
        // userDao.save (createUserData ("test04"));

        // String name = "l";
        // String name = "";
        String name = "testUser003";
        List <User> users = userDao.findByName (name);
        // Assert.assertTrue (users.size () == 4);
    }

    @Test
    public void testFindByFuzzy ()
    {
        // userDao.save (createUserData ("test01", "test01", "test01",
        // 13027499439L, "dest01@cxx.com"));
        // userDao.save (createUserData ("test02", "dest02", "test02",
        // 13127499439L, "test02@cxx.com"));
        // userDao.save (createUserData ("test03", "test03", "test03",
        // 13227499439L, "test03@cxx.com"));
        // userDao.save (createUserData ("sfds04", "ffds04", "sfds04",
        // 13327499439L, "sfds04@cxx.com"));
        // userDao.save (createUserData ("aabb03", "aabb03", "aabb03",
        // 13427499439L, "fabb03@cxx.com"));
        // userDao.save (createUserData ("ddaa04", "ddaa04", "ddaa04",
        // 13527499439L, "tdaa04@cxx.com"));

        String searchKey = "b";
        int page = 0;
        int size = 10;
        Pageable pageable = new PageRequest (page, size, new Sort ("userId"));
        Page <User> userPage = userDao.findByFuzzy (pageable, searchKey);
        System.out.println (userPage.getNumberOfElements ());
        // Assert.assertEquals (4, userPage.getNumberOfElements ());
    }

    @Test
    public void testGetUser ()
    {

    }

    @Test
    public void testDeleteUserById ()
    {
        // User user = userDao.save (createUserData ("user01"));
        // userDao.delete (user.getId ());
        User user = userDao.findByUserId ("testUser001");
        Assert.assertNull (user);
    }

    @Test
    public void testDeleteUser ()
    {
    }

    @Test
    public void testCount ()
    {
    }

    @Test
    public void initUserRoleAuthorityResource ()
    {
        Authority authority = new Authority ("adminAuthority");
        if (null == authorityDao.findByName ("adminAuthority"))
        {
            authorityDao.save (authority);
        }

        List <Authority> authorities = new ArrayList <Authority> ();

        List <Role> roles = new ArrayList <Role> ();
        authorities.add (authorityDao.findByName ("adminAuthority"));
        Role role = new Role ("boss", authorities);
        if (null == roleDao.findByName ("boss"))
        {
            roles.add (role);
        }
        // role = new Role ("admin");
        role = new Role ("ROLE_ADMIN");
        if (null == roleDao.findByName ("ROLE_ADMIN"))
        {
            roles.add (role);
        }
        // role = new Role ("salesman");
        role = new Role ("ROLE_USER");
        if (null == roleDao.findByName ("ROLE_USER"))
        {
            roles.add (role);
        }
        role = new Role ("support");
        if (null == roleDao.findByName ("support"))
        {
            roles.add (role);
        }
        role = new Role ("viewer");
        if (null == roleDao.findByName ("viewer"))
        {
            roles.add (role);
        }
        roles = (List <Role>) roleDao.save (roles);
        org.springframework.util.Assert.isTrue (((List <Role>) roleDao.findAll ()).size () == 5);

        List <User> users = new ArrayList <User> ();
        roles = new ArrayList <Role> ();
        roles.add (roleDao.findByName ("boss"));
        User user = new User ("boss", "boss", roles);
        if (null == userDao.findByUserId ("boss"))
        {
            users.add (user);
        }
        roles = new ArrayList <Role> ();
        roles.add (roleDao.findByName ("ROLE_ADMIN"));
        roles.add (roleDao.findByName ("ROLE_USER"));
        user = new User ("admin", "admin", roles);
        if (null == userDao.findByUserId ("admin"))
        {
            users.add (user);
        }
        roles = new ArrayList <Role> ();
        roles.add (roleDao.findByName ("ROLE_USER"));
        user = new User ("salesman", "salesman", roles);
        if (null == userDao.findByUserId ("salesman"))
        {
            users.add (user);
        }
        roles = new ArrayList <Role> ();
        roles.add (roleDao.findByName ("support"));
        user = new User ("suport", "suport", roles);
        if (null == userDao.findByUserId ("suport"))
        {
            users.add (user);
        }
        roles = new ArrayList <Role> ();
        roles.add (roleDao.findByName ("viewer"));
        user = new User ("viewer", "viewer", roles);
        if (null == userDao.findByUserId ("viewer"))
        {
            users.add (user);
        }
        users = (List <User>) userDao.save (users);
        org.springframework.util.Assert.isTrue (((List <User>) userDao.findAll ()).size () == 5);
    }

    @Test
    public void testUpdateUserRole ()
    {
        List <Role> roles = new ArrayList <Role> ();
        roles.add (roleDao.findByName ("ROLE_ADMIN"));
        roles.add (roleDao.findByName ("ROLE_USER"));
        User user = new User ("admin1", "admin1", roles);
        userDao.save (user);

        System.out.println (userDao.findByUserId ("admin1").getRoles ());
    }

    @Test
    public void testFindUseAuthorityByUserId ()
    {
        System.out.println (userDao.findUserRoleByUserId ("boss"));
    }

    @Test
    public void testUpdatePassword ()
    {
        System.out.println (userDao.updatePassword ("boss", "boss"));
    }

    @Test
    public void testFindByRole ()
    {
        String role = "admin";
        Assert.assertNotNull (userDao.findRoleAuthorityByRole (role));
        System.out.println (userDao.findRoleAuthorityByRole (role));
    }

    // @After
    @Test
    public void testCleanData ()
    {
        userDao.deleteAll ();
        List <User> userList2 = (List <User>) userDao.findAll ();
        Assert.assertTrue (userList2.size () == 0);
    }

    private User createUserData (String userId)
    {
        User user = new User ();
        user.setUserId (userId);
        user.setPwd (pwd);
        user.setFirstName (firstName);
        user.setLastName (lastName);
        user.setEnabled (enable);
        user.setBirthAddress (birthAddress);
        user.setBirthday (birthday);
        user.setEmail (email);
        user.setMobilePhone (mobilePhone);
        return user;
    }

    private User createUserData (String userId, String firstName, String lastName, String phoneNumber, String email)
    {
        User user = new User ();
        user.setUserId (userId);
        user.setPwd (pwd);
        user.setFirstName (firstName);
        user.setLastName (lastName);
        user.setEnabled (enable);
        user.setBirthAddress (birthAddress);
        user.setBirthday (birthday);
        user.setEmail (email);
        user.setMobilePhone (phoneNumber);
        return user;
    }
}
