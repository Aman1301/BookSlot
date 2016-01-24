package DBUtil;

import play.api.Play;
import scala.collection.generic.Sizing;

import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import com.typesafe.config.ConfigFactory;

public class DBConnection {
	private static DBConnection singleInstance;
	
	private static MongoDatabase db =null;
	private String mongoHost=null;
	private String mongoPort=null;
	private String mongoUsername=null;
	private String mongoPassword=null;
	private String mongoDB=null;
	private DBConnection() {
		 mongoHost=ConfigFactory.load().getString("mongo.host");
		 mongoPort=ConfigFactory.load().getString("mongo.port");
		 mongoUsername=ConfigFactory.load().getString("mongo.username");
		 mongoPassword=ConfigFactory.load().getString("mongo.password");
		 mongoDB=ConfigFactory.load().getString("mongo.database");
	}
	public static  DBConnection getInstance(){
		
		if(singleInstance==null){
		singleInstance=new DBConnection();
		}
		return singleInstance;
	}

	public  MongoDatabase getDBConnection(){
		
		if(db==null){
		String textUri = "mongodb://"+mongoUsername+":"+mongoPassword+"@"+mongoHost+":"+mongoPort+"/"+mongoDB;
		MongoClientURI muri = new MongoClientURI(textUri);
		MongoClient mc= new MongoClient(muri);
		 db = mc.getDatabase("amandb");
		}
		return db;
	}
}
