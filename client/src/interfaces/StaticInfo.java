package interfaces;

import cli.framework.Shell;

public class StaticInfo {
    private static String ownPseudo;
    private static String connection;
    private static ChatInterface chatInterface;
    private static ClientPrivateMessageImpl pvtMessageInterface;
    private static String lastEmitterMessagePseudo;
    private static ClientPrivateMessageInterface lastEmitterMessageInterface;
    private static ClientPublicMessageInterface publicMessageInterface;


    public static void setPublicMessageInterface(ClientPublicMessageInterface publicMessageInterface) {
        StaticInfo.publicMessageInterface = publicMessageInterface;
    }

    public static ClientPublicMessageInterface getPublicMessageInterface() {
        return publicMessageInterface;
    }

    private static Shell currentShell;

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
    public static void setPvtMessageInterface(ClientPrivateMessageImpl pvtMessageInterface) {
        StaticInfo.pvtMessageInterface = pvtMessageInterface;
    }
    public static ClientPrivateMessageImpl getPvtMessageInterface() {
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

    public static String getLastEmitterMessagePseudo() {
        return lastEmitterMessagePseudo;
    }

    public static void setLastEmitterMessagePseudo(String lastEmitterMessagePseudo) {
        StaticInfo.lastEmitterMessagePseudo = lastEmitterMessagePseudo;
    }

    public static Shell getCurrentShell() {
        return currentShell;
    }

    public static void setCurrentShell(Shell currentShell) {
        StaticInfo.currentShell = currentShell;
    }
}
