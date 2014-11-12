package controllers;

import models.Computer;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.datatable;


public class TelerikController extends Controller {
    
    public static Result datatable(int page, String sortBy, String order, String filter) {
        return ok(datatable.render(Computer.page(page, 10, sortBy, order, filter),sortBy, order, filter));
    }
        
}
            
