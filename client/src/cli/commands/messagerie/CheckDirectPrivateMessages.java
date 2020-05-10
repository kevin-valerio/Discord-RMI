package cli.commands.messagerie;

import api.PDPublicAPI;
import cli.framework.Command;
import interfaces.PrivateMessage;
import interfaces.StaticInfo;
import logging.Logger;

public class CheckDirectPrivateMessages extends Command<PDPublicAPI> {
    @Override
    public String identifier() {
        return "Check private messages";
    }

    @Override
    public void execute() throws Exception {
        PrivateMessage currentMessage;

        while (true) {
            try {
                currentMessage = StaticInfo.getPvtMessageInterface().consumePrivateMessage();

                Logger.getLogger().println("\u001B[32mPrivate message received from \u001B[31m" + currentMessage.getPseudo() + "\u001B[0m");
                Logger.getLogger().println("\t" + currentMessage.getMessage());
                Logger.getLogger().println();
            } catch (Exception e) {
                Logger.getLogger().println("\u001B[32mNo more private message\u001B[0m");
                Logger.getLogger().println();
                break;
            }
        }
    }

    @Override
    public String describe() {
        return "Go through all pending private messages";
    }
}
