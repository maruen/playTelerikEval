package models;

import java.util.Date;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.data.format.Formats;
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
    public Long id;
    
    @Constraints.Required
    public String name;
    
    @Formats.DateTime(pattern="yyyy-MM-dd")
    public Date introduced;
    
    @Formats.DateTime(pattern="yyyy-MM-dd")
    public Date discontinued;
    
    @ManyToOne
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
		
		JsonNode jsonNode = Json.parse(data.get("models")[0]);
		Computer computer = new Computer();
		computer.id 	  = jsonNode.elements().next().get("id").asLong();
		computer.name	  = jsonNode.elements().next().get("name").asText();
		
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

