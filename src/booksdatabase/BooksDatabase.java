// Assignment: 4
// Name: Cory Siebler
// StudentID: 1000832292
// Lecture Topic: Lecture 14 - JPA
// Description: 
package booksdatabase;

import javax.swing.JFrame;

/**
 *
 * @author csiebler
 */
public class BooksDatabase {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            // Initialize a JFrame to hold the GUI
            JFrame frame = new JFrame("CSE 494 - Books Database");
            
            // Set the default close operation
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            // Initialize the JTabbedPane to hold the tabbed panels
            TabbedPane tabbedPane = new TabbedPane();
            
            // Add the tabbed pane and set the frame size & visibility
            frame.add(tabbedPane);
            frame.pack();
            frame.setVisible(true);
        });
    }

}
