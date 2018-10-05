package com.reactive.streams;

import org.junit.Test;

import java.util.List;
import java.util.concurrent.SubmissionPublisher;

/**
 * Created by girishv.
 */
public class TransformPublisherTest {

    @Test
    public void should_transform_and_consume_items() throws InterruptedException {
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>(  );
        TransformPublisher<String, Integer> transformPublisher = new TransformPublisher<>( String::length );

        List<String> items = List.of( "Alex", "Bob", "Evan", "Judice" );

        publisher.subscribe( transformPublisher );

        EndSubscriber<Integer> endSubscriber = new EndSubscriber<>();
        transformPublisher.subscribe( endSubscriber );

        items.forEach( publisher::submit );

        publisher.close();
        Thread.sleep( 1000 );
    }
}