// Assignment: 4
// Name: Cory Siebler
// StudentID: 1000832292
// Lecture Topic: Lecture 14 - JPA
// Description: 
package booksdatabase;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author csiebler
 */
class TitlesPanel extends JPanel {
    
    // Declare the components for the Panel
    private final JTable resultsTable;
    private final JScrollPane scrollPane;
    private final JLabel authorLabel;
    private final JLabel isbnLabel;
    private final JLabel titleLabel;
    private final JLabel editionLabel;
    private final JLabel copyrightLabel;
    private final JComboBox authorComboBox;
    private final JTextField isbnField;
    private final JTextField titleField;
    private final JTextField editionField;
    private final JTextField copyrightField;
    
    /**
     * 
     */
    public TitlesPanel() {
        resultsTable = new JTable();
        scrollPane = new JScrollPane(resultsTable);
        authorLabel = new JLabel("Author:");
        isbnLabel = new JLabel("ISBN:");
        titleLabel = new JLabel("Title:");
        editionLabel = new JLabel("Edition:");
        copyrightLabel = new JLabel("Copyright:");
        authorComboBox = new JComboBox();
        isbnField = new JTextField();
        titleField = new JTextField();
        editionField = new JTextField();
        copyrightField = new JTextField();
    }
    
}
