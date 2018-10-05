package com.reactive.streams;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.bson.Document;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by girishv.
 */
public class SpringReactiveMongo {

    private ReactiveMongoTemplate mongoTemplate;

    public SpringReactiveMongo(String host, Integer port, String databaseName, String collectionName) {
        MongoClient mongoClient = MongoClients.create(String.format(  "mongodb://%s:%d",host,port));
        this.mongoTemplate = new ReactiveMongoTemplate( mongoClient, databaseName );
    }

    public Mono<Account> save( Account document ) {
        return mongoTemplate.save(document);
    }

    public Mono<Account> findById(String id) {
        return mongoTemplate.findById( id, Account.class );
    }

    public Flux<Account> findAll() {
        return mongoTemplate.findAll(Account.class);
    }

}
