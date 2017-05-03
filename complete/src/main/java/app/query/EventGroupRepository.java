package app.query;

import app.model.EventGroup;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

/**
 * Created by jaydatta on 4/17/17.
 */
public interface EventGroupRepository extends CrudRepository<EventGroup, Long> {

    ArrayList<EventGroup> findBysenderName(String senderName);

}
