package kasirkantin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Kasirkantin extends JFrame {
    // Deklarasi Komponen Java Swing
    private JTextField txtNamaBarang, txtHargaSatuan, txtJumlah, txtPembayaran, txtTotalHarga, txtKembalian;
    private JButton btnHitung, btnBayar, btnReset;

    public Kasirkantin() {
        // Pengaturan Jendela (JFrame)
        setTitle("Aplikasi Kasir Kantin Kampus");
        setSize(450, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel Utama dengan BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Judul Aplikasi
        JLabel lblJudul = new JLabel("KASIR KANTIN KAMPUS", JLabel.CENTER);
        lblJudul.setFont(new Font("Arial", Font.BOLD, 18));
        mainPanel.add(lblJudul, BorderLayout.NORTH);

        // Panel Form Input & Output (GridLayout 8 baris, 2 kolom)
        JPanel formPanel = new JPanel(new GridLayout(8, 2, 10, 10));

        formPanel.add(new JLabel("Nama Makanan/Minuman:"));
        txtNamaBarang = new JTextField();
        formPanel.add(txtNamaBarang);

        formPanel.add(new JLabel("Harga Satuan (Rp):"));
        txtHargaSatuan = new JTextField();
        formPanel.add(txtHargaSatuan);

        formPanel.add(new JLabel("Jumlah Pembelian:"));
        txtJumlah = new JTextField();
        formPanel.add(txtJumlah);

        formPanel.add(new JLabel("Uang Pembayaran (Rp):"));
        txtPembayaran = new JTextField();
        formPanel.add(txtPembayaran);

        formPanel.add(new JLabel("Total Harga (Rp):"));
        txtTotalHarga = new JTextField();
        txtTotalHarga.setEditable(false);
        formPanel.add(txtTotalHarga);

        formPanel.add(new JLabel("Uang Kembalian (Rp):"));
        txtKembalian = new JTextField();
        txtKembalian.setEditable(false);
        formPanel.add(txtKembalian);

        // Panel Tombol Aksi
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        btnHitung = new JButton("Hitung");
        btnBayar = new JButton("Bayar");
        btnReset = new JButton("Reset");

        buttonPanel.add(btnHitung);
        buttonPanel.add(btnBayar);
        buttonPanel.add(btnReset);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);

        // --- EVENT HANDLING (Tugas 4) ---
        
        // Tombol Hitung
        btnHitung.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateInputsForCalculate()) {
                    try {
                        double harga = Double.parseDouble(txtHargaSatuan.getText());
                        int jumlah = Integer.parseInt(txtJumlah.getText());
                        
                        double total = harga * jumlah;
                        txtTotalHarga.setText(String.valueOf((int) total));
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(Kasirkantin.this, 
                            "Kolom angka harus diisi dengan angka yang valid!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Tombol Bayar
        btnBayar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateInputsForPayment()) {
                    try {
                        double harga = Double.parseDouble(txtHargaSatuan.getText());
                        int jumlah = Integer.parseInt(txtJumlah.getText());
                        double total = harga * jumlah;
                        txtTotalHarga.setText(String.valueOf((int) total));

                        double pembayaran = Double.parseDouble(txtPembayaran.getText());

                        if (pembayaran < total) {
                            double kekurangan = total - pembayaran;
                            JOptionPane.showMessageDialog(Kasirkantin.this, 
                                "Pembayaran kurang Rp" + (int)kekurangan, "Peringatan", JOptionPane.WARNING_MESSAGE);
                            txtKembalian.setText("");
                        } else {
                            double kembalian = pembayaran - total;
                            txtKembalian.setText(String.valueOf((int) kembalian));
                            JOptionPane.showMessageDialog(Kasirkantin.this, 
                                "Transaksi Berhasil!\nTotal: Rp" + (int)total + "\nKembali: Rp" + (int)kembalian, 
                                "Informasi", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(Kasirkantin.this, 
                            "Kolom angka harus diisi dengan angka yang valid!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Tombol Reset
        btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtNamaBarang.setText("");
                txtHargaSatuan.setText("");
                txtJumlah.setText("");
                txtPembayaran.setText("");
                txtTotalHarga.setText("");
                txtKembalian.setText("");
                txtNamaBarang.requestFocus(); // Mengembalikan fokus ke input pertama
            }
        });
    }

    // --- VALIDASI INPUT (Tugas 5) ---
    private boolean validateInputsForCalculate() {
        if (txtNamaBarang.getText().trim().isEmpty() || 
            txtHargaSatuan.getText().trim().isEmpty() || 
            txtJumlah.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama barang, harga satuan, dan jumlah pembelian wajib diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        try {
            double harga = Double.parseDouble(txtHargaSatuan.getText());
            int jumlah = Integer.parseInt(txtJumlah.getText());

            if (harga < 0) {
                JOptionPane.showMessageDialog(this, "Harga satuan tidak boleh bernilai negatif!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return false;
            }
            if (jumlah <= 0) {
                JOptionPane.showMessageDialog(this, "Jumlah pembelian harus lebih dari nol!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Harga satuan dan jumlah pembelian harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean validateInputsForPayment() {
        if (!validateInputsForCalculate()) {
            return false;
        }
        if (txtPembayaran.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Uang pembayaran wajib diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        try {
            double pembayaran = Double.parseDouble(txtPembayaran.getText());
            if (pembayaran < 0) {
                JOptionPane.showMessageDialog(this, "Uang pembayaran tidak boleh bernilai negatif!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Uang pembayaran harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    // Metode Utama untuk Menjalankan Aplikasi
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Kasirkantin().setVisible(true);
            }
        });
    }
}