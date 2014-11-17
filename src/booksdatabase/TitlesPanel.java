// Assignment: 4
// Name: Cory Siebler
// StudentID: 1000832292
// Lecture Topic: Lecture 14 - JPA
// Description: Shows the Titles table and inserts new rows.
package booksdatabase;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author csiebler
 */
class TitlesPanel extends JPanel {
    
    // Define the Column Headers
    private static final Object[][] DATA = {};
    private static final String[] COL_NAMES = {"ISBN", "Title", "Edition No.", "Copyright"};
    
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
    private final JLabel authorLabel;
    private final JLabel isbnLabel;
    private final JLabel titleLabel;
    private final JLabel editionLabel;
    private final JLabel copyrightLabel;
    private final JLabel insertLabel;
    private final JComboBox authorComboBox;
    private final JTextField isbnField;
    private final JTextField titleField;
    private final JTextField editionField;
    private final JTextField copyrightField;
    private final JButton submitButton;
    private final BooksInterface iface;
    
    /**
     * Constructor
     * 
     * @param iface 
     */
    public TitlesPanel(BooksInterface iface) {
        // Assign the database interface
        this.iface = iface;
        
        resultsTable = new JTable();
        scrollPane = new JScrollPane(resultsTable);
        authorLabel = new JLabel("Author:");
        isbnLabel = new JLabel("ISBN:");
        titleLabel = new JLabel("Title:");
        editionLabel = new JLabel("Edition:");
        copyrightLabel = new JLabel("Copyright:");
        insertLabel = new JLabel("Add Title:");
        authorComboBox = new JComboBox();
        isbnField = new JTextField();
        titleField = new JTextField();
        editionField = new JTextField();
        copyrightField = new JTextField();
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
        
        // Add a combo box listener
        authorComboBox.addActionListener(this::authorActionPerformed);

        // Initialize a panel for the input & the menu
        JPanel inputPanel = new JPanel();
        JPanel menuPanel = new JPanel();

        // Add input fields sto the panel
        inputPanel.setLayout(new SpringLayout());
        inputPanel.add(authorLabel);
        inputPanel.add(authorComboBox);
        inputPanel.add(isbnLabel);
        inputPanel.add(isbnField);
        inputPanel.add(titleLabel);
        inputPanel.add(titleField);
        inputPanel.add(editionLabel);
        inputPanel.add(editionField);
        inputPanel.add(copyrightLabel);
        inputPanel.add(copyrightField);
        
        // Use the Java provided layout on the input panel
        SpringUtilities.makeCompactGrid(inputPanel,
                5, 2, // Rows x Cols
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
    }
    
    /**
     * Load the database data.
     */
    public List<Titles> getTitles() {
        // Initialize the query
        TypedQuery<Titles> findAllTitles
                = EM.createNamedQuery("Titles.findAll", Titles.class);
        
        return findAllTitles.getResultList();
    }
    
    /**
     * Load the authors from the database.
     */
    public void loadAuthors(List<Authors> authors) {
        // Clear the combo box
        authorComboBox.removeAllItems();
        
        authors.stream()
                .sorted(Comparator.comparing(Authors::getLastname).thenComparing(Authors::getFirstname))
                .forEach((Authors author) -> {
                    SwingUtilities.invokeLater(() -> {
                        authorComboBox.addItem(author);
                    });
                });
        
        // Load the Titles
        getTitlesByAuthor();
    }

    /**
     * Load the database data into the table.
     */
    private void getTitlesByAuthor() {
        // Make sure an author is selected
        if (authorComboBox.getSelectedIndex() > -1) {
            // Get the Table Model to insert rows
            DefaultTableModel model = (DefaultTableModel) resultsTable.getModel();

            // Clear the table
            model.setNumRows(0);

            TypedQuery<Titles> findAllTitlesByAuthor
                    = EM.createQuery("SELECT t FROM Titles AS t JOIN t.authorsList c WHERE c.authorid = :author", Titles.class)
                            .setParameter("author", ((Authors) authorComboBox.getSelectedItem()).getAuthorid());

            /*
             getResultList returns a List<Authors>.

             The stream's forEach method displays each Authors object in the List.

             The lambda expression passed to forEach obtains the "getter" info
             about the Authors.
             */
            findAllTitlesByAuthor.getResultList().stream().forEach((title) -> {
                SwingUtilities.invokeLater(() -> {
                    model.addRow(new Object[]{
                        title.getIsbn(),
                        title.getTitle(),
                        title.getEditionnumber(),
                        title.getCopyright()
                    });
                });
            });
        }
    }
    
    /**
     * 
     * @return 
     */
    private boolean validInput() {
        return !isbnField.getText().isEmpty()
                && !titleField.getText().isEmpty()
                && !editionField.getText().isEmpty()
                && !copyrightField.getText().isEmpty()
                && authorComboBox.getSelectedIndex() > -1;
    }
    
    /**
     *
     * @param evt
     */
    private void submitActionPerformed(ActionEvent evt) {
        // Check if a selection is made on the table
        if (validInput()) {
            // Inserting into the database
            Titles title = new Titles();
            
            // Initialize a list of Authors
            List<Authors> authors = new ArrayList<>();
            
            // Add the selected Author to the Title
            authors.add((Authors) authorComboBox.getSelectedItem());

            // Insert the fields into the Titles entity
            title.setIsbn(isbnField.getText());
            title.setTitle(titleField.getText());
            title.setEditionnumber(Integer.valueOf(editionField.getText()));
            title.setCopyright(copyrightField.getText());
            title.setAuthorsList(authors);
            
            // Get an EntityTransaction to manage insert operation
            EntityTransaction transaction = EM.getTransaction();

            try {
                transaction.begin(); // start transaction
                EM.persist(title); // store new entry
                transaction.commit(); // commit changes to the database
                JOptionPane.showMessageDialog(this, "Title added!",
                        "Title added", JOptionPane.PLAIN_MESSAGE);

                // Reload the database
                iface.updateTitles();
                getTitlesByAuthor();
            } catch (Exception e) {
                transaction.rollback(); // undo database operations
                JOptionPane.showMessageDialog(this, "Title not added!",
                        e.getMessage(), JOptionPane.PLAIN_MESSAGE);
            }
        }
    }

    /**
     *
     * @param evt
     */
    private void authorActionPerformed(ActionEvent evt) {
        getTitlesByAuthor();
    }

}
