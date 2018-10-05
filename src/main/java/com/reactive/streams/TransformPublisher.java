package com.reactive.streams;

import java.util.concurrent.Executor;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Created by girishv.
 */
public class TransformPublisher<T,R> extends SubmissionPublisher<R> implements Flow.Processor<T,R> {

    private Function<T, R> function;
    private Flow.Subscription subscription;

    public TransformPublisher( final Function<T, R> function ) {
        super();
        this.function = function;
    }

    @Override
    public void onSubscribe( final Flow.Subscription subscription ) {
        this.subscription = subscription;
        subscription.request( 1 );
    }

    @Override
    public void onNext( final T item ) {
        System.out.println("Transforming:" + item);
        submit( function.apply( item ) );
        subscription.request( 1 );
    }

    @Override
    public void onError( final Throwable throwable ) {
        throwable.printStackTrace();
    }

    @Override
    public void onComplete() {
        System.out.println("Completed");
        close();
    }
}
