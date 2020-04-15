package interfaces;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientPrivateMessageImpl extends UnicastRemoteObject implements ClientPrivateMessageInterface {
    public ClientPrivateMessageImpl() throws RemoteException {
        super();
    }
}
