/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Aleks
 */
public class PagesTest {
    
    public PagesTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of index method, of class Pages.
     */
    @Test
    public void testIndex() throws Exception {
        System.out.println("index");
        Pages instance = null;
        String expResult = "";
        String result = instance.index();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of error method, of class Pages.
     */
    @Test
    public void testError() throws Exception {
        System.out.println("error");
        Pages instance = null;
        String expResult = "";
        String result = instance.error();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of about method, of class Pages.
     */
    @Test
    public void testAbout() throws Exception {
        System.out.println("about");
        Pages instance = null;
        String expResult = "";
        String result = instance.about();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of uploadFile method, of class Pages.
     */
    @Test
    public void testUploadFile() throws Exception {
        System.out.println("uploadFile");
        Pages instance = null;
        String expResult = "";
        String result = instance.uploadFile();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of registration method, of class Pages.
     */
    @Test
    public void testRegistration() throws Exception {
        System.out.println("registration");
        Pages instance = null;
        String expResult = "";
        String result = instance.registration();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of login method, of class Pages.
     */
    @Test
    public void testLogin() throws Exception {
        System.out.println("login");
        Pages instance = null;
        String expResult = "";
        String result = instance.login();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of logout method, of class Pages.
     */
    @Test
    public void testLogout() throws Exception {
        System.out.println("logout");
        Pages instance = null;
        String expResult = "";
        String result = instance.logout();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of redirect method, of class Pages.
     */
    @Test
    public void testRedirect() throws Exception {
        System.out.println("redirect");
        Pages instance = null;
        String expResult = "";
        String result = instance.redirect();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of commandHandler method, of class Pages.
     */
    @Test
    public void testCommandHandler() throws Exception {
        System.out.println("commandHandler");
        String buf = "";
        Pages instance = null;
        String expResult = "";
        String result = instance.commandHandler(buf);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTopQuestion method, of class Pages.
     */
    @Test
    public void testGetTopQuestion() {
        System.out.println("getTopQuestion");
        Pages instance = null;
        String expResult = "";
        String result = instance.getTopQuestion();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getContent method, of class Pages.
     */
    @Test
    public void testGetContent() {
        System.out.println("getContent");
        Pages instance = null;
        String expResult = "";
        String result = instance.getContent();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getError method, of class Pages.
     */
    @Test
    public void testGetError() {
        System.out.println("getError");
        Pages instance = null;
        String expResult = "";
        String result = instance.getError();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of redirectHTTPS method, of class Pages.
     */
    @Test
    public void testRedirectHTTPS() {
        System.out.println("redirectHTTPS");
        String url = "";
        Pages instance = null;
        String expResult = "";
        String result = instance.redirectHTTPS(url);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMethod method, of class Pages.
     */
    @Test
    public void testGetMethod() throws Exception {
        System.out.println("getMethod");
        String name = "";
        Object[] o = null;
        Pages instance = null;
        Object expResult = null;
        Object result = instance.getMethod(name, o);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCMethod method, of class Pages.
     */
    @Test
    public void testGetCMethod() throws Exception {
        System.out.println("getCMethod");
        String name = "";
        Object[] o = null;
        Pages instance = null;
        Object expResult = null;
        Object result = instance.getCMethod(name, o);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSelf method, of class Pages.
     */
    @Test
    public void testGetSelf() {
        System.out.println("getSelf");
        Pages instance = null;
        String[] expResult = null;
        String[] result = instance.getSelf();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCommands method, of class Pages.
     */
    @Test
    public void testGetCommands() {
        System.out.println("getCommands");
        Pages instance = null;
        String[] expResult = null;
        String[] result = instance.getCommands();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
