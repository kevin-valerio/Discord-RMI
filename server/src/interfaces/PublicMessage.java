package interfaces;

import java.io.Serializable;

public class PublicMessage implements Serializable {
    private String pseudo;
    private String message;

    public PublicMessage(String pseudo, String message) {
        this.pseudo = pseudo;
        this.message = message;
    }

    public String getPseudo() {
        return pseudo;
    }

    public String getMessage() {
        return message;
    }
}