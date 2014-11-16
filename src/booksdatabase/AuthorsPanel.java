// Assignment: 4
// Name: Cory Siebler
// StudentID: 1000832292
// Lecture Topic: Lecture 14 - JPA
// Description: 
package booksdatabase;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;

/**
 *
 * @author csiebler
 */
class AuthorsPanel extends JPanel {
    
    private static final String EMPTY = "";
    
    private static final int COL_FIRST = 1;
    private static final int COL_LAST = 2;
    
    private static final String[] COL_NAMES = { "First Name", "Last Name" };
    
    // Declare the components for the Panel
    private final JTable resultsTable;
    private final JScrollPane scrollPane;
    private final JLabel firstNameLabel;
    private final JLabel lastNameLabel;
    private final JLabel insertLabel;
    private final JTextField firstNameField;
    private final JTextField lastNameField;
    private final JButton submitButton;

    /**
     * Constructor
     */
    public AuthorsPanel() {
        // Initialize the GUI components
        resultsTable = new JTable();
        scrollPane = new JScrollPane(resultsTable);
        insertLabel = new JLabel("Add/Edit Author:");
        firstNameLabel = new JLabel("First Name:");
        lastNameLabel = new JLabel("Last Name:");
        firstNameField = new JTextField();
        lastNameField = new JTextField();
        submitButton = new JButton("Submit");
        
        // Initialize a panel for the input & the menu
        JPanel inputPanel = new JPanel();
        JPanel menuPanel = new JPanel();
        
        // Add input fields sto the panel
        inputPanel.setLayout(new GridLayout(2,2));
        inputPanel.add(firstNameLabel);
        inputPanel.add(firstNameField);
        inputPanel.add(lastNameLabel);
        inputPanel.add(lastNameField);
        
        // Add menu components to the panel
        menuPanel.setLayout(new BorderLayout());
        menuPanel.add(insertLabel, BorderLayout.NORTH);
        menuPanel.add(inputPanel, BorderLayout.CENTER);
        menuPanel.add(submitButton, BorderLayout.SOUTH);
        
        // Add the components to the main panel
        this.setLayout(new BorderLayout());
        this.add(scrollPane, BorderLayout.NORTH);
        this.add(menuPanel, BorderLayout.CENTER);
        
        // Add a listener to update the text fields
        resultsTable.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            updateFields();
        });
    }

    /**
     * 
     */
    private void updateFields() {
        if (resultsTable.getSelectedRow() > -1) {
            firstNameField.setText(resultsTable.getValueAt(
                    resultsTable.getSelectedRow(),
                    COL_FIRST
            ));
            lastNameField.setText(resultsTable.getValueAt(
                    resultsTable.getSelectedRow(),
                    COL_LAST
            ));
        } else {
            firstNameField.setText(EMPTY);
            lastNameField.setText(EMPTY);
        }
    }
    
    /**
     * 
     * @param author 
     */
    private void insertIntoTable(Authors author) {
        
    }
    
}
