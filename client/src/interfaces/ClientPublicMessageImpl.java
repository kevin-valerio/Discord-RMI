package interfaces;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;

public class ClientPublicMessageImpl extends UnicastRemoteObject implements ClientPublicMessageInterface {

    public ClientPublicMessageImpl() throws RemoteException {
        super();
    }

    @Override
    public void displayMessage(PublicMessage message) throws RemoteException, InterruptedException {
        if (message.getPseudo() != null)
            System.out.println(
                    "\u001B[47m" + "\u001B[31m" + message.getPseudo() + ": "
                            + "\u001B[30m" + message.getMessage()
                            + "\u001B[0m" + "\u001B[40m" + "\u001B[0m");
        else
            System.out.println(message.getMessage());

    }

}
