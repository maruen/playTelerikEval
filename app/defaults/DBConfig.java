package defaults;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import play.db.ebean.Model;

@Entity
public class DBConfig extends Model {
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Long id;
	
	public String dbname;
	public String dbdriver;
	public String dburl;
	public String dbuser;
	public String dbpassword;
	
	@ManyToMany(mappedBy="dbconfigs")
    public List<User> users;
	
	public static Model.Finder<Long,DBConfig> find = new Model.Finder<Long, DBConfig>(Long.class, DBConfig.class);

}
