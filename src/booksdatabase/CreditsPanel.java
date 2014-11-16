// Assignment: 4
// Name: Cory Siebler
// StudentID: 1000832292
// Lecture Topic: Lecture 14 - JPA
// Description: 
package booksdatabase;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author csiebler
 */
class CreditsPanel extends JPanel {
    
    // Declare the components for the Panel
    private final JTable resultsTable;
    private final JScrollPane scrollPane;
    private final JComboBox titleComboBox;
    
    /**
     * 
     */
    public CreditsPanel() {
        resultsTable = new JTable();
        scrollPane = new JScrollPane(resultsTable);
        titleComboBox = new JComboBox();
    }
    
}
