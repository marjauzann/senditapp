package senditapp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Test cases for Sendit Application based on Boundary Value Analysis (BVA)
 * This class contains test cases for different input parameters according to their boundaries
 */
public class TestCases {
    
    /**
     * Test cases for Package Weight (0.1 - 50 kg)
     * Boundaries: 0.1, 50
     * Test values: 0.09 (invalid), 0.1 (valid), 0.11 (valid), 49.99 (valid), 50 (valid), 50.01 (invalid)
     */
    public static void testPackageWeight() {
        System.out.println("=== Testing Package Weight ===");
        testWeight(0.09, false); // Below minimum - Invalid
        testWeight(0.1, true);   // Minimum boundary - Valid
        testWeight(0.11, true);  // Just above minimum - Valid
        testWeight(25, true);    // Normal value - Valid
        testWeight(49.99, true); // Just below maximum - Valid
        testWeight(50, true);    // Maximum boundary - Valid
        testWeight(50.01, false); // Above maximum - Invalid
    }
    
    private static void testWeight(double weight, boolean expectedResult) {
        boolean result = (weight >= 0.1 && weight <= 50);
        System.out.println("Weight: " + weight + " kg, Valid: " + result + 
                           (result == expectedResult ? " (PASS)" : " (FAIL)"));
    }
    
    /**
     * Test cases for Package Dimensions (max 200 cm per dimension, sum <= 400 cm)
     * Boundaries: 1, 200 for individual dimensions; 400 for sum
     */
    public static void testPackageDimensions() {
        System.out.println("\n=== Testing Package Dimensions ===");
        
        // Test individual dimensions
        testDimension(0, 50, 50, false);    // Invalid length
        testDimension(1, 50, 50, true);     // Minimum length
        testDimension(200, 50, 50, true);   // Maximum length
        testDimension(201, 50, 50, false);  // Invalid length
        
        testDimension(50, 0, 50, false);    // Invalid width
        testDimension(50, 1, 50, true);     // Minimum width
        testDimension(50, 200, 50, true);   // Maximum width
        testDimension(50, 201, 50, false);  // Invalid width
        
        testDimension(50, 50, 0, false);    // Invalid height
        testDimension(50, 50, 1, true);     // Minimum height
        testDimension(50, 50, 200, true);   // Maximum height
        testDimension(50, 50, 201, false);  // Invalid height
        
        // Test sum of dimensions
        testDimension(133, 133, 133, true);   // Sum < 400
        testDimension(133, 133, 134, false);  // Sum > 400
        testDimension(150, 150, 100, true);   // Sum = 400
    }
    
    private static void testDimension(double length, double width, double height, boolean expectedResult) {
        boolean result = (length > 0 && width > 0 && height > 0 && 
                         length <= 200 && width <= 200 && height <= 200 &&
                         (length + width + height) <= 400);
        System.out.println("Dimensions: " + length + " x " + width + " x " + height + 
                         " cm, Sum: " + (length + width + height) + 
                         " cm, Valid: " + result + 
                         (result == expectedResult ? " (PASS)" : " (FAIL)"));
    }
    
    /**
     * Test cases for Postal Code (5 digit numeric)
     * Boundaries: 5 digits
     */
    public static void testPostalCode() {
        System.out.println("\n=== Testing Postal Code ===");
        testPostalCode("", false);       // Empty - Invalid
        testPostalCode("1234", false);   // 4 digits - Invalid
        testPostalCode("12345", true);   // 5 digits - Valid
        testPostalCode("123456", false); // 6 digits - Invalid
        testPostalCode("1234a", false);  // Non-numeric - Invalid
    }
    
    private static void testPostalCode(String code, boolean expectedResult) {
        boolean result = code.matches("\\d{5}");
        System.out.println("Postal Code: " + code + ", Valid: " + result + 
                         (result == expectedResult ? " (PASS)" : " (FAIL)"));
    }
    
    /**
     * Test cases for Phone Number (10-13 digits)
     * Boundaries: 10 digits, 13 digits
     */
    public static void testPhoneNumber() {
        System.out.println("\n=== Testing Phone Number ===");
        testPhoneNumber("", false);           // Empty - Invalid
        testPhoneNumber("123456789", false);  // 9 digits - Invalid
        testPhoneNumber("1234567890", true);  // 10 digits - Valid
        testPhoneNumber("08123456789", true); // 11 digits - Valid
        testPhoneNumber("081234567890", true); // 12 digits - Valid
        testPhoneNumber("0812345678901", true); // 13 digits - Valid
        testPhoneNumber("08123456789012", false); // 14 digits - Invalid
        testPhoneNumber("08123456a89", false);    // Non-numeric - Invalid
    }
    
