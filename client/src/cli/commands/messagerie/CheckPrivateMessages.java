package cli.commands.messagerie;

import Colors.ANSI;
import api.PDPublicAPI;
import cli.framework.Command;
import interfaces.PrivateMessage;
import interfaces.StaticInfo;
import logging.Logger;

import java.util.LinkedList;

public class CheckPrivateMessages extends Command<PDPublicAPI> {
    @Override
    public String identifier() {
        return "Check private messages";
    }

    @Override
    public void execute() throws Exception {
        LinkedList<PrivateMessage> newPrivateMessages = StaticInfo.getChatInterface().consumeAllMsgsFromQueueOfUser(StaticInfo.getOwnPseudo());
        PrivateMessage pm;
        while (!newPrivateMessages.isEmpty()) {
            pm = newPrivateMessages.remove();
            Logger.getLogger().println(
                    "\u001B[32m" + "Private message received from \u001B[31m" + pm.getPseudo() + "\u001B[0m");
            Logger.getLogger().println("\t" + pm.getMessage() + "\n");
            //StaticInfo.setLastEmitterPvtMessageInterface(pm.getPmInterface());
            //StaticInfo.setLastEmitterPvtMessagePseudo(pm.getPseudo());
        }
        Logger.getLogger().println("\u001B[32mNo more private message" + ANSI.SANE + "\n");
    }

    @Override
    public String describe() {
        return "Go through all pending private messages";
    }
}