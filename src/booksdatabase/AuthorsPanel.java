// Assignment: 4
// Name: Cory Siebler
// StudentID: 1000832292
// Lecture Topic: Lecture 14 - JPA
// Description: Shows the Authors table and inserts a new row.
package booksdatabase;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author csiebler
 */
class AuthorsPanel extends JPanel {

    private static final String EMPTY = "";

    // Define the Column Index
    private static final int COL_INDEX = 0;
    private static final int COL_FIRST = 1;
    private static final int COL_LAST = 2;

    // Define the Column Headers
    private static final Object[][] DATA = {};
    private static final String[] COL_NAMES = {"ID", "First Name", "Last Name"};

    /*
     Create the EntityManagerFactory object.
         
     Static method createEntityManagerFactory of class Persistence
     receives as an argument the persistence unit name, specified in the
     persistence.xml file.
     */
    private static final EntityManagerFactory EMF
            = Persistence.createEntityManagerFactory("BooksPU");

    /*
     Create an application-managed EntityManager that handles interactions
     between the app and the database.
     */
    private static final EntityManager EM = EMF.createEntityManager();

    // Declare the components for the Panel
    private final JTable resultsTable;
    private final JScrollPane scrollPane;
    private final JLabel firstNameLabel;
    private final JLabel lastNameLabel;
    private final JLabel insertLabel;
    private final JTextField firstNameField;
    private final JTextField lastNameField;
    private final JButton submitButton;
    private final BooksInterface iface;

    /**
     * Constructor
     * 
     * @param iface
     */
    public AuthorsPanel(BooksInterface iface) {
        // Assign the database interface
        this.iface = iface;
        
        // Initialize the GUI components
        resultsTable = new JTable();
        scrollPane = new JScrollPane(resultsTable);
        insertLabel = new JLabel("Add/Edit Author:");
        firstNameLabel = new JLabel("First Name:");
        lastNameLabel = new JLabel("Last Name:");
        firstNameField = new JTextField();
        lastNameField = new JTextField();
        submitButton = new JButton("Submit");

        // Set the Table Model
        resultsTable.setColumnSelectionAllowed(false);
        resultsTable.setRowSelectionAllowed(true);
        resultsTable.setFillsViewportHeight(true);
        resultsTable.setShowGrid(true);
        resultsTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        resultsTable.setModel(new DefaultTableModel(DATA, COL_NAMES) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        // Add a button listener
        submitButton.addActionListener(this::submitActionPerformed);

        // Initialize a panel for the input & the menu
        JPanel inputPanel = new JPanel();
        JPanel menuPanel = new JPanel();

        // Add input fields to the panel
        inputPanel.setLayout(new SpringLayout());
        inputPanel.add(firstNameLabel);
        inputPanel.add(firstNameField);
        inputPanel.add(lastNameLabel);
        inputPanel.add(lastNameField);
        
        // Use the Java provided layout on the input panel
        SpringUtilities.makeCompactGrid(inputPanel,
                2, 2, // Rows x Cols
                5, 5, 5, 5 // Padding
        );

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
     * Load the database data into the table.
     */
    public List<Authors> getAuthors() {
        // Get the Table Model to insert rows
        DefaultTableModel model = (DefaultTableModel) resultsTable.getModel();

        // Initialize the query
        TypedQuery<Authors> findAllAuthors
                = EM.createNamedQuery("Authors.findAll", Authors.class);
        
        // Clear the table
        model.setNumRows(0);

        /*
         getResultList returns a List<Authors>.
         
         The stream's forEach method displays each Authors object in the List.
         
         The lambda expression passed to forEach obtains the "getter" info
         about the Authors.
         */
        List<Authors> authors = findAllAuthors.getResultList();
        authors.stream().forEach((author) -> {
            SwingUtilities.invokeLater(() -> {
                model.addRow(new Object[]{
                    author.getAuthorid(),
                    author.getFirstname(),
                    author.getLastname()
                });
            });
        });
        
        return authors;
    }

    /**
     * Update the text fields when selection changes.
     */
    private void updateFields() {
        // Check that a selection is made
        if (resultsTable.getSelectedRow() > -1) {
            firstNameField.setText((String) resultsTable.getValueAt(
                    resultsTable.getSelectedRow(),
                    COL_FIRST
            ));
            lastNameField.setText((String) resultsTable.getValueAt(
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
     * @return 
     */
    private boolean validInput() {
        return !firstNameField.getText().isEmpty()
                && !lastNameField.getText().isEmpty();
    }

    /**
     *
     * @param evt
     */
    private void submitActionPerformed(ActionEvent evt) {
        // Check if a selection is made on the table
        if (validInput()) {
            if (resultsTable.getSelectedRow() > -1) {
                // Updating an existing row
                Authors author = EM.find(Authors.class, resultsTable.getValueAt(
                        resultsTable.getSelectedRow(),
                        COL_INDEX
                ));

                // Get an EntityTransaction to manage insert operation
                EntityTransaction transaction = EM.getTransaction();

                try {
                    transaction.begin(); // start transaction

                    // Update the fields for the Author entity
                    author.setFirstname(firstNameField.getText());
                    author.setLastname(lastNameField.getText());

                    transaction.commit(); // commit changes to the database
                    JOptionPane.showMessageDialog(this, "Author updated!",
                            "Author updated", JOptionPane.PLAIN_MESSAGE);

                    // Reload the database
                    iface.updateAuthors();
                } catch (Exception e) {
                    transaction.rollback(); // undo database operations
                    JOptionPane.showMessageDialog(this, "Person not updated!",
                            e.getMessage(), JOptionPane.PLAIN_MESSAGE);
                }
            } else {
                // Inserting into the database
                Authors author = new Authors();

                // Insert the fields into the Authors entity
                author.setFirstname(firstNameField.getText());
                author.setLastname(lastNameField.getText());

                // Get an EntityTransaction to manage insert operation
                EntityTransaction transaction = EM.getTransaction();

                try {
                    transaction.begin(); // start transaction
                    EM.persist(author); // store new entry
                    transaction.commit(); // commit changes to the database
                    JOptionPane.showMessageDialog(this, "Author added!",
                            "Author added", JOptionPane.PLAIN_MESSAGE);
                    
                    // Reload the database
                    iface.updateAuthors();
                } catch (Exception e) {
                    transaction.rollback(); // undo database operations
                    JOptionPane.showMessageDialog(this, "Author not added!",
                            e.getMessage(), JOptionPane.PLAIN_MESSAGE);
                }
            }
        }
    }

}
