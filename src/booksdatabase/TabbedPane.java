// Assignment: 4
// Name: Cory Siebler
// StudentID: 1000832292
// Lecture Topic: Lecture 14 - JPA
// Description: Component to organize GUI panels.
package booksdatabase;

import java.util.List;
import javax.swing.JTabbedPane;

/**
 *
 * @author csiebler
 */
class TabbedPane extends JTabbedPane implements BooksInterface {
    
    // Declare the Panels to insert into the Tabbed Pane1
    AuthorsPanel authorsPanel;
    TitlesPanel titlesPanel;
    CreditsPanel creditsPanel;
    CustomQueryPanel customQueryPanel;
    
    public TabbedPane() {
        // Initialize the Panels for the Book Database operations
        authorsPanel = new AuthorsPanel(this);
        titlesPanel = new TitlesPanel(this);
        creditsPanel = new CreditsPanel(this);
        customQueryPanel = new CustomQueryPanel(this);
        
        // Add the Panels to the Tabbed Pane
        addTab("Authors", authorsPanel);
        addTab("Titles", titlesPanel);
        addTab("Credits", creditsPanel);
        addTab("Custom", customQueryPanel);
        
        // Load the information from the DB
        List<Authors> authors = authorsPanel.getAuthors();
        List<Titles> titles = titlesPanel.getTitles();
        titlesPanel.loadAuthors(authors);
        creditsPanel.loadAuthors(authors);
        creditsPanel.loadTitles(titles);
    }

    /**
     * 
     */
    @Override
    public void updateAuthors() {
        List<Authors> authors = authorsPanel.getAuthors();
        
        titlesPanel.loadAuthors(authors);
        creditsPanel.loadAuthors(authors);
    }

    /**
     * 
     */
    @Override
    public void updateTitles() {
        List<Titles> titles = titlesPanel.getTitles();
        
        creditsPanel.loadTitles(titles);
    }

    /**
     * 
     */
    @Override
    public void updateISBN() {
        updateAuthors();
        updateTitles();
    }
    
}
