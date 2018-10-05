package com.reactive.streams;

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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * Created by girishv.
 */
public class SpringReactiveMongoTest {

    private static MongodProcess mongod;
    private static final String HOST = "localhost";
    private static final Integer PORT = 12345;
    private static final String DATABASE = "mydb";
    private static final String COLLECTION = "test";
    private SpringReactiveMongo springReactiveMongo;

    @BeforeClass
    public static void startMongo() throws IOException {
        MongodStarter starter = MongodStarter.getDefaultInstance();
        IMongodConfig mongodConfig = new MongodConfigBuilder()
                .version(Version.Main.PRODUCTION)
                .net(new Net(HOST, PORT, Network.localhostIsIPv6()))
                .build();

        MongodExecutable mongodExecutable = starter.prepare(mongodConfig);
        mongod = mongodExecutable.start();
    }

    @AfterClass
    public static void stopMongo() {
        mongod.stop();
    }

    @Before
    public void setUp() throws Exception {
        this.springReactiveMongo = new SpringReactiveMongo( HOST, PORT, DATABASE, COLLECTION );
    }

    @Test
    public void test_insert_single_document() throws InterruptedException {
        Account account = new Account( "1", "owner1", 200.0 );

        springReactiveMongo.save( account ).block();

        final List<Account> accounts = springReactiveMongo.findAll()
                .toStream()
                .collect( Collectors.toList() );
        System.out.println(accounts);
    }
}