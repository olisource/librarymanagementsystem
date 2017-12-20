package com.librarymanagementsystem.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.librarymanagementsystem.demo.entity.ConfigParameter;


@Repository
public interface ConfigParameterRepository extends CrudRepository<ConfigParameter, Long> {
    @Query("select c from ConfigParameter c where c.level = :level")
    public List<ConfigParameter> findByLevel(@Param("level") String level);

    @Query("select c from ConfigParameter c where c.level = :level and c.name = :name")
    public List<ConfigParameter> findByLevelAndName(@Param("level") String level, @Param("name") String name);    
}