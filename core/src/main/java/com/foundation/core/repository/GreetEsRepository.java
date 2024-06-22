package com.foundation.core.repository;

import com.foundation.core.entity.GreetEsEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GreetEsRepository extends ElasticsearchRepository<GreetEsEntity, String> {
    List<GreetEsEntity> findAllByName(String name);
}
