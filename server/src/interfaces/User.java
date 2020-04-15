package interfaces;

public class User {
    private String login;

    private String password;

    private String pseudo;

    private ClientPrivateMessageInterface privateMessageInterface;

    public User(String login,String password, String pseudo){
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

    public void setPrivateMessageInterface(ClientPrivateMessageInterface clientPrivateMessageInterface) {
        this.privateMessageInterface = clientPrivateMessageInterface;
    }

    public ClientPrivateMessageInterface getPrivateMessageInterface() {
        return privateMessageInterface;
    }
}
