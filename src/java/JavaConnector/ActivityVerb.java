package JavaConnector;

/**
 *
 * @author Unity Developers
 */

/* TODO vres ena rima gia to fellow */
public enum ActivityVerb  {
    COMMENT("COMMENT"),
    CREATE("CREATE"),
    DELETE("DELETE"),
    REMOVE("REMOVE"),
    POST("POST"),
    JOIN("JOIN"),
    FOLLOW("FOLLOW"),
    FELLOW("FELLOW"),
    ADD("ADD"),
    TAG("TAGG"),
    INTERMEDIATE("INTERMEDIATE");

    private String verb;

    private ActivityVerb(String verb) {
        this.verb = verb;
    }
}
