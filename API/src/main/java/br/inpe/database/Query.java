package br.inpe.database;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.bson.Document;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

public class Query {

	
//	
//	public static void findOne(String key, String value){
//		MongoCollection<Document> collection = Mongo.getInstance().getDataBase().getCollection("inpe");
//		Document findI = collection.find().filter(Filters.eq(key,value)).first();
//		System.out.println(findI);
//	}
//	
	public static List<Object> findOne(int page){
		
		JSONArray a = new JSONArray();

		try (MongoCursor<Document> collection = Mongo.getInstance().getDataBase().getCollection("inpe").find().projection(new Document("_id",1)).limit(10).skip(0).iterator()) {
		 //http://stackoverflow.com/questions/25589113/how-to-select-a-single-field-in-mongodb
		
			while (collection.hasNext()) {
				//ss.append(collection.next());
		      
				a.put(collection.next().toJson());
		      
		    }
		}
		
		return a.toList();
//		Object json = mapper.readValue(collection.find().limit(10).skip(page).first().toJson(), Object.class);
//		return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
				//collection.find().filter(Filters.eq(key,value)).first();
	}
	
}
