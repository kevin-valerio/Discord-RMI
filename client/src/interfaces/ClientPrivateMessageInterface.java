package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientPrivateMessageInterface extends Remote {

    boolean addPrivateMessageToQueue(PrivateMessage pm) throws RemoteException, InterruptedException;

    void receivePrivateMessage(
            String emitterPseudo,
            ClientPrivateMessageInterface pvtMessageInterface,
            String message) throws RemoteException, InterruptedException;
}
