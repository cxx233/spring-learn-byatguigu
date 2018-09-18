package com.cxx.generic.di.service;
import com.cxx.generic.di.repository.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.Serializable;
public abstract class BaseService<M extends Serializable> {
    @Autowired
    protected BaseRepository<M> repository;
    public void add() {
        System.out.println("add....");
        System.out.println(repository);
    }
}
