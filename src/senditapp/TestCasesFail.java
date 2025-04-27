package senditapp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.InputStream;

/**
 * Test case untuk aplikasi SenditApp tanpa menggunakan JUnit
 * Hanya menggunakan pure Java
 */
public class TestCasesFail {
    
    private static final InputStream originalIn = System.in;
    private static final PrintStream originalOut = System.out;
    private static ByteArrayOutputStream testOut;
    
    public static void main(String[] args) {
        System.out.println("Menjalankan test case untuk SenditApp...");
        
        // Jalankan semua test case
        testInvalidUsernameLogin();
        testInvalidPasswordLogin();
        testNegativeWeightInput();
        testInvalidPhoneNumberFormat();
        testInvalidShippingOption();
        testTrackingInvalidTrackingNumber();
        
        System.out.println("\nSemua test case selesai dijalankan.");
    }
    
    /**
     * Memulai test dan mempersiapkan output stream
     */
    private static void setupTest() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }
    
    /**
     * Membersihkan test dan mengembalikan stream default
     */
    private static void cleanupTest() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }
    
    /**
     * Memverifikasi apakah output aplikasi mengandung teks yang diharapkan
     */
    private static boolean outputContains(String text) {
        return testOut.toString().contains(text);
    }
    
    /**
     * Test Case 1: Validasi Login Gagal - Username Tidak Valid
     */
    private static void testInvalidUsernameLogin() {
        setupTest();
        
        // Arrange - Setup input simulasi
        String simulatedUserInput = "invalidUser\nadmin123\n5\n"; // username, password, exit
        ByteArrayInputStream testIn = new ByteArrayInputStream(simulatedUserInput.getBytes());
        System.setIn(testIn);
        
        // Act - Jalankan aplikasi dengan input simulasi
        SenditApp.main(new String[]{});
        
        // Assert - Cek output aplikasi
        boolean passed = outputContains("Login Failed") || outputContains("Login gagal");
        
        // Print result
        cleanupTest();
        System.out.println("Test 1 (Username Tidak Valid): " + (passed ? "PASSED" : "FAILED"));
    }
    
    /**
     * Test Case 2: Validasi Login Gagal - Password Salah
     */
    private static void testInvalidPasswordLogin() {
        setupTest();
        
        // Arrange
        String simulatedUserInput = "admin\nwrongpassword\n5\n"; // username, wrong password, exit
        ByteArrayInputStream testIn = new ByteArrayInputStream(simulatedUserInput.getBytes());
        System.setIn(testIn);
        
        // Act
        SenditApp.main(new String[]{});
        
        // Assert
        boolean passed = outputContains("Login Failed") || outputContains("Login gagal");
        
        // Print result
        cleanupTest();
        System.out.println("Test 2 (Password Salah): " + (passed ? "PASSED" : "FAILED"));
    }
    
    /**
     * Test Case 3: Validasi Berat Paket - Input Negatif
     */
    private static void testNegativeWeightInput() {
        setupTest();
        
        // Arrange - Login, pilih opsi 1 (kirim paket), input data dengan berat negatif
        String simulatedUserInput = "admin\nadmin123\n1\nJohn\nJl. ABC\n123456\nJane\nJl. XYZ\n654321\n-5\nRegular\n5\n";
        ByteArrayInputStream testIn = new ByteArrayInputStream(simulatedUserInput.getBytes());
        System.setIn(testIn);
        
        // Act
        SenditApp.main(new String[]{});
        
        // Assert - Aplikasi tidak memvalidasi berat paket negatif, seharusnya gagal
        boolean containsNegativeWeight = outputContains("-5");
        boolean containsTotal = outputContains("Total");
        boolean passed = containsNegativeWeight && containsTotal;
        
        // Print result
        cleanupTest();
        System.out.println("Test 3 (Berat Paket Negatif): " + (passed ? "FAILED (Aplikasi menerima berat negatif)" : "PASSED"));
    }
    
    /**
     * Test Case 4: Validasi Input Nomor Telepon - Format Tidak Valid
     */
    private static void testInvalidPhoneNumberFormat() {
        setupTest();
        
        // Arrange - Login, pilih opsi 1 (kirim paket), nomor telepon dengan huruf
        String simulatedUserInput = "admin\nadmin123\n1\nJohn\nJl. ABC\nabc123\nJane\nJl. XYZ\n654321\n2\nRegular\n5\n";
        ByteArrayInputStream testIn = new ByteArrayInputStream(simulatedUserInput.getBytes());
        System.setIn(testIn);
        
        // Act
        SenditApp.main(new String[]{});
        
        // Assert - Aplikasi tidak memvalidasi format nomor telepon, seharusnya gagal
        boolean containsInvalidPhone = outputContains("abc123");
        boolean containsTotal = outputContains("Total");
        boolean passed = containsInvalidPhone && containsTotal;
        
        // Print result
        cleanupTest();
        System.out.println("Test 4 (Nomor Telepon Tidak Valid): " + (passed ? "FAILED (Aplikasi menerima format telepon tidak valid)" : "PASSED"));
    }
    
    /**
     * Test Case 5: Validasi Opsi Pengiriman - Pilihan Tidak Valid
     */
    private static void testInvalidShippingOption() {
        setupTest();
        
        // Arrange - Login, pilih opsi 1 (kirim paket), input data dengan opsi pengiriman tidak valid
        String simulatedUserInput = "admin\nadmin123\n1\nJohn\nJl. ABC\n123456\nJane\nJl. XYZ\n654321\n2\nUltraSpeed\n5\n";
        ByteArrayInputStream testIn = new ByteArrayInputStream(simulatedUserInput.getBytes());
        System.setIn(testIn);
        
        // Act
        SenditApp.main(new String[]{});
        
        // Assert - Aplikasi tidak memvalidasi opsi pengiriman, menggunakan nilai default yang tidak benar
        boolean containsInvalidOption = outputContains("UltraSpeed");
        boolean containsTotal = outputContains("Total");
        boolean passed = containsInvalidOption && containsTotal;
        
        // Print result
        cleanupTest();
        System.out.println("Test 5 (Opsi Pengiriman Tidak Valid): " + (passed ? "FAILED (Aplikasi menerima opsi pengiriman tidak valid)" : "PASSED"));
    }
    
    /**
     * Test Case 6: Lacak Pengiriman - Nomor Resi Tidak Valid
     */
    private static void testTrackingInvalidTrackingNumber() {
        setupTest();
        
        // Arrange - Login, pilih opsi 2 (lacak pengiriman), nomor resi tidak valid
        String simulatedUserInput = "admin\nadmin123\n2\nINV-999999\n5\n";
        ByteArrayInputStream testIn = new ByteArrayInputStream(simulatedUserInput.getBytes());
        System.setIn(testIn);
        
        // Act
        SenditApp.main(new String[]{});
        
        // Assert
        boolean passed = outputContains("tidak ditemukan") || outputContains("not found");
        
        // Print result
        cleanupTest();
        System.out.println("Test 6 (Lacak Pengiriman Tidak Valid): " + (passed ? "PASSED" : "FAILED"));
    }
}