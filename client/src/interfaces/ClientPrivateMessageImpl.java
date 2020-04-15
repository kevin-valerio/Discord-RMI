package interfaces;

import api.PDPublicAPI;
import cli.commands.Return;
import cli.commands.messagerie.SendDirectPrivateMessage;
import cli.framework.Shell;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientPrivateMessageImpl extends UnicastRemoteObject implements ClientPrivateMessageInterface {

    public ClientPrivateMessageImpl() throws RemoteException {
        super();
    }

    @Override
    public void receivePrivateMessage(String emitterPseudo, ClientPrivateMessageInterface pvtMessageInterface, String message) throws RemoteException, InterruptedException  {
        System.out.println("Message received from \u001B[31m" + emitterPseudo + "\u001B[0m");
        System.out.println("\t" +message);

        StaticInfo.setLastEmitterMessageInterface(pvtMessageInterface);

        Shell<PDPublicAPI> shell = new Shell<>();
        shell.system = new PDPublicAPI();
        shell.invite = "Discord";
        shell.register(
                Return.class,
                SendDirectPrivateMessage.class);
        shell.run();
    }
}
