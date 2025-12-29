package com.fintrack;

import com.fintrack.util.Config;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class ConnectionTest {
    public static void main(String[] args) {
        System.out.println("Starting Connection Test...");
        try {
            String uri = Config.get("MONGODB_URI");
            System.out.println("URI: " + uri);

            if (uri == null || uri.isEmpty()) {
                System.out.println("ERROR: MONGODB_URI is empty/null");
                return;
            }

            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(new ConnectionString(uri))
                    .build();

            try (MongoClient client = MongoClients.create(settings)) {
                MongoDatabase db = client.getDatabase(Config.get("DB_NAME", "fintrack_db"));
                System.out.println("Attempting 'ping' command...");
                db.runCommand(new Document("ping", 1));
                System.out.println("SUCCESS: Connected to MongoDB!");
            }
        } catch (Exception e) {
            System.out.println("FAILURE: Connection threw exception:");
            e.printStackTrace();
        }
    }
}
