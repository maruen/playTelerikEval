package defaults;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import play.data.validation.Constraints;
import play.db.ebean.Model;

@Entity
public class User extends Model {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Long id;
    
    @Constraints.Required
    public String name;
    
    @Constraints.Required
    public String login;
    
    @Constraints.Required
    public String password;
    
    @Constraints.Required
    public String email;
    
    @Constraints.Required
    public String role;
    
    @Constraints.Required
    public Boolean enabled;
    
    @ManyToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    public List<DBConfig> dbconfigs;
    
    
    public static Model.Finder<Long,User> find = new Model.Finder<Long,User>(Long.class, User.class);
    
    public static User authenticate(String login, String password) {
        return find.where().eq("login", login).eq("password", password).findUnique();
    }

    

}

