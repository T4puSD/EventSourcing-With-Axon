package com.example.demoeventsourcing.repository;

import com.example.demoeventsourcing.domain.AccountQueryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<AccountQueryEntity,String> {

}
