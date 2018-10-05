package com.reactive.streams;

import com.mongodb.reactivestreams.client.*;
import org.bson.Document;
import org.reactivestreams.Publisher;

import java.util.List;

/**
 * Created by girishv.
 */
public class ReactiveMongo {

    private final MongoCollection<Document> collection;
    private final MongoClient mongoClient;

    public ReactiveMongo( String host, Integer port, String databaseName, String collectionName ) {
        MongoClient mongoClient = MongoClients.create(String.format(  "mongodb://%s:%d",host,port));
        final MongoDatabase database = mongoClient.getDatabase( databaseName );;
        this.collection = database.getCollection( collectionName );
        this.mongoClient = mongoClient;
    }

    public Publisher<Success> insertDocument( Document document ) {
        return collection.insertOne( document );
    }

    public Publisher<Success> insertMany( List<Document> documents ) {
        return collection.insertMany( documents );
    }

    public Publisher<Document> findFirst( ) {
        return collection.find().first();
    }

    public FindPublisher<Document> findAll() {
        return collection.find();
    }

    public void disconnect() {
        this.mongoClient.close();
    }

}
