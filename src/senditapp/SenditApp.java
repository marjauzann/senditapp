// File: SenditApp.java
package senditapp;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class SenditApp extends JFrame {
    // Components
    private JTabbedPane tabbedPane;
    private JPanel mainPanel, orderPanel, trackingPanel, profilePanel;
    private JTextField txtWeight, txtLength, txtWidth, txtHeight;
    private JTextField txtOriginPostalCode, txtDestinationPostalCode;
    private JTextField txtOriginPhone, txtDestinationPhone;
    private JTextField txtItemValue, txtItemQuantity;
    private JComboBox<String> cmbService;
    private JTextField txtPickupDate;
    private JTextArea txtOriginAddress, txtDestinationAddress;
    private JLabel lblTotalCost;
    private JButton btnCalculate, btnOrder, btnClear;
    
    // Constants
    private static final double BASE_RATE = 10000.0; // Rp per kg
    private static final double DISTANCE_RATE = 1000.0; // Rp per km
    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    
    public SenditApp() {
        setTitle("Sendit - Aplikasi Jasa Pengiriman Barang");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        initComponents();
        
        setVisible(true);
    }
    
    private void initComponents() {
        // Initialize main layout
        tabbedPane = new JTabbedPane();
        
        // Create panels
        orderPanel = createOrderPanel();
        trackingPanel = createTrackingPanel();
        profilePanel = createProfilePanel();
        
        // Add panels to tabbed pane
        tabbedPane.addTab("Buat Pengiriman", new ImageIcon(), orderPanel, "Buat order pengiriman baru");
        tabbedPane.addTab("Lacak Kiriman", new ImageIcon(), trackingPanel, "Lacak status pengiriman");
        tabbedPane.addTab("Profil", new ImageIcon(), profilePanel, "Pengaturan profil");
        
        add(tabbedPane);
    }
    
    private JPanel createOrderPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        
        // Form panel with GridBagLayout for better alignment
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Package Information Section
        JPanel packagePanel = new JPanel(new GridBagLayout());
        packagePanel.setBorder(BorderFactory.createTitledBorder("Informasi Paket"));
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.fill = GridBagConstraints.HORIZONTAL;
        gbc2.insets = new Insets(5, 5, 5, 5);
        
        // Weight
        gbc2.gridx = 0;
        gbc2.gridy = 0;
        packagePanel.add(new JLabel("Berat (kg):"), gbc2);
        
        gbc2.gridx = 1;
        txtWeight = new JTextField(10);
        packagePanel.add(txtWeight, gbc2);
        
        // Dimensions
        gbc2.gridx = 0;
        gbc2.gridy = 1;
        packagePanel.add(new JLabel("Panjang (cm):"), gbc2);
        
        gbc2.gridx = 1;
        txtLength = new JTextField(10);
        packagePanel.add(txtLength, gbc2);
        
        gbc2.gridx = 0;
        gbc2.gridy = 2;
        packagePanel.add(new JLabel("Lebar (cm):"), gbc2);
        
        gbc2.gridx = 1;
        txtWidth = new JTextField(10);
        packagePanel.add(txtWidth, gbc2);
        
        gbc2.gridx = 0;
        gbc2.gridy = 3;
        packagePanel.add(new JLabel("Tinggi (cm):"), gbc2);
        
        gbc2.gridx = 1;
        txtHeight = new JTextField(10);
        packagePanel.add(txtHeight, gbc2);
        
        // Item value
        gbc2.gridx = 0;
        gbc2.gridy = 4;
        packagePanel.add(new JLabel("Nilai Barang (Rp):"), gbc2);
        
        gbc2.gridx = 1;
        txtItemValue = new JTextField(10);
        packagePanel.add(txtItemValue, gbc2);
        
        // Item quantity
        gbc2.gridx = 0;
        gbc2.gridy = 5;
        packagePanel.add(new JLabel("Jumlah Barang:"), gbc2);
        
        gbc2.gridx = 1;
        txtItemQuantity = new JTextField(10);
        packagePanel.add(txtItemQuantity, gbc2);
        
        // Add package panel to form panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        formPanel.add(packagePanel, gbc);
        
        // Origin Information Section
        JPanel originPanel = new JPanel(new GridBagLayout());
        originPanel.setBorder(BorderFactory.createTitledBorder("Informasi Pengirim"));
        GridBagConstraints gbc3 = new GridBagConstraints();
        gbc3.fill = GridBagConstraints.HORIZONTAL;
        gbc3.insets = new Insets(5, 5, 5, 5);
        
        // Origin postal code
        gbc3.gridx = 0;
        gbc3.gridy = 0;
        originPanel.add(new JLabel("Kode Pos:"), gbc3);
        
        gbc3.gridx = 1;
        txtOriginPostalCode = new JTextField(10);
        originPanel.add(txtOriginPostalCode, gbc3);
        
        // Origin phone
        gbc3.gridx = 0;
        gbc3.gridy = 1;
        originPanel.add(new JLabel("Nomor Telepon:"), gbc3);
        
        gbc3.gridx = 1;
        txtOriginPhone = new JTextField(10);
        originPanel.add(txtOriginPhone, gbc3);
        
        // Origin address
        gbc3.gridx = 0;
        gbc3.gridy = 2;
        originPanel.add(new JLabel("Alamat:"), gbc3);
        
        gbc3.gridx = 1;
        gbc3.gridwidth = 3;
        txtOriginAddress = new JTextArea(4, 20);
        txtOriginAddress.setLineWrap(true);
        txtOriginAddress.setWrapStyleWord(true);
        JScrollPane originAddressScroll = new JScrollPane(txtOriginAddress);
        originPanel.add(originAddressScroll, gbc3);
        
        // Add origin panel to form panel
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        formPanel.add(originPanel, gbc);
        
        // Destination Information Section
        JPanel destinationPanel = new JPanel(new GridBagLayout());
        destinationPanel.setBorder(BorderFactory.createTitledBorder("Informasi Penerima"));
        GridBagConstraints gbc4 = new GridBagConstraints();
        gbc4.fill = GridBagConstraints.HORIZONTAL;
        gbc4.insets = new Insets(5, 5, 5, 5);
        
        // Destination postal code
        gbc4.gridx = 0;
        gbc4.gridy = 0;
        destinationPanel.add(new JLabel("Kode Pos:"), gbc4);
        
        gbc4.gridx = 1;
        txtDestinationPostalCode = new JTextField(10);
        destinationPanel.add(txtDestinationPostalCode, gbc4);
        
        // Destination phone
        gbc4.gridx = 0;
        gbc4.gridy = 1;
        destinationPanel.add(new JLabel("Nomor Telepon:"), gbc4);
        
        gbc4.gridx = 1;
        txtDestinationPhone = new JTextField(10);
        destinationPanel.add(txtDestinationPhone, gbc4);
        
        // Destination address
        gbc4.gridx = 0;
        gbc4.gridy = 2;
        destinationPanel.add(new JLabel("Alamat:"), gbc4);
        
        gbc4.gridx = 1;
        gbc4.gridwidth = 3;
        txtDestinationAddress = new JTextArea(4, 20);
        txtDestinationAddress.setLineWrap(true);
        txtDestinationAddress.setWrapStyleWord(true);
        JScrollPane destAddressScroll = new JScrollPane(txtDestinationAddress);
        destinationPanel.add(destAddressScroll, gbc4);
        
        // Add destination panel to form panel
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        formPanel.add(destinationPanel, gbc);
        
        // Shipping Options Section
        JPanel shippingPanel = new JPanel(new GridBagLayout());
        shippingPanel.setBorder(BorderFactory.createTitledBorder("Opsi Pengiriman"));
        GridBagConstraints gbc5 = new GridBagConstraints();
        gbc5.fill = GridBagConstraints.HORIZONTAL;
        gbc5.insets = new Insets(5, 5, 5, 5);
        
        // Service type
        gbc5.gridx = 0;
        gbc5.gridy = 0;
        shippingPanel.add(new JLabel("Jenis Layanan:"), gbc5);
        
        gbc5.gridx = 1;
        String[] services = {"Same Day (0-12 jam)", "Next Day (12-24 jam)", "Reguler (2-3 hari)", "Ekonomi (4-7 hari)"};
        cmbService = new JComboBox<>(services);
        shippingPanel.add(cmbService, gbc5);
        
        // Pickup date
        gbc5.gridx = 0;
        gbc5.gridy = 1;
        shippingPanel.add(new JLabel("Tanggal Pengambilan:"), gbc5);
        
        gbc5.gridx = 1;
        txtPickupDate = new JTextField(10);
        // Set default date to today
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        txtPickupDate.setText(dateFormat.format(new Date()));
        shippingPanel.add(txtPickupDate, gbc5);
        
        // Total cost
        gbc5.gridx = 0;
        gbc5.gridy = 2;
        shippingPanel.add(new JLabel("Total Biaya:"), gbc5);
        
        gbc5.gridx = 1;
        lblTotalCost = new JLabel("Rp 0");
        lblTotalCost.setFont(new Font("Arial", Font.BOLD, 14));
        shippingPanel.add(lblTotalCost, gbc5);
        
        // Add shipping panel to form panel
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        formPanel.add(shippingPanel, gbc);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnCalculate = new JButton("Hitung Biaya");
        btnOrder = new JButton("Pesan");
        btnClear = new JButton("Bersihkan");
        
        buttonPanel.add(btnCalculate);
        buttonPanel.add(btnOrder);
        buttonPanel.add(btnClear);
        
        // Add all panels to the main panel
        panel.add(new JScrollPane(formPanel), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add action listeners
        addActionListeners();
        
        return panel;
    }
    
    private JPanel createTrackingPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JPanel trackingFormPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        trackingFormPanel.add(new JLabel("Nomor Resi:"));
        JTextField txtTrackingNumber = new JTextField(20);
        trackingFormPanel.add(txtTrackingNumber);
        JButton btnTrack = new JButton("Lacak");
        trackingFormPanel.add(btnTrack);
        
        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JTextArea txtTrackingResult = new JTextArea(15, 40);
        txtTrackingResult.setEditable(false);
        txtTrackingResult.setLineWrap(true);
        txtTrackingResult.setWrapStyleWord(true);
        
        resultPanel.add(new JScrollPane(txtTrackingResult), BorderLayout.CENTER);
        
        panel.add(trackingFormPanel, BorderLayout.NORTH);
        panel.add(resultPanel, BorderLayout.CENTER);
        
        // Action listener for tracking button
        btnTrack.addActionListener(e -> {
            String trackingNumber = txtTrackingNumber.getText().trim();
            if (trackingNumber.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Mohon masukkan nomor resi!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Simulate tracking result
            StringBuilder result = new StringBuilder();
            result.append("Informasi Pengiriman:\n");
            result.append("Nomor Resi: ").append(trackingNumber).append("\n");
            result.append("Status: Dalam proses pengiriman\n\n");
            result.append("Riwayat Pengiriman:\n");
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Calendar cal = Calendar.getInstance();
            
            // Generate random tracking history
            cal.add(Calendar.HOUR, -24);
            result.append(dateFormat.format(cal.getTime())).append(" - Paket telah diterima di gudang asal\n");
            
            cal.add(Calendar.HOUR, 6);
            result.append(dateFormat.format(cal.getTime())).append(" - Paket dikirim ke gudang tujuan\n");
            
            cal.add(Calendar.HOUR, 12);
            result.append(dateFormat.format(cal.getTime())).append(" - Paket telah sampai di gudang tujuan\n");
            
            cal.add(Calendar.HOUR, 4);
            result.append(dateFormat.format(cal.getTime())).append(" - Paket sedang dalam perjalanan ke alamat tujuan\n");
            
            txtTrackingResult.setText(result.toString());
        });
        
        return panel;
    }
    
    private JPanel createProfilePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JPanel infoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Profile info
        gbc.gridx = 0;
        gbc.gridy = 0;
        infoPanel.add(new JLabel("Nama:"), gbc);
        
        gbc.gridx = 1;
        JTextField txtName = new JTextField("User Demo", 20);
        infoPanel.add(txtName, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        infoPanel.add(new JLabel("Email:"), gbc);
        
        gbc.gridx = 1;
        JTextField txtEmail = new JTextField("user@example.com", 20);
        infoPanel.add(txtEmail, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        infoPanel.add(new JLabel("Nomor Telepon:"), gbc);
        
        gbc.gridx = 1;
        JTextField txtPhone = new JTextField("08123456789", 20);
        infoPanel.add(txtPhone, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        infoPanel.add(new JLabel("Alamat Default:"), gbc);
        
        gbc.gridx = 1;
        JTextArea txtAddress = new JTextArea(4, 20);
        txtAddress.setText("Jl. Contoh No. 123\nKecamatan Contoh\nKota Contoh\nKode Pos 12345");
        txtAddress.setLineWrap(true);
        txtAddress.setWrapStyleWord(true);
        JScrollPane addressScroll = new JScrollPane(txtAddress);
        infoPanel.add(addressScroll, gbc);
        
        // Rating history panel
        JPanel ratingPanel = new JPanel(new BorderLayout());
        ratingPanel.setBorder(BorderFactory.createTitledBorder("Riwayat Rating"));
        
        // Simulate rating history
        JPanel starPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel lblAverageRating = new JLabel("Rating Rata-rata: 4.5/5.0");
        lblAverageRating.setFont(new Font("Arial", Font.BOLD, 14));
        starPanel.add(lblAverageRating);
        
        String[] ratingData = {
            "Pengiriman #1001: 5/5 - Sangat cepat dan aman",
            "Pengiriman #1002: 4/5 - Barang sampai dengan baik",
            "Pengiriman #1003: 5/5 - Pelayanan memuaskan",
            "Pengiriman #1004: 4/5 - Pengiriman tepat waktu"
        };
        
        JList<String> ratingList = new JList<>(ratingData);
        JScrollPane ratingScroll = new JScrollPane(ratingList);
        
        ratingPanel.add(starPanel, BorderLayout.NORTH);
        ratingPanel.add(ratingScroll, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnSave = new JButton("Simpan Profil");
        JButton btnChangePassword = new JButton("Ubah Password");
        
        buttonPanel.add(btnSave);
        buttonPanel.add(btnChangePassword);
        
        // Add all panels to profile panel
        panel.add(infoPanel, BorderLayout.NORTH);
        panel.add(ratingPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Action listeners
        btnSave.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Profil berhasil disimpan!", "Informasi", JOptionPane.INFORMATION_MESSAGE);
        });
        
        btnChangePassword.addActionListener(e -> {
            JPasswordField oldPass = new JPasswordField(10);
            JPasswordField newPass = new JPasswordField(10);
            JPasswordField confirmPass = new JPasswordField(10);
            
            JPanel passPanel = new JPanel(new GridLayout(3, 2));
            passPanel.add(new JLabel("Password Lama:"));
            passPanel.add(oldPass);
            passPanel.add(new JLabel("Password Baru:"));
            passPanel.add(newPass);
            passPanel.add(new JLabel("Konfirmasi Password:"));
            passPanel.add(confirmPass);
            
            int result = JOptionPane.showConfirmDialog(this, passPanel, "Ubah Password", 
                                                      JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            
            if (result == JOptionPane.OK_OPTION) {
                JOptionPane.showMessageDialog(this, "Password berhasil diubah!", "Informasi", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        return panel;
    }
    
    private void addActionListeners() {
        // Calculate button action listener
        btnCalculate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateInputs()) {
                    calculateShippingCost();
                }
            }
        });
        
        // Order button action listener
        btnOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateInputs()) {
                    // Generate a random tracking number for demonstration
                    String trackingNumber = "SND" + System.currentTimeMillis() % 10000000;
                    JOptionPane.showMessageDialog(SenditApp.this, 
                        "Pesanan berhasil dibuat!\nNomor Resi: " + trackingNumber, 
                        "Pesanan Berhasil", JOptionPane.INFORMATION_MESSAGE);
                    clearForm();
                }
            }
        });
        
        // Clear button action listener
        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });
        
        // Add document listeners to enable real-time calculation
        DocumentListener documentListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                tryCalculate();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                tryCalculate();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                tryCalculate();
            }
            
            private void tryCalculate() {
                try {
                    if (allRequiredFieldsFilled()) {
                        calculateShippingCost();
                    }
                } catch (Exception ex) {
                    // Ignore exceptions during typing
                }
            }
        };
        
        // Add listeners to key fields
        txtWeight.getDocument().addDocumentListener(documentListener);
        txtLength.getDocument().addDocumentListener(documentListener);
        txtWidth.getDocument().addDocumentListener(documentListener);
        txtHeight.getDocument().addDocumentListener(documentListener);
        
        // Add listener to service combo box
        cmbService.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (allRequiredFieldsFilled()) {
                    calculateShippingCost();
                }
            }
        });
    }
    
    private boolean allRequiredFieldsFilled() {
        return !txtWeight.getText().trim().isEmpty() &&
               !txtLength.getText().trim().isEmpty() &&
               !txtWidth.getText().trim().isEmpty() &&
               !txtHeight.getText().trim().isEmpty();
    }
    
    private boolean validateInputs() {
        StringBuilder errors = new StringBuilder();
        
        // Validate weight (0.1 - 50 kg)
        try {
            double weight = Double.parseDouble(txtWeight.getText().trim());
            if (weight < 0.1 || weight > 50) {
                errors.append("Berat harus antara 0.1 kg hingga 50 kg\n");
            }
        } catch (NumberFormatException e) {
            errors.append("Berat harus berupa angka\n");
        }
        
        // Validate dimensions (max 200 cm per dimension, sum <= 400 cm)
        try {
            double length = Double.parseDouble(txtLength.getText().trim());
            double width = Double.parseDouble(txtWidth.getText().trim());
            double height = Double.parseDouble(txtHeight.getText().trim());
            
            if (length <= 0 || width <= 0 || height <= 0) {
                errors.append("Dimensi paket harus lebih besar dari 0 cm\n");
            } else if (length > 200 || width > 200 || height > 200) {
                errors.append("Dimensi maksimum adalah 200 cm per sisi\n");
            } else if (length + width + height > 400) {
                errors.append("Total dimensi (P+L+T) tidak boleh melebihi 400 cm\n");
            }
        } catch (NumberFormatException e) {
            errors.append("Dimensi harus berupa angka\n");
        }
        
        // Validate item value
        if (!txtItemValue.getText().trim().isEmpty()) {
            try {
                double value = Double.parseDouble(txtItemValue.getText().trim());
                if (value < 10000 || value > 100000000) {
                    errors.append("Nilai barang harus antara Rp 10.000 hingga Rp 100.000.000\n");
                }
            } catch (NumberFormatException e) {
                errors.append("Nilai barang harus berupa angka\n");
            }
        }
        
        // Validate item quantity
        if (!txtItemQuantity.getText().trim().isEmpty()) {
            try {
                int quantity = Integer.parseInt(txtItemQuantity.getText().trim());
                if (quantity < 1 || quantity > 100) {
                    errors.append("Jumlah barang harus antara 1 hingga 100 unit\n");
                }
            } catch (NumberFormatException e) {
                errors.append("Jumlah barang harus berupa angka bulat\n");
            }
        }
        
        // Validate postal codes
        if (!txtOriginPostalCode.getText().trim().isEmpty() && 
            !Pattern.matches("\\d{5}", txtOriginPostalCode.getText().trim())) {
            errors.append("Kode pos pengirim harus terdiri dari 5 digit\n");
        }
        
        if (!txtDestinationPostalCode.getText().trim().isEmpty() && 
            !Pattern.matches("\\d{5}", txtDestinationPostalCode.getText().trim())) {
            errors.append("Kode pos penerima harus terdiri dari 5 digit\n");
        }
        
        // Validate phone numbers
        if (!txtOriginPhone.getText().trim().isEmpty()) {
            String phone = txtOriginPhone.getText().trim();
            if (phone.length() < 10 || phone.length() > 13 || !phone.matches("\\d+")) {
                errors.append("Nomor telepon pengirim harus 10-13 digit\n");
            }
        }
        
        if (!txtDestinationPhone.getText().trim().isEmpty()) {
            String phone = txtDestinationPhone.getText().trim();
            if (phone.length() < 10 || phone.length() > 13 || !phone.matches("\\d+")) {
                errors.append("Nomor telepon penerima harus 10-13 digit\n");
            }
        }
        
        // Validate pickup date
        if (!txtPickupDate.getText().trim().isEmpty()) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                dateFormat.setLenient(false);
                Date pickupDate = dateFormat.parse(txtPickupDate.getText().trim());
                
                // Check if date is within allowed range (today to next 30 days)
                Calendar cal = Calendar.getInstance();
                Date today = cal.getTime();
                
                cal.add(Calendar.DAY_OF_MONTH, 30);
                Date maxDate = cal.getTime();
                
                if (pickupDate.before(today)) {
                    errors.append("Tanggal pengambilan tidak boleh di masa lalu\n");
                } else if (pickupDate.after(maxDate)) {
                    errors.append("Tanggal pengambilan maksimum 30 hari dari sekarang\n");
                }
            } catch (ParseException e) {
                errors.append("Format tanggal harus dd/MM/yyyy\n");
            }
        }
        
        // Check if there are any validation errors
        if (errors.length() > 0) {
            JOptionPane.showMessageDialog(this, errors.toString(), "Validasi Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    private void calculateShippingCost() {
        try {
            // Get weight and dimensions
            double weight = Double.parseDouble(txtWeight.getText().trim());
            double length = Double.parseDouble(txtLength.getText().trim());
            double width = Double.parseDouble(txtWidth.getText().trim());
            double height = Double.parseDouble(txtHeight.getText().trim());
            
            // Calculate volumetric weight (L x W x H / 6000)
            double volumetricWeight = (length * width * height) / 6000;
            
            // Use the greater of actual weight and volumetric weight
            double chargeableWeight = Math.max(weight, volumetricWeight);
            
            // Get service type multiplier
            double serviceMultiplier = 1.0;
            switch (cmbService.getSelectedIndex()) {
                case 0: // Same Day
                    serviceMultiplier = 3.0;
                    break;
                case 1: // Next Day
                    serviceMultiplier = 2.0;
                    break;
                case 2: // Regular
                    serviceMultiplier = 1.0;
                    break;
                case 3: // Economy
                    serviceMultiplier = 0.7;
                    break;
            }
            
            // Calculate distance based on postal codes (simplified)
            // In a real app, you would use actual distance calculation or API
            double distance = 10.0; // Default 10 km
            if (!txtOriginPostalCode.getText().trim().isEmpty() && 
                
!txtDestinationPostalCode.getText().trim().isEmpty()) {
                // Simple distance calculation based on postal code difference
                // This is just for demonstration, not an accurate distance calculation
                int originCode = Integer.parseInt(txtOriginPostalCode.getText().trim());
                int destCode = Integer.parseInt(txtDestinationPostalCode.getText().trim());
                distance = Math.abs(originCode - destCode) / 10.0;
                
                // Ensure distance is within valid range
                if (distance < 1) distance = 1;
                if (distance > 1000) distance = 1000;
            }
            
            // Calculate base cost based on weight
            double baseCost = chargeableWeight * BASE_RATE;
            
            // Add distance cost
            double distanceCost = distance * DISTANCE_RATE;
            
            // Calculate total cost
            double totalCost = (baseCost + distanceCost) * serviceMultiplier;
            
            // Display formatted cost
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
            lblTotalCost.setText(currencyFormat.format(totalCost).replace("$", "Rp "));
            
        } catch (NumberFormatException e) {
            // Error parsing numbers
            lblTotalCost.setText("Rp 0");
        }
    }
    
    private void clearForm() {
        // Clear all input fields
        txtWeight.setText("");
        txtLength.setText("");
        txtWidth.setText("");
        txtHeight.setText("");
        txtOriginPostalCode.setText("");
        txtDestinationPostalCode.setText("");
        txtOriginPhone.setText("");
        txtDestinationPhone.setText("");
        txtItemValue.setText("");
        txtItemQuantity.setText("");
        txtOriginAddress.setText("");
        txtDestinationAddress.setText("");
        
        // Reset pickup date to today
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        txtPickupDate.setText(dateFormat.format(new Date()));
        
        // Reset service type to first option
        cmbService.setSelectedIndex(0);
        
        // Reset total cost
        lblTotalCost.setText("Rp 0");
    }
    
    public static void main(String[] args) {
        // Set look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Start application
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SenditApp();
            }
        });
    }
}