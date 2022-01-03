package com.bermuda.bermudacase.dataAccess;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bermuda.bermudacase.entities.CheckedGuid;

@Repository
public interface ICheckedGuidRepository extends MongoRepository<CheckedGuid, String> {

}
