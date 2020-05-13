package cli.commands.messagerie;

import Colors.ANSI;
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
                    ANSI.CYAN + "\tUser " + "\u001B[31m" + StaticInfo.getOwnPseudo()
                            + ANSI.CYAN + " successfully sent Direct Private Message to "
                            + "\u001B[31m" + pseudo + ANSI.SANE);
            remoteClientMessageInterface.addPrivateMessageToQueue(
                    new PrivateMessage(
                            StaticInfo.getOwnPseudo(),
                            message.toString(),
                            StaticInfo.getPvtMessageInterface()));
        } else {
            Logger.getLogger().println(
                    ANSI.CYAN + "\t" + "Recipient " + ANSI.RED + pseudo + "\t" + ANSI.CYAN
                            + "is currently disconnected\n" + ANSI.RED
                            + "\tFailed " + ANSI.CYAN + "to route direct message to " + ANSI.RED + pseudo
                            + ANSI.SANE);
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
