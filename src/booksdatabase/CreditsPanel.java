// Assignment: 4
// Name: Cory Siebler
// StudentID: 1000832292
// Lecture Topic: Lecture 14 - JPA
// Description: Shows the AuthorISBN table and inserts new rows.
package booksdatabase;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.Comparator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author csiebler
 */
class CreditsPanel extends JPanel {

    // Define the Column Index
    private static final int COL_INDEX = 0;
    private static final int COL_FIRST = 1;
    private static final int COL_LAST = 2;

    // Define the Column Headers
    private static final Object[][] DATA = {};
    private static final String[] COL_NAMES = {"ID", "First Name", "Last Name"};

    // Declare the components for the Panel
    private final JTable resultsTable;
    private final JScrollPane scrollPane;
    private final JLabel insertLabel;
    private final JLabel titleLabel;
    private final JLabel authorLabel;
    private final JComboBox titleComboBox;
    private final JComboBox authorComboBox;
    private final JButton submitButton;
    private final BooksInterface iface;

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

    /**
     *
     */
    public CreditsPanel(BooksInterface iface) {
        // Assign the database interface
        this.iface = iface;

        // Initialize the GUI components
        resultsTable = new JTable();
        scrollPane = new JScrollPane(resultsTable);
        titleComboBox = new JComboBox();
        authorComboBox = new JComboBox();
        submitButton = new JButton("Submit");
        titleLabel = new JLabel("Title:");
        authorLabel = new JLabel("Author:");
        insertLabel = new JLabel("Insert Credit:");

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
        titleComboBox.addActionListener(this::titleActionPerformed);

        // Initialize a panel for the input & the menu
        JPanel inputPanel = new JPanel();
        JPanel menuPanel = new JPanel();

        // Add input fields to the panel
        inputPanel.setLayout(new SpringLayout());
        inputPanel.add(authorLabel);
        inputPanel.add(authorComboBox);
        inputPanel.add(titleLabel);
        inputPanel.add(titleComboBox);

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
    }
    
    /**
     * Load the authors from the database.
     */
    public void loadTitles(List<Titles> titles) {
        // Clear the combo box
        titleComboBox.removeAllItems();
        
        titles.stream()
                .sorted(Comparator.comparing(Titles::getIsbn))
                .forEach((Titles title) -> {
                    SwingUtilities.invokeLater(() -> {
                        titleComboBox.addItem(title);
                    });
                });
    }
    
    /**
     *
     */
    private void getAuthorsByTitle() {
        // Make sure an author is selected
        if (titleComboBox.getSelectedIndex() > -1) {
            // Get the Table Model to insert rows
            DefaultTableModel model = (DefaultTableModel) resultsTable.getModel();

            // Clear the table
            model.setNumRows(0);

            TypedQuery<Authors> findAllAuthorsByTitle
                    = EM.createQuery("SELECT a FROM Authors AS a JOIN a.titlesList t WHERE t.isbn = :title", Authors.class)
                    .setParameter("title", ((Titles) titleComboBox.getSelectedItem()).getIsbn());

            /*
             getResultList returns a List<Authors>.

             The stream's forEach method displays each Authors object in the List.

             The lambda expression passed to forEach obtains the "getter" info
             about the Authors.
             */
            findAllAuthorsByTitle.getResultList().stream()
                    .sorted(Comparator.comparing(Authors::getLastname).thenComparing(Authors::getFirstname))
                    .forEach((author) -> {
                        SwingUtilities.invokeLater(() -> {
                            model.addRow(new Object[]{
                                author.getAuthorid(),
                                author.getFirstname(),
                                author.getLastname()
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
        return authorComboBox.getSelectedIndex() > -1
                && titleComboBox.getSelectedIndex() > -1;
    }

    /**
     *
     * @param evt
     */
    private void submitActionPerformed(ActionEvent evt) {
        // Check if a selection is made on the table
        if (validInput()) {
            
        }
    }

    /**
     *
     * @param evt
     */
    private void titleActionPerformed(ActionEvent evt) {
        getAuthorsByTitle();
    }

}
