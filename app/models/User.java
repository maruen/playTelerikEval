package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.validation.*;

@Entity 
public class User extends Model {

    private static final long serialVersionUID = 1L;

	@Id
    public Long id;
    
    @Constraints.Required
    public String name;
    
    @Constraints.Required
    public String login;
    
    @Constraints.Required
    public String email;
    
    @Constraints.Required
    public String role;
    
    @Constraints.Required
    public Boolean enabled;
    
    public static Model.Finder<Long,User> find = new Model.Finder<Long,User>(Long.class, User.class);

    public static Map<String,String> options() {
        LinkedHashMap<String,String> options = new LinkedHashMap<String,String>();
        for(User c: User.find.orderBy("name").findList()) {
            options.put(c.id.toString(), c.name);
        }
        return options;
    }

}

