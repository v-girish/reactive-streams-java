package com.reactive.streams;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by girishv.
 */
@Document
public class Account {

    @Id
    private String id;
    private String owner;
    private Double value;

    public Account() {
    }

    public Account( final String id, final String owner, final Double value ) {
        this.id = id;
        this.owner = owner;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId( final String id ) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner( final String owner ) {
        this.owner = owner;
    }

    public Double getValue() {
        return value;
    }

    public void setValue( final Double value ) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "{" +
                "id:\"" + id + "\"" +
                ", owner:\"" + owner + "\"" +
                ", value:" + value +
                '}';
    }
}
