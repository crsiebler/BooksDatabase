package booksdatabase;

import booksdatabase.Authors;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2014-11-16T22:01:35")
@StaticMetamodel(Titles.class)
public class Titles_ { 

    public static volatile SingularAttribute<Titles, Integer> editionnumber;
    public static volatile SingularAttribute<Titles, String> copyright;
    public static volatile SingularAttribute<Titles, String> isbn;
    public static volatile SingularAttribute<Titles, String> title;
    public static volatile ListAttribute<Titles, Authors> authorsList;

}