package interfaces;

import logging.Logger;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import Colors.ANSI;
import java.util.LinkedList;

public class ClientPublicMessageImpl extends UnicastRemoteObject implements ClientPublicMessageInterface {

    public ClientPublicMessageImpl() throws RemoteException {
        super();
    }

    @Override
    public void displayMessage(PublicMessage message) throws RemoteException, InterruptedException {
        if (message.getPseudo() != null)
            Logger.getLogger().println(
                    ANSI.UNDERLINE + ANSI.BACKGROUND_WHITE + ANSI.YELLOW + message.getPseudo() + ":"
                            + ANSI.SANE + ANSI.BACKGROUND_WHITE + ANSI.GREEN + " " + message.getMessage()
                            + ANSI.SANE + "\u001B[40m" + ANSI.SANE);
        else if (message.getMessage().equals("new_notif"))
            Logger.getLogger().println(
                    "\u001B[32m" + "--------------> YOU HAVE NEW MESSAGES <--------------"
                            + "\u001B[0m");

    }

}
