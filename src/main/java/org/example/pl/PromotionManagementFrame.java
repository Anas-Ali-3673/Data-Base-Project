package org.example.pl;

import org.example.bll.PromotionManager;
import org.example.dto.PromotionDto;
import org.example.dto.ProductDto;
import org.example.bll.ProductManager;
import org.example.bll.SignUpUser;
import org.example.dal.ProductDal;
import org.example.ui.UiUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PromotionManagementFrame extends JFrame {
    private final PromotionManager promotionManager;
    private final ProductManager productManager;
    private final SignUpUser userManager;

    private final DefaultTableModel promotionTableModel;
    private final DefaultTableModel productsTableModel;
    private final DefaultTableModel usersTableModel;

    private JTable usersTable;
    private JTable productsTable;
    private JTable promotionTable;

    private JTabbedPane tabbedPane;

    private User selectedUser;
    private ProductDto selectedProduct;

    public PromotionManagementFrame() {
        promotionManager = new PromotionManager();
        productManager = new ProductManager(new ProductDal());
        userManager = new SignUpUser();

        setTitle("Promotion Management");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        promotionTableModel = createTableModel(new String[]{"Code", "Discount (%)", "Active", "Product", "User"});
        productsTableModel = createTableModel(new String[]{"ID", "Name", "Description", "Price", "Stock", "Category", "Available"});
        usersTableModel = createTableModel(new String[]{"User ID", "Username", "Email", "Role"});

        initUI();
        UiUtils.setButtonCursor(this);
    }

    private void initUI() {
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 16));

        tabbedPane.addTab("Promotions", createPromotionPanel());
        tabbedPane.addTab("Products", createProductsPanel());
        tabbedPane.addTab("Users", createUsersPanel());

        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createPromotionPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);

        promotionTable = new JTable(promotionTableModel);
        promotionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        promotionTable.setFont(new Font("Arial", Font.PLAIN, 14));
        promotionTable.setRowHeight(30);

        JScrollPane tableScrollPane = new JScrollPane(promotionTable);
        tableScrollPane.setBorder(BorderFactory.createTitledBorder("Promotions"));

        JPanel addPromotionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton addPromotionButton = new JButton("Add Promotion");
        addPromotionButton.setBackground(new Color(46, 204, 113));
        addPromotionButton.setForeground(Color.WHITE);

        addPromotionButton.addActionListener(e -> handleAddPromotion());

        addPromotionPanel.add(addPromotionButton);

        panel.add(tableScrollPane, BorderLayout.CENTER);
        panel.add(addPromotionPanel, BorderLayout.SOUTH);

        loadPromotions();
        return panel;
    }

    private JPanel createProductsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        productsTable = new JTable(productsTableModel);
        panel.add(new JScrollPane(productsTable), BorderLayout.CENTER);
        loadProducts();
        return panel;
    }

    private JPanel createUsersPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        usersTable = new JTable(usersTableModel);
        panel.add(new JScrollPane(usersTable), BorderLayout.CENTER);
        loadUsers();
        return panel;
    }

    private DefaultTableModel createTableModel(String[] columnNames) {
        return new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    private void loadPromotions() {
        promotionTableModel.setRowCount(0);
        List<PromotionDto> promotions = promotionManager.getAllPromotions();
        for (PromotionDto promotion : promotions) {
            ProductDto product = productManager.getProductById(promotion.getProductId());
            User user = userManager.getUserId(promotion.getUserId());
            promotionTableModel.addRow(new Object[]{
                promotion.getCode(),
                promotion.getDiscountPercentage(),
                promotion.isActive(),
                product != null ? product.getName() : "N/A",
                user != null ? user.getUsername() : "N/A"
            });
        }
    }

    private void loadProducts() {
        productsTableModel.setRowCount(0);
        List<ProductDto> products = productManager.getAllProducts();
        for (ProductDto product : products) {
            productsTableModel.addRow(new Object[]{
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getCategory(),
                product.isAvailable() ? "Yes" : "No"
            });
        }
    }

    private void loadUsers() {
        usersTableModel.setRowCount(0);
        List<User> users = userManager.getAllUsers();
        for (User user : users) {
            usersTableModel.addRow(new Object[]{
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole()
            });
        }
    }

    private void handleAddPromotion() {
        // Navigate to Users Tab
        tabbedPane.setSelectedIndex(2);
        JOptionPane.showMessageDialog(this, "Please select a user.", "Step 1", JOptionPane.INFORMATION_MESSAGE);

        usersTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedUserRow = usersTable.getSelectedRow();
                if (selectedUserRow != -1) {
                    selectedUser = new User(
                        (int) usersTableModel.getValueAt(selectedUserRow, 0),
                        (String) usersTableModel.getValueAt(selectedUserRow, 1),
                        (String) usersTableModel.getValueAt(selectedUserRow, 2),
                        (String) usersTableModel.getValueAt(selectedUserRow, 3),
                        "defaultRole" // Provide the fifth argument here
                    );

                    // Navigate to Products Tab
                    tabbedPane.setSelectedIndex(1);
                    JOptionPane.showMessageDialog(this, "Please select a product.", "Step 2", JOptionPane.INFORMATION_MESSAGE);

                    productsTable.getSelectionModel().addListSelectionListener(ev -> {
                        if (!ev.getValueIsAdjusting()) {
                            int selectedProductRow = productsTable.getSelectedRow();
                            if (selectedProductRow != -1) {
                                selectedProduct = new ProductDto(
                                    (int) productsTableModel.getValueAt(selectedProductRow, 0),
                                    (String) productsTableModel.getValueAt(selectedProductRow, 1),
                                    (String) productsTableModel.getValueAt(selectedProductRow, 2),
                                    (double) productsTableModel.getValueAt(selectedProductRow, 3),
                                    (int) productsTableModel.getValueAt(selectedProductRow, 4),
                                    (String) productsTableModel.getValueAt(selectedProductRow, 5),
                                    "Yes".equals(productsTableModel.getValueAt(selectedProductRow, 6))
                                );

                                // Show Promotion Details Dialog
                                showPromotionDetailsDialog();
                            }
                        }
                    });
                }
            }
        });
    }

    private void showPromotionDetailsDialog() {
        JTextField codeField = new JTextField();
        JTextField discountField = new JTextField();
        JCheckBox activeCheckBox = new JCheckBox();

        Object[] message = {
            "Promotion Code:", codeField,
            "Discount (%):", discountField,
            "Active:", activeCheckBox
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Enter Promotion Details", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String code = codeField.getText().trim();
            String discountText = discountField.getText().trim();

            if (code.isEmpty() || discountText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Code and Discount are required.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double discount;
            try {
                discount = Double.parseDouble(discountText);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid discount value.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean isActive = activeCheckBox.isSelected();
            PromotionDto promotion = new PromotionDto(code, discount, isActive, selectedProduct.getId(), selectedUser.getUserId());

            if (promotionManager.addPromotion(promotion)) {
                loadPromotions();
                JOptionPane.showMessageDialog(this, "Promotion added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add promotion.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PromotionManagementFrame().setVisible(true));
    }
}
