package cli.commands.messagerie;

import Colors.ANSI;
import api.PDPublicAPI;
import cli.framework.Command;
import interfaces.PrivateMessage;
import interfaces.StaticInfo;
import logging.Logger;

public class CheckDirectPrivateMessages extends Command<PDPublicAPI> {
    @Override
    public String identifier() {
        return "Check direct Private messages";
    }

    @Override
    public void execute() throws Exception {
        PrivateMessage currentMessage;

        while (true) {
            try {
                currentMessage = StaticInfo.getPvtMessageInterface().consumePrivateMessage();

                Logger.getLogger().println(
                        ANSI.CYAN + "Direct Private message received from "
                                + ANSI.RED + currentMessage.getPseudo() + ANSI.SANE);
                Logger.getLogger().println("\t" + currentMessage.getMessage());
                Logger.getLogger().println();
            } catch (Exception e) {
                Logger.getLogger().println(ANSI.YELLOW + "No more Direct Private messages" + ANSI.SANE);
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
