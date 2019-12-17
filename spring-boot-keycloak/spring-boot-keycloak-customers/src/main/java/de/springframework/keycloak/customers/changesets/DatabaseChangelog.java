package de.springframework.keycloak.customers.changesets;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;

@ChangeLog
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "init-collections", author = "flippii")
    public void initCollections(MongoDatabase db) {
        db.createCollection("owners");
        db.createCollection("pets");
        db.createCollection("pet_types");
    }

}