    private static void testPhoneNumber(String number, boolean expectedResult) {
        boolean result = number.matches("\\d{10,13}");
        System.out.println("Phone Number: " + number + ", Valid: " + result + 
                         (result == expectedResult ? " (PASS)" : " (FAIL)"));
    }
    
    /**
     * Test cases for Item Value (Rp 10,000 - Rp 100,000,000)
     * Boundaries: 10,000, 100,000,000
     */
    public static void testItemValue() {
        System.out.println("\n=== Testing Item Value ===");
        testItemValue(9999, false);       // Below minimum - Invalid
        testItemValue(10000, true);       // Minimum boundary - Valid
        testItemValue(10001, true);       // Just above minimum - Valid
        testItemValue(99999999, true);    // Just below maximum - Valid
        testItemValue(100000000, true);   // Maximum boundary - Valid
        testItemValue(100000001, false);  // Above maximum - Invalid
    }
    
    private static void testItemValue(double value, boolean expectedResult) {
        boolean result = (value >= 10000 && value <= 100000000);
        System.out.println("Item Value: Rp " + value + ", Valid: " + result + 
                         (result == expectedResult ? " (PASS)" : " (FAIL)"));
    }
    
    /**
     * Test cases for Item Quantity (1-100 units)
     * Boundaries: 1, 100
     */
    public static void testItemQuantity() {
        System.out.println("\n=== Testing Item Quantity ===");
        testItemQuantity(0, false);    // Below minimum - Invalid
        testItemQuantity(1, true);     // Minimum boundary - Valid
        testItemQuantity(2, true);     // Just above minimum - Valid
        testItemQuantity(99, true);    // Just below maximum - Valid
        testItemQuantity(100, true);   // Maximum boundary - Valid
        testItemQuantity(101, false);  // Above maximum - Invalid
    }
    
    private static void testItemQuantity(int quantity, boolean expectedResult) {
        boolean result = (quantity >= 1 && quantity <= 100);
        System.out.println("Item Quantity: " + quantity + ", Valid: " + result + 
                         (result == expectedResult ? " (PASS)" : " (FAIL)"));
    }
    
    /**
     * Test cases for Pickup Date (today to 30 days from now)
     * Boundaries: today, today+30 days
     */
    public static void testPickupDate() {
        System.out.println("\n=== Testing Pickup Date ===");
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        
        // Get current date
        Calendar cal = Calendar.getInstance();
        Date today = cal.getTime();
        
        // Yesterday
        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date yesterday = cal.getTime();
        
        // Tomorrow
        cal.add(Calendar.DAY_OF_MONTH, 2); // -1 + 2 = +1 from today
        Date tomorrow = cal.getTime();
        
        // 29 days from today
        cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 29);
        Date day29 = cal.getTime();
        
        // 30 days from today (max allowed)
        cal.add(Calendar.DAY_OF_MONTH, 1); // 29 + 1 = 30
        Date day30 = cal.getTime();
        
        // 31 days from today (beyond limit)
        cal.add(Calendar.DAY_OF_MONTH, 1); // 30 + 1 = 31
        Date day31 = cal.getTime();
        
        testDate(yesterday, false, today, day30); // Yesterday - Invalid
        testDate(today, true, today, day30);      // Today - Valid
        testDate(tomorrow, true, today, day30);   // Tomorrow - Valid
        testDate(day29, true, today, day30);      // 29 days from today - Valid
        testDate(day30, true, today, day30);      // 30 days from today - Valid
        testDate(day31, false, today, day30);     // 31 days from today - Invalid
        
        System.out.println("Today: " + dateFormat.format(today));
        System.out.println("Max Date (Today+30): " + dateFormat.format(day30));
    }
    
    private static void testDate(Date testDate, boolean expectedResult, Date minDate, Date maxDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        boolean result = (!testDate.before(minDate) && !testDate.after(maxDate));
        System.out.println("Date: " + dateFormat.format(testDate) + ", Valid: " + result + 
                         (result == expectedResult ? " (PASS)" : " (FAIL)"));
    }
    
    /**
     * Run all test cases
     */
    public static void runAllTests() {
        testPackageWeight();
        testPackageDimensions();
        testPostalCode();
        testPhoneNumber();
        testItemValue();
        testItemQuantity();
        testPickupDate();
    }
    
    public static void main(String[] args) {
        runAllTests();
    }
}