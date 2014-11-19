package controllers;

import java.util.List;

import models.Computer;
import models.User;
import play.libs.Json;
import play.libs.Jsonp;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.components.datatable1;
import views.html.components.datatable2;
import views.html.forms.createUser;
import views.html.forms.newRegisterForm;
import views.html.pages.page1;
import views.html.pages.page2;

import com.fasterxml.jackson.databind.JsonNode;

public class MainController extends Controller {
    
	public static Result index() {
    	return GO_HOME;
    }
	
	public static Result GO_HOME = redirect(
    	routes.MainController.page1()
    );
	
	public static Result createUser() {
		return ok(createUser.render(new User()));
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
        List<Computer> computers 	= Computer.find.all();
        JsonNode  	   json 		= Json.toJson(computers);
        return ok(Jsonp.jsonp(callback, json));
    } 
    
    public static Result computersJson() {
        List<Computer> computers 	= Computer.find.all();
        JsonNode  	   json 		= Json.toJson(computers);
        return ok(Json.toJson(json));
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
        computer.save();
        JsonNode  json = Json.toJson(computer);
        return ok(Jsonp.jsonp(callback, json));
    }
    
        
}
            
