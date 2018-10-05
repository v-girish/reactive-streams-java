package com.reactive.streams;

import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

/**
 * Created by girishv.
 */
public class EndSubscriber<T> implements Subscriber<T> {

    private Subscription subscription;


    @Override
    public void onSubscribe( final Subscription subscription ) {
        this.subscription = subscription;
        subscription.request( 1 );
    }

    @Override
    public void onNext( final T item ) {
        System.out.println("Got: " + item);
        subscription.request( 1 );
    }

    @Override
    public void onError( final Throwable throwable ) {
        throwable.printStackTrace();
    }

    @Override
    public void onComplete() {
        System.out.println("Completed");
    }
}
