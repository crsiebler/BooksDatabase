// Assignment: 4
// Name: Cory Siebler
// StudentID: 1000832292
// Lecture Topic: Lecture 14 - JPA
// Description: 
package booksdatabase;

import javax.swing.JTabbedPane;

/**
 *
 * @author csiebler
 */
class TabbedPane extends JTabbedPane {
    
    // Declare the Panels to insert into the Tabbed Pane1
    AuthorsPanel authorsPanel;
    TitlesPanel titlesPanel;
    CreditsPanel creditsPanel;
    CustomQueryPanel customQueryPanel;
    
    public TabbedPane() {
        // Initialize the Panels for the Book Database operations
        authorsPanel = new AuthorsPanel();
        titlesPanel = new TitlesPanel();
        creditsPanel = new CreditsPanel();
        customQueryPanel = new CustomQueryPanel();
        
        // Add the Panels to the Tabbed Pane
        addTab("Authors", authorsPanel);
        addTab("Titles", titlesPanel);
        addTab("Credits", creditsPanel);
        addTab("Custom", customQueryPanel);
    }
    
}
