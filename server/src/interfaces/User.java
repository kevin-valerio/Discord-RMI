package interfaces;

import com.sun.security.ntlm.Client;

public class User {
    private String login;
    private String password;
    private String pseudo;
    private ClientPrivateMessageInterface lastEmitterPvtMessageInterface;
    private String lastEmitterPvtMessagePseudo;

    private ClientPrivateMessageInterface privateMessageInterface;
    private ClientPublicMessageInterface publicMessageInterface;

    public void setPublicMessageInterface(ClientPublicMessageInterface publicMessageInterface) {
        this.publicMessageInterface = publicMessageInterface;
    }

    public ClientPublicMessageInterface getPublicMessageInterface() {
        return publicMessageInterface;
    }

    public User(String login, String password, String pseudo){
        this.login = login;
        this.password = password;
        this.pseudo = pseudo;
    }

    public String getPseudo() {
        return pseudo;
    }

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }

    public ClientPrivateMessageInterface getLastEmitterPvtMessageInterface() {
        return lastEmitterPvtMessageInterface;
    }

    public void setLastEmitterPvtMessageInterface(ClientPrivateMessageInterface lastEmitterPvtMessageInterface) {
        this.lastEmitterPvtMessageInterface = lastEmitterPvtMessageInterface;
    }

    public String getLastEmitterPvtMessagePseudo() {
        return lastEmitterPvtMessagePseudo;
    }

    public void setLastEmitterPvtMessagePseudo(String lastEmitterPvtMessagePseudo) {
        this.lastEmitterPvtMessagePseudo = lastEmitterPvtMessagePseudo;
    }

    public void setPrivateMessageInterface(ClientPrivateMessageInterface clientPrivateMessageInterface) {
        this.privateMessageInterface = clientPrivateMessageInterface;
    }

    public ClientPrivateMessageInterface getPrivateMessageInterface() {
        return privateMessageInterface;
    }
}
