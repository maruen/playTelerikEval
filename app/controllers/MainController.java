package controllers;

import static play.data.Form.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import models.Computer;

import org.reflections.Reflections;
import org.yaml.snakeyaml.util.UriEncoder;

import play.Logger;
import play.data.Form;
import play.db.ebean.Model;
import play.libs.Json;
import play.libs.Jsonp;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;
import views.html.components.datatable1;
import views.html.components.datatable2;
import views.html.forms.createUser;
import views.html.forms.newRegisterForm;
import views.html.pages.page1;
import views.html.pages.page2;
import views.html.templates.template4;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.EbeanServerFactory;
import com.avaje.ebean.config.DataSourceConfig;
import com.avaje.ebean.config.ServerConfig;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;

import defaults.DBConfig;
import defaults.User;

public class MainController extends Controller {
    
	public static String EMPTY_STRING = "";
	
	public static Result index() {
		return ok(index.render(form(User.class)));
    }
	
	public static Result GO_HOME = redirect(
    	routes.MainController.page1()
    );
	
	public static Result login() {
		Form<User> form =  form(User.class).bindFromRequest();
		User user		=  User.authenticate(form.data().get("login"),form.data().get("password"));

		if (user != null) {
			session().put("user.id", user.id.toString() );
			return GO_HOME;
		} else {
			return redirect("/?errorMessage=" + UriEncoder.encode("Usuário ou senha inválidos"));
		}
	}
	
	public static Result createUser() {
		return ok(createUser.render(new User()));
	}
	
	public static Result template4() {
		return ok(template4.render());
	}
	
    public static Result newRegister() {
    	return ok(newRegisterForm.render());
    }
    
	public static Result datatable1() {
    	return ok(datatable1.render());
    }
	
	public static Result datatable2() {
    	return ok(datatable2.render( new Computer()));
    }
	
    public static Result page1() {
    	return ok(page1.render());    	
    }
    
    public static Result page2() {
    	return ok(page2.render());
    }
    
    public static Result computers(String callback) {
    	List<Computer> computers 	= Ebean.getServer("db1").find(Computer.class).findList();
        JsonNode  	   json 		= Json.toJson(computers);
        return ok(Jsonp.jsonp(callback, json));
    } 
    
    public static Result computersJson() {
        List<Computer> computers 	= Computer.find.all();
        JsonNode  	   json 		= Json.toJson(computers);
        return ok(Json.toJson(json));
    }
    
    public static Result computersPDF() {
    	 return ok(new java.io.File("/tmp/pdf.png"));
    }
    
    
    public static Result update(String callback, Computer computer) {
        computer.update();
        JsonNode  json = Json.toJson(computer);
        return ok(Jsonp.jsonp(callback, json));
    }
    
    public static Result delete(String callback, Computer computer) {
        Computer obj = Computer.find.byId(computer.id);
        obj.delete();
        JsonNode  json = Json.toJson(obj);
        return ok(Jsonp.jsonp(callback, json));
    }
    
    public static Result create(String callback, Computer computer) {
    	
    	Computer computer1 = (Computer) computer._ebean_createCopy();
    	Computer computer2 = (Computer) computer._ebean_createCopy();
    	Computer computer3 = (Computer) computer._ebean_createCopy();
    	
    	Ebean.getServer("db1").save(computer1);
    	Ebean.getServer("db2").save(computer2);
    	Ebean.getServer("db3").save(computer3);
        
        JsonNode  json = Json.toJson(computer);
        return ok(Jsonp.jsonp(callback, json));
    }
    
    
    public static Result getDatabasesByUser(Long userId) {

    	User user =	Ebean.find(User.class).fetch("dbconfigs").where().eq("id", userId).findUnique();
    	
    	List<DBConfig> 			dbconfigs 	=  user.dbconfigs;
    	HashMap<String,String> 	hash 		=  new HashMap<String,String>();
    	
    	List<HashMap<String,String>> ListOfhash = Lists.newArrayList();
    	for(DBConfig config : dbconfigs) {
    		
    		hash.put("id", 	 config.id.toString());
    		hash.put("name", config.dbname);
    		ListOfhash.add(hash);
    	}
    	
    	JsonNode 	  json 	=  Json.toJson(ListOfhash);
    	return ok(json);
    }
    
    
    
    public static Result registerDatabases() {
    	
    	List<String> result = new ArrayList<String>();
    	
		List<DBConfig> dbconfs = DBConfig.find.all();
		for (DBConfig dbconfig: dbconfs) {
			
			EbeanServer ebeanServer = null;
			try {
				ebeanServer = Ebean.getServer(dbconfig.dbname);
			} catch (Exception exp) {} {
			}
			
			if (ebeanServer != null) {
				continue;
			}
			
			DataSourceConfig dataSourceConfig = new DataSourceConfig();  
			dataSourceConfig.setDriver(dbconfig.dbdriver);  
			dataSourceConfig.setUsername(dbconfig.dbuser);  
			dataSourceConfig.setPassword(dbconfig.dbpassword);  
			dataSourceConfig.setUrl(dbconfig.dburl);
			
			ServerConfig config = new ServerConfig();
			config.setName(dbconfig.dbname);
			config.setDataSourceConfig(dataSourceConfig);
			config.setDdlGenerate(true);
			config.setDdlRun(true);
			Reflections reflections = new Reflections("models");
			Set<Class<? extends Model>> entities =  reflections.getSubTypesOf(Model.class);
			for (Class<? extends Model> entity: entities) {
				config.addClass(entity);
			}
			
			config.setDefaultServer(false);  
			config.setRegister(true);

			EbeanServerFactory.create(config);
			String msg= "Database [" + dbconfig.dbname +"] connected at " + dbconfig.dburl ; 
			result.add(msg);
			Logger.info(msg);
		}
    	if (result.isEmpty()) {
    		result.add("OK - but there was none new database to register");
    	}
    	
    	return ok(Json.toJson(result));
    }
        
}
            
