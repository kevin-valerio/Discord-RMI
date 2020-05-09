package cli.commands.messagerie;

import api.PDPublicAPI;
import cli.framework.Command;
import interfaces.*;
import logging.Logger;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;

public class SendDirectPrivateMessage extends Command<PDPublicAPI> {

    private String pseudo;

    private StringBuilder message = new StringBuilder();

    @Override
    public String identifier() {
        return "Send a direct private message";
    }

    @Override
    public void load(List<String> args) {
        Iterator<String> it = args.iterator();
        if (it.hasNext())
            pseudo = it.next();

        while (it.hasNext()) {
            message.append(it.next());
            message.append(" ");
        }
    }

    @Override
    public void execute() throws Exception {

        ChatInterface chatInterface = StaticInfo.getChatInterface();
        ClientPrivateMessageInterface remoteClientMessageInterface =
                chatInterface.getUserPrivateMessageInterface(pseudo);

        if (remoteClientMessageInterface != null) {  //if remoteClient is connected
            System.out.println(StaticInfo.getOwnPseudo() + "sends direct private message");
            remoteClientMessageInterface.addPrivateMessageToQueue(
                    new PrivateMessage(
                            StaticInfo.getOwnPseudo(),
                            message.toString(),
                            StaticInfo.getPvtMessageInterface()));
        } else {
            Logger.getLogger().println(
                    "\t\u001B[32m" + "recipient " + "\u001B[31m" + pseudo + "\t\u001B[32m"
                            + "is currently disconnected\n\tyour message was saved in server"
                            + "\t\u001B[0m");
            StaticInfo.getChatInterface().addMessageToPvtMsgQueueOfUser(
                    pseudo,
                    new PrivateMessage(
                            StaticInfo.getOwnPseudo(),
                            message.toString(),
                            StaticInfo.getPvtMessageInterface()));
        }
    }

    /*
    @Override
    public boolean shouldContinue() {
        return false;
    }*/

    @Override
    public String describe() {
        return null;
    }
}
