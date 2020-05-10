package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.LinkedList;

public interface ClientPrivateMessageInterface extends Remote {

    boolean addPrivateMessageToQueue(PrivateMessage pm) throws RemoteException, InterruptedException;

    public LinkedList<PrivateMessage> getPmQueue() throws RemoteException, InterruptedException;

    public void silentAddPrivateMessageToQueue(PrivateMessage pm) throws RemoteException, InterruptedException;

    public void emptyDirectPvtMessageQueue() throws RemoteException, InterruptedException;

    public void setPvtMessageLastEmitterData(PrivateMessage pm) throws RemoteException, InterruptedException;

    void receivePrivateMessage(
            String emitterPseudo,
            ClientPrivateMessageInterface pvtMessageInterface,
            String message) throws RemoteException, InterruptedException;
}
