package org.example.pl;

import org.example.bll.ProductManager;
import org.example.dto.ProductDto;
import org.example.ui.UiUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ProductSearchPanel extends JPanel {
    private ProductManager productManager;
    private JTextField nameField;
    private JTextField categoryField;
    private JTextField minPriceField;
    private JTextField maxPriceField;
    private DefaultTableModel tableModel;

    public ProductSearchPanel(ProductManager productManager) {
        this.productManager = productManager;
        setLayout(new BorderLayout());

        JPanel searchPanel = new JPanel(new GridLayout(0, 2));
        searchPanel.add(new JLabel("Name:"));
        nameField = new JTextField(20);
        searchPanel.add(nameField);
        searchPanel.add(new JLabel("Category:"));
        categoryField = new JTextField(20);
        searchPanel.add(categoryField);
        searchPanel.add(new JLabel("Min Price:"));
        minPriceField = new JTextField(20);
        searchPanel.add(minPriceField);
        searchPanel.add(new JLabel("Max Price:"));
        maxPriceField = new JTextField(20);
        searchPanel.add(maxPriceField);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> searchProducts());
        searchPanel.add(searchButton);

        add(searchPanel, BorderLayout.NORTH);

        String[] columnNames = {"ID", "Name", "Description", "Price", "Stock", "Category", "Available"};
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable productTable = new JTable(tableModel);
        add(new JScrollPane(productTable), BorderLayout.CENTER);
        UiUtils.setButtonCursor(this);
    }

    private void searchProducts() {
        String name = nameField.getText();
        String category = categoryField.getText();
        Double minPrice = null;
        Double maxPrice = null;
        try {
            if (!minPriceField.getText().isEmpty()) {
                minPrice = Double.parseDouble(minPriceField.getText());
            }
            if (!maxPriceField.getText().isEmpty()) {
                maxPrice = Double.parseDouble(maxPriceField.getText());
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid price format.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<ProductDto> products = productManager.searchProducts(name, category, minPrice, maxPrice);
        tableModel.setRowCount(0); // Clear existing rows
        for (ProductDto product : products) {
            Object[] row = {product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getStock(), product.getCategory(), product.isAvailable() ? "Yes" : "No"};
            tableModel.addRow(row);
        }
    }
}