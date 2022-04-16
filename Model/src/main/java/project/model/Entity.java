package project.model;

import java.io.Serial;
import java.io.Serializable;

/**
 * Entity class
 *
 * @param <ID> each Entity needs to have an ID
 */
public class Entity<ID> implements Serializable {

    @Serial
    private static final long serialVersionUID = 7331115341259248461L;
    private ID id;

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

}