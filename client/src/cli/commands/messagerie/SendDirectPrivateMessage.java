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
            Logger.getLogger().println(
                    "\t\u001B[32mUser " + "\u001B[31m" + StaticInfo.getOwnPseudo()
                            + "\u001B[32msuccessfully sent Direct Private Message to "
                            + "\u001B[31m" + pseudo + "\u001B[0m");
            remoteClientMessageInterface.addPrivateMessageToQueue(
                    new PrivateMessage(
                            StaticInfo.getOwnPseudo(),
                            message.toString(),
                            StaticInfo.getPvtMessageInterface()));
        } else {
            Logger.getLogger().println(
                    "\t\u001B[32m" + "Recipient " + "\u001B[31m" + pseudo + "\t\u001B[32m"
                            + "is currently disconnected\n" + "\u001B[31m"
                            + "\tFailed \u001B[32mto route direct message to " + "\u001B[31m" + pseudo
                            + "\u001B[0m");
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
