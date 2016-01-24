package controllers;

import java.util.ArrayList;
import java.util.List;

import model.Ticket;
import model.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import Service.service;
import views.html.*;
import play.*;
public class Application extends Controller {

	private  service s= null;
	
	public service getService(){
		if(s==null){
			s=  new service();
		}
		return s;
	}
	
    public Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public Result sayHello() {
    	
    	User  u= Form.form(User.class).bindFromRequest().get();
    	boolean status=getService().checkValidUser(u);
    	System.out.println(status);
    	if(status){
        return ok(welcome.render(u.username));
    	}
    	else {
    	return ok(index.render("Invalid Username/Password"));
		}
    }
    
    public Result newTicketPage() {
    	Form<Ticket> ft= Form.form(Ticket.class).fill(new Ticket());
        return ok(raiseTickets.render(null,ft));
    }
    
    public Result displayTickets() {
    	List<Ticket> ticketsList= getService().getAllTickets();
    	List<Ticket> ticketsListNew=new ArrayList<Ticket>();
    	List<Ticket> ticketsListOpen=new ArrayList<Ticket>();
    	List<Ticket> ticketsListClosed=new ArrayList<Ticket>();
    	for(Ticket tck:ticketsList){
    		if(tck.status.equals("New")){
    			ticketsListNew.add(tck);
    		}else if (tck.status.equals("Open")) {
    			ticketsListOpen.add(tck);
			}else if (tck.status.equals("Closed")) {
				ticketsListClosed.add(tck);
			}
    		
    	}
        return ok(viewTicket.render(ticketsListNew,ticketsListOpen,ticketsListClosed));
    }
    
    public Result newTicket() {
    	Ticket  t= Form.form(Ticket.class).bindFromRequest().get();
    	boolean status = getService().insertTicket(t);
    	System.out.println(t.ticketId);
    	System.out.println(t.customerInfo);
    	System.out.println(t.createdBy);
    	System.out.println("t.ticketId "+t.ticketId);
    	Form<Ticket> ft= Form.form(Ticket.class).fill(new Ticket());
        return ok(raiseTickets.render(null,ft));
    }
    
    public Result editTicketStatus(String ticket_id) {
    	Ticket tic=getService().getTicketDetail(ticket_id);
    	Form<Ticket> ft= Form.form(Ticket.class).fill(tic);
    	System.out.println(tic.comment);
        return ok(updateTicket.render(ticket_id,ft));
    }
    public Result updateTicket(String ticket_id) {
    	
    	Ticket  t= Form.form(Ticket.class).bindFromRequest().get();
    	
    	boolean tics=getService().updateTicket(t);
    	List<Ticket> ticketsList= getService().getAllTickets();
    	List<Ticket> ticketsListNew=new ArrayList<Ticket>();
    	List<Ticket> ticketsListOpen=new ArrayList<Ticket>();
    	List<Ticket> ticketsListClosed=new ArrayList<Ticket>();
    	for(Ticket tck:ticketsList){
    		if(tck.status.equals("New")){
    			ticketsListNew.add(tck);
    		}else if (tck.status.equals("Open")) {
    			ticketsListOpen.add(tck);
			}else if (tck.status.equals("Closed")) {
				ticketsListClosed.add(tck);
			}
    		
    	}
    	return ok(viewTicket.render(ticketsListNew,ticketsListOpen,ticketsListClosed));
    }
}
