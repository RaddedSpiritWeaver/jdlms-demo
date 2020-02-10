package server;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.time.LocalTime;

public class DatabaseHandler {

    private MongoClient client;
    private MongoDatabase dlmsDatabase;
    private MongoCollection records;

    
    DatabaseHandler(String mongoAddress, String dbName, String colName){
        MongoClientURI uri = new MongoClientURI(mongoAddress);
//        MongoClientOptions options = MongoClientOptions.builder().serverSelectionTimeout(10000).connectTimeout(10000).build();
        this.client = new MongoClient(uri);
        this.dlmsDatabase = client.getDatabase(dbName);
        this.records = this.dlmsDatabase.getCollection(colName);
    }

    public void saveRecord(PhysicalDeviceConf phyConf, int logicalId, String obis, String voltage, String time){
        Document record = new Document()
                .append("PhysicalAddress", phyConf.getIpAddress().toString())
                .append("PhysicalPort", Integer.toString(phyConf.getPort()))
                .append("LogicalId", Integer.toString(logicalId))
                .append("OBIS", obis)
                .append("time", time)
                .append("voltage", voltage);
        this.records.insertOne(record);
    }


}
