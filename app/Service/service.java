package Service;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.Ticket;
import model.User;

import org.bson.Document;

import DBUtil.DBConnection;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;


public class service {

	public boolean checkValidUser(User u) {
		try {

			MongoDatabase db = DBConnection.getInstance().getDBConnection();
			System.out.println(db.getName());
			MongoCollection<Document> collection = db
					.getCollection("userDetails");

			MongoCursor<Document> cursor = collection.find(
					new Document("userid", u.username).append("pass",
							u.password)).iterator();

			while (cursor.hasNext()) {
				return true;
				// Process your values and go to the next record
			}
		} catch (MongoException e) {
			e.printStackTrace();
		}
		return false;

	}

	// public static boolean insertMongo() {
	// try {
	//
	// MongoDatabase db = DBConnection.getDBConnection();
	// System.out.println(db.getName());
	// // get a single collection
	// MongoCollection<Document> collection = db.getCollection("userDetails");
	//
	// // insertDummyDocuments(collection);
	//
	// System.out.println("1. Find first matched document");
	// MongoCursor<Document> cursor = collection.find(new Document("userid",
	// "user2" ).append("pass","pass2")).iterator();
	// Document doc;
	//
	// while(cursor.hasNext()){
	// doc = cursor.next();
	// System.out.println("In");
	// String userid = doc.get("userid").toString();
	// String pass = doc.get("pass").toString();
	// System.out.println(userid);
	// System.out.println(pass);
	// // Process your values and go to the next record
	// }
	//
	//
	// System.out.println("\n1. Find all matched documents");
	//
	// System.out.println(collection); // [datas, names, system.indexes, users]
	// /*} catch (UnknownHostException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();*/
	// } catch (MongoException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return false;
	//
	// }
	//
	// public static void main(String[] args) {
	// insertMongo();
	// }
	//
	// public static void insertDummyDocuments(MongoCollection collection) {
	//
	// Document doc = new Document("_id",2).append("userid",
	// "user1").append("pass", "pass1");
	// collection.insertOne(doc);
	//
	// }

//	public boolean checkValidUser(User u) {
//
//		try {
//			Class.forName("com.mysql.jdbc.Driver");
//
//			Connection con = DriverManager.getConnection(
//					"jdbc:mysql://localhost:3306/slotbooking", "aman",
//					"password");
//
//			// here sonoo is database name, root is username and password
//
//			Statement stmt = con.createStatement();
//
//			ResultSet rs = stmt
//					.executeQuery("select * from users WHERE userid=" + "'"
//							+ u.username + "'" + " and password=" + "'"
//							+ u.password + "'");
//
//			if (rs.next())
//				return true;
//			con.close();
//
//		} catch (Exception e) {
//			System.out.println(e);
//		}
//
//		return false;
//
//	}

	public boolean insertTicketMySQlx(Ticket t) {

		try {
			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/slotbooking", "aman",
					"password");
			PreparedStatement stmt = null;
			stmt = con
					.prepareStatement("insert into tickets(cutomer_info,comments,createdBy,assignedTo,status) values(?,?,?,?,?)");
			// here sonoo is database name, root is username and password
			stmt.setString(1, t.customerInfo);
			stmt.setString(2, t.comment);
			stmt.setString(3, t.createdBy);
			stmt.setString(4, t.assignedTo);
			stmt.setString(5, t.status);
			boolean a = stmt.execute();
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}

		return false;

	}

	public boolean insertTicket(Ticket t) {

		try {
			MongoDatabase db = DBConnection.getInstance().getDBConnection();
			System.out.println(db.getName());
			MongoCollection<Document> collection = db
					.getCollection("ticketDetails");
			Random generator = new Random();
			int i = generator.nextInt(10000);
			Document doc = new Document("ticketId", i)
					.append("customerInfo", t.customerInfo)
					.append("comment", t.comment)
					.append("assignedTo", t.assignedTo)
					.append("createdBy", t.createdBy)
					.append("status", t.status);

			collection.insertOne(doc);
			
		} catch (Exception e) {
			System.out.println(e);
		}

		return true;

	}

	public boolean updateTicket(Ticket t) {

		try {
			MongoDatabase db = DBConnection.getInstance().getDBConnection();
			System.out.println(db.getName());
			MongoCollection<Document> collection = db
					.getCollection("ticketDetails");
			System.out.println(t.comment);
			Long i=collection.updateOne(
					new Document("ticketId", Integer.parseInt(t.ticketId)),
					new Document("$set",new Document("comment", t.comment)
							.append("assignedTo", t.assignedTo)
							.append("createdBy", t.createdBy)
							.append("status", t.status))).getModifiedCount();

			System.out.println(i);
		} catch (Exception e) {
			System.out.println(e);
		}

		return true;

	}

	public List<Ticket> getAllTickets() {

		List<Ticket> ticketList = new ArrayList<Ticket>();
		try {
			MongoDatabase db = DBConnection.getInstance().getDBConnection();
			System.out.println(db.getName());
			MongoCollection<Document> collection = db
					.getCollection("ticketDetails");

			MongoCursor<Document> cursor = collection.find().iterator();
			Document doc;
			while (cursor.hasNext()) {
				doc=cursor.next();
				Ticket t= new Ticket();
				t.ticketId=doc.get("ticketId").toString();
				t.customerInfo=doc.get("customerInfo").toString();
				t.comment=doc.get("comment").toString();
				t.assignedTo=doc.get("assignedTo").toString();
				t.createdBy=doc.get("createdBy").toString();
				t.status=doc.get("status").toString();
				ticketList.add(t);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return ticketList;
	}

	public Ticket getTicketDetail(String tic_id) {
		System.out.println(tic_id);
		Ticket t = new Ticket();
		try {
			MongoDatabase db = DBConnection.getInstance().getDBConnection();
			System.out.println(db.getName());
			MongoCollection<Document> collection = db
					.getCollection("ticketDetails");

			MongoCursor<Document> cursor = collection.find(new Document("ticketId",Integer.parseInt(tic_id))).iterator();
			Document doc;
			System.out.println("jbjbjknb");
			while (cursor.hasNext()) {
				System.out.println("jbjbjknb");
				doc=cursor.next();
				t.ticketId=doc.get("ticketId").toString();
				t.customerInfo=doc.get("customerInfo").toString();
				t.comment=doc.get("comment").toString();
				t.assignedTo=doc.get("assignedTo").toString();
				t.createdBy=doc.get("createdBy").toString();
				t.status=doc.get("status").toString();
				System.out.println(t.createdBy);
		}
		}catch (Exception e) {
			System.out.println(e);
		}
		return t;

	}

}
