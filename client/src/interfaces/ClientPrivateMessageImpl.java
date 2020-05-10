package interfaces;

import api.PDPublicAPI;
import cli.commands.Return;
import cli.commands.messagerie.*;
import cli.framework.Shell;
import logging.Logger;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;

public class ClientPrivateMessageImpl extends UnicastRemoteObject implements ClientPrivateMessageInterface {

    private LinkedList<PrivateMessage> pmQueue;



    public ClientPrivateMessageImpl() throws RemoteException {
        super();
        this.pmQueue = new LinkedList<>();
    }

    @Override
    public boolean addPrivateMessageToQueue(PrivateMessage pm) throws RemoteException, InterruptedException {
        Logger.getLogger().flush();
        Logger.getLogger().println();
        Logger.getLogger().println();
        Logger.getLogger().println("New private message received from \u001B[31m" + pm.getPseudo() + "\u001B[0m");
        Logger.getLogger().println("Use the appropriate command to check pending private messages");
        StaticInfo.setLastEmitterDirectPvtMessageInterface(pm.getPmInterface());
        StaticInfo.setLastEmitterDirectPvtMessagePseudo(pm.getPseudo());
        Logger.getLogger().println();
        Shell shell = StaticInfo.getCurrentShell();

        if (!StaticInfo.isCurrentlyVisualisingGroup())
            System.out.print(shell.invite + " > ");

        return this.pmQueue.add(pm);
    }

    @Override
    public void silentAddPrivateMessageToQueue(PrivateMessage pm) throws RemoteException, InterruptedException {
        StaticInfo.setLastEmitterDirectPvtMessageInterface(pm.getPmInterface());
        StaticInfo.setLastEmitterDirectPvtMessagePseudo(pm.getPseudo());
        this.pmQueue.add(pm);
    }

    @Override
    public void setPvtMessageLastEmitterData(PrivateMessage pm) throws RemoteException, InterruptedException {
        StaticInfo.setLastEmitterPvtMessageInterface(pm.getPmInterface());
        StaticInfo.setLastEmitterPvtMessagePseudo(pm.getPseudo());
    }

    @Override
    public void emptyDirectPvtMessageQueue() throws RemoteException, InterruptedException {
        this.pmQueue = new LinkedList<>();
    }

    public LinkedList<PrivateMessage> getPmQueue() throws RemoteException, InterruptedException {
        return pmQueue;
    }

    public PrivateMessage consumePrivateMessage() {
        return this.pmQueue.remove();
    }

    @Override
    public void receivePrivateMessage(String emitterPseudo, ClientPrivateMessageInterface pvtMessageInterface, String message) throws RemoteException, InterruptedException  {
        System.out.println("Message received from \u001B[31m" + emitterPseudo + "\u001B[0m");
        System.out.println("\t" +message);

        StaticInfo.setLastEmitterDirectPvtMessageInterface(pvtMessageInterface);
        //shell.printCommandsIDs();
        Shell<PDPublicAPI> shell = new Shell<>();
        System.out.print("==> |");
        System.out.println("|<===\n");
        shell.system = new PDPublicAPI();
        shell.invite = "Discord";
        shell.register(
                Return.class,
                ReplyToDirectPrivateMessage.class);
        shell.run();
    }
}
