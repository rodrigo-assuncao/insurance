package com.insurance.repository.mongo;

import com.insurance.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderMongoRepository extends MongoRepository<Order, UUID> {

}
