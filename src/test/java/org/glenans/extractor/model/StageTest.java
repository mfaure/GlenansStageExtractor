/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.glenans.extractor.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import junit.framework.TestCase;
import org.apache.commons.lang3.time.DateUtils;

/**
 *
 * @author mfaure
 */
public class StageTest extends TestCase {
    
    public StageTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of getName method, of class Stage.
     */
    public void testconvertStringToDate() {
        System.out.println("convertStringToDate in a static way, I know it's bad but it's just to illustrate how to Unit Test");
        Date date = Stage.convertStringToDate("");
        assertTrue(date == null);
        date = Stage.convertStringToDate("04/10/15");
        Date date1 = Stage.convertStringToDate("18/07/15 à 15H00");
        Date date2 = Stage.convertStringToDate("24/07/15 à 17H00");
        Date date3 = Stage.convertStringToDate("01/08/15 à 15H00");
        Date date4 = Stage.convertStringToDate("7/08/15 à 17H00");
        Date date5 = Stage.convertStringToDate("7/08/15 à 17H01");
        Date date6 = Stage.convertStringToDate("07/08/15 à 17H01");
        Date date7 = Stage.convertStringToDate("07/8/15 à 17H01");
        Date date8 = Stage.convertStringToDate("7/8/15 à 17H01");
        Date date9 = Stage.convertStringToDate("12/09/15 à 14h30");
        
//        System.out.println(new SimpleDateFormat(Stage.DATE_PATTERN_OUTPUT, Locale.FRANCE).format(null));
        System.out.println(new SimpleDateFormat(Stage.DATE_PATTERN_OUTPUT, Locale.FRANCE).format(date));
        System.out.println(new SimpleDateFormat(Stage.DATE_PATTERN_OUTPUT, Locale.FRANCE).format(date1));
        System.out.println(new SimpleDateFormat(Stage.DATE_PATTERN_OUTPUT, Locale.FRANCE).format(date2));
        System.out.println(new SimpleDateFormat(Stage.DATE_PATTERN_OUTPUT, Locale.FRANCE).format(date3));
        System.out.println(new SimpleDateFormat(Stage.DATE_PATTERN_OUTPUT, Locale.FRANCE).format(date4));
        System.out.println(new SimpleDateFormat(Stage.DATE_PATTERN_OUTPUT, Locale.FRANCE).format(date5));
        System.out.println(new SimpleDateFormat(Stage.DATE_PATTERN_OUTPUT, Locale.FRANCE).format(date6));
        System.out.println(new SimpleDateFormat(Stage.DATE_PATTERN_OUTPUT, Locale.FRANCE).format(date7));
        System.out.println(new SimpleDateFormat(Stage.DATE_PATTERN_OUTPUT, Locale.FRANCE).format(date8));
        System.out.println(new SimpleDateFormat(Stage.DATE_PATTERN_OUTPUT, Locale.FRANCE).format(date9));
        
        // Test conversion is OK
        assertNotNull(date);
        assertNotNull(date1);
        assertNotNull(date2);
        assertNotNull(date3);
        assertNotNull(date4);
        assertNotNull(date5);
        assertNotNull(date6);
        assertNotNull(date7);
        assertNotNull(date8);
        assertNotNull(date9);
        assertEquals(new SimpleDateFormat(Stage.DATE_PATTERN_OUTPUT, Locale.FRANCE).format(date),"04/10/15 00:00");
//        assertEquals(new SimpleDateFormat(Stage.DATE_PATTERN_OUTPUT, Locale.FRANCE).format(date1),"04/10/15 00:00");
//18/07/15 15:00
//24/07/15 17:00
//01/08/15 15:00
//07/08/15 17:00
//07/08/15 17:01
//07/08/15 17:01
//07/08/15 17:01
//07/08/15 17:01
//12/09/15 14:30
        // test date is the same but time is not
        assertTrue(DateUtils.isSameDay(date4, date5));
        assertFalse(DateUtils.isSameInstant(date4, date5));
                
        // Comparison test
        assertTrue(date1.before(date2));
        
        // Comparison Date with different format on day
        assertTrue(DateUtils.isSameInstant(date6, date5));
        assertTrue(DateUtils.isSameInstant(date7, date5));
        assertTrue(DateUtils.isSameInstant(date8, date5));
    }

    
}
