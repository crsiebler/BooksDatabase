// Assignment: 4
// Name: Cory Siebler
// StudentID: 1000832292
// Lecture Topic: Lecture 14 - JPA
// Description: Displays the results of a custom query.
package booksdatabase;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author csiebler
 */
class CustomQueryPanel extends JPanel {
 
    // Declare the components for the Panel
    private final JTable resultsTable;
    private final JScrollPane scrollPane;
    private final JLabel queryLabel;
    private final JTextField queryField;
    
    /**
     * 
     */
    public CustomQueryPanel(BooksInterface iface) {
        resultsTable = new JTable();
        scrollPane = new JScrollPane(resultsTable);
        queryLabel = new JLabel("Custom Query:");
        queryField = new JTextField();
    }
    
}
