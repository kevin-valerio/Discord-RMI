package interfaces;

import cli.framework.Shell;

public class StaticInfo {
    private static String ownPseudo;
    private static String connection;
    private static ChatInterface chatInterface;
    private static ConnectionInterface connectionInterface;



    private static ClientPrivateMessageImpl pvtMessageInterface;
    private static String lastEmitterDirectPvtMessagePseudo;
    private static ClientPrivateMessageInterface lastEmitterDirectPvtMessageInterface;
    private static String lastEmitterPvtMessagePseudo;
    private static ClientPrivateMessageInterface lastEmitterPvtMessageInterface;

    private static ClientPublicMessageInterface publicMessageInterface;
    private static boolean isVisualisingGroup = false;

    public static void enterVisualisingGroup() {isVisualisingGroup = true;}

    public static void exitVisualisingGroup() {isVisualisingGroup = false;}

    public static boolean isCurrentlyVisualisingGroup() {return  isVisualisingGroup;}

    private static Shell currentShell;

    public static void setLastEmitterPvtMessagePseudo(String lastEmitterPvtMessagePseudo) {
        StaticInfo.lastEmitterPvtMessagePseudo = lastEmitterPvtMessagePseudo;
    }

    public static String getLastEmitterPvtMessagePseudo() {
        return lastEmitterPvtMessagePseudo;
    }

    public static void setLastEmitterPvtMessageInterface(ClientPrivateMessageInterface lastEmitterPvtMessageInterface) {
        StaticInfo.lastEmitterPvtMessageInterface = lastEmitterPvtMessageInterface;
    }

    public static ClientPrivateMessageInterface getLastEmitterPvtMessageInterface() {
        return lastEmitterPvtMessageInterface;
    }

    public static void setPublicMessageInterface(ClientPublicMessageInterface publicMessageInterface) {
        StaticInfo.publicMessageInterface = publicMessageInterface;
    }

    public static ClientPublicMessageInterface getPublicMessageInterface() {
        return publicMessageInterface;
    }
    public static void setConnectionInterface(ConnectionInterface connectionInterface) {
        StaticInfo.connectionInterface = connectionInterface;
    }

    public static ConnectionInterface getConnectionInterface() {
        return connectionInterface;
    }

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

    public static ClientPrivateMessageInterface getLastEmitterDirectPvtMessageInterface() {
        return lastEmitterDirectPvtMessageInterface;
    }

    public static void setLastEmitterDirectPvtMessageInterface(ClientPrivateMessageInterface pvtMessageInterface) {
        lastEmitterDirectPvtMessageInterface = pvtMessageInterface;
    }

    public static String getLastEmitterDirectPvtMessagePseudo() {
        return lastEmitterDirectPvtMessagePseudo;
    }

    public static void setLastEmitterDirectPvtMessagePseudo(String lastEmitterDirectPvtMessagePseudo) {
        StaticInfo.lastEmitterDirectPvtMessagePseudo = lastEmitterDirectPvtMessagePseudo;
    }

    public static Shell getCurrentShell() {
        return currentShell;
    }

    public static void setCurrentShell(Shell currentShell) {
        StaticInfo.currentShell = currentShell;
    }
}
