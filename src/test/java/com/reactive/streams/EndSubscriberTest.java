package com.reactive.streams;

import org.junit.Test;

import java.util.List;
import java.util.concurrent.SubmissionPublisher;

/**
 * Created by girishv.
 */
public class EndSubscriberTest {

    @Test
    public void should_consume_events() throws InterruptedException {
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>( );
        EndSubscriber<String> endSubscriber1 = new EndSubscriber<>();
        EndSubscriber<String> endSubscriber2 = new EndSubscriber<>();

        publisher.subscribe( endSubscriber1 );
        publisher.subscribe( endSubscriber2 );

        List<String> items = List.of( "1", "2", "3", "4", "5" );
        items.forEach( publisher::submit );

        publisher.close();

        Thread.sleep( 1000 );
    }
}