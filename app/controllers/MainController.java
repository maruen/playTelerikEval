package controllers;

import java.util.List;

import models.Computer;
import play.libs.Json;
import play.libs.Jsonp;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

import com.fasterxml.jackson.databind.JsonNode;


public class MainController extends Controller {
    
    
	public static Result GO_HOME = redirect(
    		routes.MainController.page1(0, "name", "asc", "")
    );
	
    public static Result index() {
    	return GO_HOME;
    }
    
    public static Result newRegister() {
    	return ok(newRegisterForm.render());
    }
    
	public static Result datatable1(int page,String sortBy,	String order,String filter) {
    	return ok(datatable1.render(Computer.page(page,10, sortBy, order, filter),sortBy,order,filter));
    }
    
    public static Result page1(int page, String sortBy, String order, String filter) {
    	return ok(page1.render(Computer.page(page,10,sortBy,order,filter),sortBy,order,filter));    	
    }
    
    public static Result page2(int page,String sortBy,String order, String filter) {
    	return ok(page2.render(Computer.page(page,10,sortBy,order,filter),sortBy,order,filter));
    }
    
    public static Result telerikGrid(int page,String sortBy,String order,String filter) {
        
    	return ok(telerikGrid.render(Computer.page(page,10,sortBy, order,filter),sortBy,order,filter));
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
            
