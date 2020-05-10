package cli.commands.messagerie;

import api.PDPublicAPI;
import cli.framework.Command;
import interfaces.*;
import logging.Logger;

import java.util.Iterator;
import java.util.List;

public class SendPrivateMessage extends Command<PDPublicAPI> {

    private String pseudo;

    private final StringBuilder message = new StringBuilder();

    @Override
    public String identifier() {
        return "Send a private message";
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
        StaticInfo.getChatInterface().addMessageToPvtMsgQueueOfUser(
                pseudo,
                new PrivateMessage(
                        StaticInfo.getOwnPseudo(),
                        message.toString(),
                        StaticInfo.getPvtMessageInterface()
                ));
        Logger.getLogger().println(
                "\n\u001B[33m" + "user " + "\u001B[31m" + StaticInfo.getOwnPseudo()
                        + "\u001B[33m successfully sent Private Message to "
                        + "\u001B[31m" + pseudo + "\u001B[0m");
    }

    @Override
    public String describe() {
        return null;
    }

}
