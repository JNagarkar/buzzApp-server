package app.query;

import app.util.UserPersonalization;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

/**
 * Created by jaydatta on 4/20/17.
 */
public interface UserPersonalizationRepository extends CrudRepository<UserPersonalization, Long> {

    ArrayList<UserPersonalization> findByUserID(String senderID);

}
