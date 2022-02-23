package egor.core.errors;

import java.util.Objects;

public class GameErrorObject {

    private String id;

    public GameErrorObject(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GameErrorObject){
            GameErrorObject object = (GameErrorObject) obj;
            return Objects.equals(id, object.id);
        }
        return false;
    }

    @Override
    public String toString() {
        return GameErrorObject.class.getSimpleName() + "[" + id + "]";
    }
}
