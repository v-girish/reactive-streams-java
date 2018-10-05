package com.reactive.streams;

import com.mongodb.reactivestreams.client.Success;
import com.reactive.streams.SubscriberHelpers.OperationSubscriber;
import com.reactive.streams.SubscriberHelpers.PrintDocumentSubscriber;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.bson.Document;
import org.junit.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by girishv.
 */
public class ReactiveMongoTest {

    private static MongodProcess mongod;
    private static final String HOST = "localhost";
    private static final Integer PORT = 12345;
    private static final String DATABASE = "mydb";
    private static final String COLLECTION = "test";
    private ReactiveMongo reactiveMongo;

    @BeforeClass
    public static void startMongo() throws IOException {
        MongodStarter starter = MongodStarter.getDefaultInstance();
        IMongodConfig mongodConfig = new MongodConfigBuilder()
                .version(Version.Main.PRODUCTION)
                .net(new Net(HOST, PORT, Network.localhostIsIPv6()))
                .build();

        MongodExecutable mongodExecutable = null;
        mongodExecutable = starter.prepare(mongodConfig);
        mongod = mongodExecutable.start();
    }

    @AfterClass
    public static void stopMongo() {
        mongod.stop();
    }

    @Before
    public void setUp() throws Exception {
        this.reactiveMongo = new ReactiveMongo( HOST, PORT, DATABASE, COLLECTION );
    }

    @After
    public void tearDown() throws Exception {
        this.reactiveMongo.disconnect();
    }

    @Test
    public void test_insert_single_document() throws Throwable {
        Document document = new Document( "name", "MongoDB" )
                .append( "type", "database" )
                .append("count", 1)
                .append("info", new Document("x", 203).append("y", 102));

        final OperationSubscriber<Success> operationSubscriber = new OperationSubscriber<>();
        reactiveMongo.insertDocument( document )
                .subscribe( operationSubscriber );
        operationSubscriber.await();

        final PrintDocumentSubscriber printDocumentSubscriber = new PrintDocumentSubscriber();
        reactiveMongo.findFirst().subscribe( printDocumentSubscriber );

        printDocumentSubscriber.await();
    }

    @Test
    public void test_insert_multiple_documents() throws Throwable {
        List<Document> documents = new ArrayList<Document>();
        for (int i = 0; i < 100; i++) {
            documents.add(new Document("i", i));
        }

        final OperationSubscriber<Success> operationSubscriber = new OperationSubscriber<>();
        reactiveMongo.insertMany( documents )
                .subscribe( operationSubscriber );
        operationSubscriber.await();

        final PrintDocumentSubscriber printDocumentSubscriber = new PrintDocumentSubscriber();
        reactiveMongo.findAll().subscribe( printDocumentSubscriber );

        printDocumentSubscriber.await();
    }


}
