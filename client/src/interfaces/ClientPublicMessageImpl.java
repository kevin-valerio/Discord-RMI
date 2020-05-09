package interfaces;

import logging.Logger;

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
            Logger.getLogger().println(
                    "\u001B[47m" + "\u001B[31m" + message.getPseudo() + ": "
                            + "\u001B[30m" + message.getMessage()
                            + "\u001B[0m" + "\u001B[40m" + "\u001B[0m");
        else if (message.getMessage().equals("new_notif"))
            Logger.getLogger().println(
                    "\u001B[32m" + "--------------> YOU HAVE NEW MESSAGES <--------------"
                            + "\u001B[0m");

    }

}
