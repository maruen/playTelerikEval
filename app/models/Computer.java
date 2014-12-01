package models;

import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import play.data.validation.Constraints;
import play.db.ebean.Model;
import play.libs.F.Option;
import play.libs.Json;
import play.mvc.QueryStringBindable;

import com.avaje.ebean.Page;
import com.fasterxml.jackson.databind.JsonNode;

@Entity
public class Computer extends Model implements QueryStringBindable<Computer> {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Long id;
    
    @Constraints.Required
    public String name;
    
    public String introduced;
    
    public String discontinued;
    
    public Company company;
    
    public static Finder<Long,Computer> find = new Finder<Long,Computer>(Long.class, Computer.class); 
    
    public static Page<Computer> page(int page, int pageSize, String sortBy, String order, String filter) {
        return 
            find.where()
                .ilike("name", "%" + filter + "%")
                .orderBy(sortBy + " " + order)
                .fetch("company")
                .findPagingList(pageSize)
                .setFetchAhead(false)
                .getPage(page);
    }

	@Override
	public Option<Computer> bind(String key, Map<String, String[]> data) {
		
		
		JsonNode jsonNode 		= Json.parse(data.get("models")[0]);
		Computer computer 		= new Computer();
		computer.id 	  		= jsonNode.elements().next().get("id").asLong();
		computer.name	  		= jsonNode.elements().next().get("name").asText();
		computer.introduced 	= jsonNode.elements().next().get("introduced").asText();
		computer.discontinued 	= jsonNode.elements().next().get("discontinued").asText();
		
		return Option.Some(computer);
		
	}

	@Override
	public String javascriptUnbind() {
		return null;
	}

	@Override
	public String unbind(String arg0) {
		return null;
	}
    
    
}

