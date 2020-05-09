package interfaces;

import java.io.Serializable;

public class PrivateMessage implements Serializable {
    private String pseudo;
    private String message;
    private ClientPrivateMessageInterface pmInterface;

    public PrivateMessage(String pseudo, String message, ClientPrivateMessageInterface pmInterface) {
        this.pseudo = pseudo;
        this.message = message;
        this.pmInterface = pmInterface;
    }

    public String getPseudo() {
        return pseudo;
    }

    public String getMessage() {
        return message;
    }

    public ClientPrivateMessageInterface getPmInterface() {
        return pmInterface;
    }
}
