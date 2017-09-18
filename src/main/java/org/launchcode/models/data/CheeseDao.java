package org.launchcode.models.data;

import org.launchcode.models.Cheese;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by LaunchCode
 */
// these annationtions made a DATA ACCESS OBJECT aka cheese DOA
    //we gave it the reposityory and trasactional annotation points to cheese controller
    //Spring created an implementation for us using some spring magic and dependency injection
@Repository
@Transactional
public interface CheeseDao extends CrudRepository<Cheese, Integer> {
}
