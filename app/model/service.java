package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


public class service {

	public boolean checkUser(User u) {
		try {
			
			String textUri = "mongodb://Amanjot:mongolab123@ds037155.mongolab.com:37155/amandb";
			MongoClientURI muri = new MongoClientURI(textUri);
			MongoClient mc= new MongoClient(muri);
			System.out.println("reflected");
		//System.out.println(mongo.getDatabaseNames());
		  MongoDatabase db = mc.getDatabase("testdatabase");
		  System.out.println(db.getName());
		  // get a single collection
		  MongoCollection<Document> collection = db.getCollection("coll1");

		  insertDummyDocuments(collection);

		  System.out.println("1. Find first matched document");
		  FindIterable<Document> dbObject = collection.find();
		  System.out.println(dbObject);

		  System.out.println("\n1. Find all matched documents");
		  /*DBCursor cursor = collection.find();
		  while (cursor.hasNext()) {
			System.out.println(cursor.next());
		  }*/
		System.out.println(collection); // [datas, names, system.indexes, users]
		/*} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();*/
		} catch (MongoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}
	
	public static void insertDummyDocuments(MongoCollection collection) {

		collection.insertOne(new Document("a",1));


	}
	
	public boolean checkValidUser(User u) {
		
		try{  
			Class.forName("com.mysql.jdbc.Driver");  
			  
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/slotbooking","aman","password");  
			  
			//here sonoo is database name, root is username and password  
			  
			Statement stmt=con.createStatement();  
			  
			ResultSet rs=stmt.executeQuery("select * from users WHERE userid="+"'"+u.username+"'"+" and password="+"'"+u.password+"'");  
			  
			if(rs.next())
			  return true;
			con.close();  
			  
			}catch(Exception e){ 
				System.out.println(e);
			}  

		return false;
			
	}

	
public boolean insertTicket(Ticket t) {
		
		try{  
			Class.forName("com.mysql.jdbc.Driver");  
			  
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/slotbooking","aman","password");  
			PreparedStatement stmt=null;
			stmt=con.prepareStatement("insert into tickets(cutomer_info,comments,createdBy,assignedTo,status) values(?,?,?,?,?)");
			//here sonoo is database name, root is username and password  
			stmt.setString(1,t.customerInfo);
			stmt.setString(2,t.comment);
			stmt.setString(3,t.createdBy);
			stmt.setString(4,t.assignedTo);
			stmt.setString(5,t.status);
			boolean a=stmt.execute();
			con.close();  
			  
			}catch(Exception e){ 
				System.out.println(e);
			}  

		return false;
			
	}


public boolean updateTicket(Ticket t) {
	
	try{  
		Class.forName("com.mysql.jdbc.Driver");  
		  
		Connection con=DriverManager.getConnection(  
		"jdbc:mysql://localhost:3306/slotbooking","aman","password");  
		PreparedStatement stmt=null;
		//here sonoo is database name, root is username and password  
		stmt=con.prepareStatement("update tickets set cutomer_info=?,comments=?,createdBy=?,assignedTo=?,status=? where ticketId="+t.ticketId);
		stmt.setString(1,t.customerInfo);
		stmt.setString(2,t.comment);
		stmt.setString(3,t.createdBy);
		stmt.setString(4,t.assignedTo);
		stmt.setString(5,t.status);
		boolean a=stmt.execute();
		con.close();  
		  
		}catch(Exception e){ 
			System.out.println(e);
		}  

	return true;
		
}


public List<Ticket> getAllTickets() {
	
	List<Ticket> ticketList= new ArrayList<Ticket>();
	try{  
		Class.forName("com.mysql.jdbc.Driver");  
		  
		Connection con=DriverManager.getConnection(  
		"jdbc:mysql://localhost:3306/slotbooking","aman","password");  
		
		//here sonoo is database name, root is username and password  
		  
		Statement stmt=con.createStatement();  
		  
		ResultSet rs=stmt.executeQuery("select * from tickets" );  
		
		while(rs.next()){
			Ticket t= new Ticket();
			t.ticketId=rs.getString("ticketId");
			t.customerInfo=rs.getString("cutomer_info");
			t.comment=rs.getString("comments");
			t.createdBy=rs.getString("createdBy");
			t.assignedTo=rs.getString("assignedTo");
			t.status=rs.getString("status");
			
			ticketList.add(t);
		}
		}catch(Exception e){ 
			System.out.println(e);
		} 
	return ticketList;
}


public Ticket getTicketDetail(String tic_id) {
	Ticket t= new Ticket();
	try{  
		Class.forName("com.mysql.jdbc.Driver");  
		  
		Connection con=DriverManager.getConnection(  
		"jdbc:mysql://localhost:3306/slotbooking","aman","password");  
		
		//here sonoo is database name, root is username and password  
		  
		PreparedStatement stmt=con.prepareStatement("select * from tickets where ticketId=?");  
		  
	stmt.setString(1, tic_id);
		ResultSet rs=stmt.executeQuery();
		while(rs.next()){
			t.ticketId=tic_id;
			t.customerInfo=rs.getString("cutomer_info");
			t.comment=rs.getString("comments");
			System.out.println("t.comment "+t.comment);
			t.createdBy=rs.getString("createdBy");
			t.assignedTo=rs.getString("assignedTo");
			t.status=rs.getString("status");
		}
		}catch(Exception e){ 
			System.out.println(e);
		} 
	return t;
	
	
}
	
}
