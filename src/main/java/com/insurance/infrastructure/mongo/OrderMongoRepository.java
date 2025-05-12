package com.insurance.infrastructure.mongo;

import com.insurance.domain.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderMongoRepository extends MongoRepository<Order, UUID> {

}
