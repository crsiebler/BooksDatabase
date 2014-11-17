package booksdatabase;

import booksdatabase.Titles;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2014-11-16T22:01:35")
@StaticMetamodel(Authors.class)
public class Authors_ { 

    public static volatile SingularAttribute<Authors, String> firstname;
    public static volatile SingularAttribute<Authors, Integer> authorid;
    public static volatile ListAttribute<Authors, Titles> titlesList;
    public static volatile SingularAttribute<Authors, String> lastname;

}