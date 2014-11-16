// Assignment: 4
// Name: Cory Siebler
// StudentID: 1000832292
// Lecture Topic: Lecture 14 - JPA
// Description: 
package booksdatabase;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author cnaustin
 * @author csiebler
 */
@Entity
@Table(name = "TITLES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Titles.findAll", query = "SELECT t FROM Titles t"),
    @NamedQuery(name = "Titles.findByIsbn", query = "SELECT t FROM Titles t WHERE t.isbn = :isbn"),
    @NamedQuery(name = "Titles.findByTitle", query = "SELECT t FROM Titles t WHERE t.title = :title"),
    @NamedQuery(name = "Titles.findByEditionnumber", query = "SELECT t FROM Titles t WHERE t.editionnumber = :editionnumber"),
    @NamedQuery(name = "Titles.findByCopyright", query = "SELECT t FROM Titles t WHERE t.copyright = :copyright")})
public class Titles implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @Column(name = "ISBN")
    private String isbn;
    
    @Basic(optional = false)
    @Column(name = "TITLE")
    private String title;
    
    @Basic(optional = false)
    @Column(name = "EDITIONNUMBER")
    private int editionnumber;
    
    @Basic(optional = false)
    @Column(name = "COPYRIGHT")
    private String copyright;
    
    @ManyToMany(mappedBy = "titlesList")
    private List<Authors> authorsList;

    /**
     * 
     */
    public Titles() {
    }

    /**
     * 
     * @param isbn 
     */
    public Titles(String isbn) {
        this.isbn = isbn;
    }

    /**
     * 
     * @param isbn
     * @param title
     * @param editionnumber
     * @param copyright 
     */
    public Titles(String isbn, String title, int editionnumber, String copyright) {
        this.isbn = isbn;
        this.title = title;
        this.editionnumber = editionnumber;
        this.copyright = copyright;
    }

    /**
     * 
     * @return 
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * 
     * @param isbn 
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * 
     * @return 
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title 
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return 
     */
    public int getEditionnumber() {
        return editionnumber;
    }

    /**
     * 
     * @param editionnumber 
     */
    public void setEditionnumber(int editionnumber) {
        this.editionnumber = editionnumber;
    }

    /**
     * 
     * @return 
     */
    public String getCopyright() {
        return copyright;
    }

    /**
     * 
     * @param copyright 
     */
    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    /**
     * 
     * @return 
     */
    @XmlTransient
    public List<Authors> getAuthorsList() {
        return authorsList;
    }

    /**
     * 
     * @param authorsList 
     */
    public void setAuthorsList(List<Authors> authorsList) {
        this.authorsList = authorsList;
    }

    /**
     * 
     * @return 
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (isbn != null ? isbn.hashCode() : 0);
        return hash;
    }

    /**
     * 
     * @param object
     * @return 
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Titles)) {
            return false;
        }
        
        Titles other = (Titles) object;
        
        return !((this.isbn == null && other.isbn != null) || (this.isbn != null && !this.isbn.equals(other.isbn)));
    }

    /**
     * 
     * @return 
     */
    @Override
    public String toString() {
        return "booksdatabaseexample.Titles[ isbn=" + isbn + " ]";
    }

}
