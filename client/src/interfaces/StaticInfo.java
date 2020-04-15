package interfaces;

public class StaticInfo {
    private static String ownPseudo;
    private static String connection;
    private static ChatInterface chatInterface;
    private static ClientPrivateMessageInterface pvtMessageInterface;
    private static ClientPrivateMessageInterface lastEmitterMessageInterface;

    public static ChatInterface getChatInterface() {
        return chatInterface;
    }

    public static void setChatInterface(ChatInterface chatInterface) {
        StaticInfo.chatInterface = chatInterface;
    }

    public static String getConnection() {
        return connection;
    }

    public static void setConnection(String conection) {
        StaticInfo.connection = conection;
    }
    public static void setPvtMessageInterface(ClientPrivateMessageInterface pvtMessageInterface) {
        StaticInfo.pvtMessageInterface = pvtMessageInterface;
    }
    public static ClientPrivateMessageInterface getPvtMessageInterface() {
        return pvtMessageInterface;
    }

    public static String getOwnPseudo() {
        return ownPseudo;
    }

    public static void setOwnPseudo(String ownPseudo) {
        StaticInfo.ownPseudo = ownPseudo;
    }

    public static ClientPrivateMessageInterface getLastEmitterMessageInterface() {
        return lastEmitterMessageInterface;
    }

    public static void setLastEmitterMessageInterface(ClientPrivateMessageInterface pvtMessageInterface) {
        lastEmitterMessageInterface = pvtMessageInterface;
    }
}
