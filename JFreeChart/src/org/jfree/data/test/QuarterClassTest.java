package org.jfree.data.test;
import static org.junit.Assert.*;
import org.jfree.data.time.Quarter;
import org.jfree.data.time.Year;
import org.junit.Test;
import java.util.Calendar;
import java.util.TimeZone;

public class QuarterClassTest {
    Quarter quarter;

    private void arrange(Integer quart, Integer year) {
        quarter = new Quarter(quart, year);
    }

    private void arrange(Integer quart, Year year) {
        quarter = new Quarter(quart, year);
    }

    private void arrange() {
        quarter = new Quarter();
    }

    @Test
    public void testQuarterDefaultCtor() {
        arrange();
        assertEquals(2023, quarter.getYear().getYear());
        assertEquals(2, quarter.getQuarter());
    }

    @Test
    public void testQuarterConstructorWithQuarterAndYear() {
        //valid
        arrange(3, 2023);
        assertEquals(2023, quarter.getYear().getYear());
        assertEquals(3, quarter.getQuarter());     

        //boundry up year
        arrange(1, 1900);
        assertEquals(1900, quarter.getYear().getYear());
        assertEquals(1, quarter.getQuarter());  

        //boundry down year
        arrange(4, 9999);
        assertEquals(9999, quarter.getYear().getYear());
        assertEquals(4, quarter.getQuarter());   

        //unvalid quarter down
        arrange(-1, 2021);
        assertEquals(2021, quarter.getYear().getYear());
        assertEquals(-1, quarter.getQuarter());  

        //unvalid quarter up
        arrange(5, 2021);
        assertEquals(2021, quarter.getYear().getYear());
        assertEquals(5, quarter.getQuarter());  

    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testQuarterConstructorWithQuarterAndYear2() {
        
        //unvalid year down
        arrange(1, 1899);
        assertEquals(1899, quarter.getYear().getYear());
        assertEquals(1, quarter.getQuarter());  

        //unvalid year up
        arrange(1, 10000);
        assertEquals(10000, quarter.getYear().getYear());
        assertEquals(1, quarter.getQuarter());  
    }

    @Test
    public void testQuarterConstructorWithQuarterAndYeartype() {
        //valid
        Year year = new Year(2021);
        int Quarter = 3;
        arrange(Quarter, year);
        assertEquals(year, quarter.getYear());
        assertEquals(Quarter, quarter.getQuarter());   
        
        //boundry down 
        year = new Year(1900);
        Quarter = 1;
        arrange(Quarter, year);
        assertEquals(year, quarter.getYear());
        assertEquals(Quarter, quarter.getQuarter()); 
        
        //boundry up 
        year = new Year(9999);
        Quarter = 4;
        arrange(Quarter, year);
        assertEquals(year, quarter.getYear());
        assertEquals(Quarter, quarter.getQuarter());   

        //unvalid quarter down
        year = new Year(2021);
        Quarter = -1;
        arrange(Quarter, year);
        assertEquals(year, quarter.getYear());
        assertEquals(Quarter, quarter.getQuarter()); 

        //unvalid quarter up
        year = new Year(2021);
        Quarter = 5;
        arrange(Quarter, year);
        assertEquals(year, quarter.getYear());
        assertEquals(Quarter, quarter.getQuarter());  
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testQuarterConstructorWithQuarterAndYeartype2() {
        //unvalid year down
        Year year = new Year(1899);
        int Quarter = 1;
        arrange(Quarter, year);
        assertEquals(year, quarter.getYear());
        assertEquals(Quarter, quarter.getQuarter());  

        //unvalid year up
        year = new Year(10000);
        Quarter = 1;
        arrange(Quarter, year);
        assertEquals(year, quarter.getYear());
        assertEquals(Quarter, quarter.getQuarter());  
    }

    @Test
    public void testGetQuarter() {
        arrange(2, 2023);
        // assert
        assertEquals(2, quarter.getQuarter());
    }

    @Test
    public void testGetYear() {
        int year = 2023;
        arrange(2, year);
        // assert
        assertEquals(year, quarter.getYear().getYear());
    }

    @Test
    public void testGetYear2() {
        Year year = new Year(2023);
        arrange(2, year);
        // assert
        assertEquals(year, quarter.getYear());
    }

    @Test
    public void testCompareTo() {
        Quarter q1 = new Quarter(1, 2023);
        Quarter q2 = new Quarter(2, 2023);
        Quarter q3 = new Quarter(3, 2023);

        // test equality
        assertEquals(0, q1.compareTo(q1));
        
        // test less than
        assertTrue(q1.compareTo(q2) < 0);
        assertTrue(q2.compareTo(q3) < 0);
        
        // test greater than
        assertTrue(q2.compareTo(q1) > 0);
        assertTrue(q3.compareTo(q2) > 0);
        
    }

    @Test(expected = java.lang.IllegalArgumentException.class)
    public void testCompareTo2() {
        arrange(2, 20223);
        quarter.compareTo(null);       
    }

    @Test
    public void testEquals() {
        Quarter q1 = new Quarter(1, 2022);
        Quarter q2 = new Quarter(1, 2022);
        Quarter q3 = new Quarter(2, 2022);
        Quarter q4 = new Quarter(1, 2021);
        
        assertTrue(q1.equals(q2));  // Same quarter and year
        assertFalse(q1.equals(q3)); // Different quarter, same year
        assertFalse(q1.equals(q4)); // Same quarter, different year
        assertFalse(q1.equals(null)); // Should return false if the target is null
        assertFalse(q1.equals("test")); // Should return false if the target is not a Quarter instance
    }

    @Test
    public void testGetFirstMillisecond() {
        // Test the first millisecond of Q1 2023 in the default time zone.
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.JANUARY, 1);
        arrange(1, 2023);
        long expected = calendar.getTimeInMillis();
        long actual = quarter.getFirstMillisecond(calendar);
        assertEquals(expected, actual);
        
        // Test the first millisecond of Q2 2025 in cairo time zone.
        calendar = Calendar.getInstance(TimeZone.getTimeZone("Africa/Cairo"));
        calendar.set(2025, Calendar.APRIL, 1);
        arrange(2, 2025);
        expected = calendar.getTimeInMillis();
        actual = quarter.getFirstMillisecond(calendar);
        assertEquals(expected, actual);
    }

    @Test(expected = java.lang.NullPointerException.class)
    public void testGetFirsttMillisecond2() {
        //calender is null
        Calendar calendar = null;
        long result = quarter.getFirstMillisecond(calendar);
        
    }

    
    @Test
    public void testGetLastMillisecond() {
        // Test the first millisecond of Q1 2023 in the default time zone.
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.JANUARY, 1);
        arrange(1, 2023);
        long expected = quarter.getLastMillisecond(calendar); 
        long actual = 1672473599999L;
        assertEquals(expected, actual);
        
        // Test the first millisecond of Q2 2025 in cairo time zone.
        calendar = Calendar.getInstance(TimeZone.getTimeZone("Africa/Cairo"));
        calendar.set(2025, Calendar.APRIL, 1);
        arrange(2, 2025);
        expected = quarter.getLastMillisecond(calendar); 
        actual = 1730457599999L;
        assertEquals(expected, actual);
    }

    @Test(expected = java.lang.NullPointerException.class)
    public void testGetLastMillisecond2() {
        //calender is null
        Calendar calendar = null;
        long result = quarter.getLastMillisecond(calendar);
        
    }
}
