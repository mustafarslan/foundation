package com.foundation.core.repository;

import com.foundation.core.entity.GreetEsEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface GreetEsRepository extends ElasticsearchRepository<GreetEsEntity, String> {
    List<GreetEsEntity> findAllByName(String name);
}
