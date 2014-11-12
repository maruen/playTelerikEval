package controllers;

import static play.data.Form.form;
import models.Computer;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.createForm;
import views.html.editForm;
import views.html.list;


public class ComputersController extends Controller {
    
    public static Result GO_HOME = redirect(
    		routes.ComputersController.list(0, "name", "asc", "")
    );
	
    public static Result index() {
    	return GO_HOME;
    }

    public static Result list(int page, String sortBy, String order, String filter) {
        return ok(list.render(Computer.page(page, 10, sortBy, order, filter),sortBy, order, filter));
    }
    
    
    public static Result edit(Long id) {
        Form<Computer> computerForm = form(Computer.class).fill(Computer.find.byId(id));
        return ok(editForm.render(id, computerForm));
    }
    
    
    public static Result update(Long id) {
        Form<Computer> computerForm = form(Computer.class).bindFromRequest();
        if(computerForm.hasErrors()) {
            return badRequest(editForm.render(id, computerForm));
        }
        computerForm.get().update(id);
        flash("success", "Computer " + computerForm.get().name + " has been updated");
        return GO_HOME;
    }
    
    
    public static Result create() {
        Form<Computer> computerForm = form(Computer.class);
        return ok(createForm.render(computerForm));
    }
    
    
    public static Result save() {
        Form<Computer> computerForm = form(Computer.class).bindFromRequest();
        if(computerForm.hasErrors()) {
            return badRequest(createForm.render(computerForm));
        }
        computerForm.get().save();
        flash("success", "Computer " + computerForm.get().name + " has been created");
        return GO_HOME;
    }
    
    
    public static Result delete(Long id) {
        Computer.find.ref(id).delete();
        flash("success", "Computer has been deleted");
        return GO_HOME;
    }
    
}
            
