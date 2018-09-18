package com.cxx.generic.di.repository;
import java.io.Serializable;
public abstract class BaseRepository<M extends Serializable> {
    public void save(M m) {
        System.out.println("=========repository save:"+m);
    }
}