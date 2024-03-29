package cli.commands.messagerie;

import Colors.ANSI;
import api.PDPublicAPI;
import cli.framework.Command;
import interfaces.ClientPrivateMessageInterface;
import interfaces.PrivateMessage;
import interfaces.StaticInfo;
import logging.Logger;

import java.util.Iterator;
import java.util.List;

public class ReplyToPrivateMessage extends Command<PDPublicAPI> {

    private final StringBuilder message = new StringBuilder();

    @Override
    public String identifier() { return "Reply to last private message";
    }

    @Override
    public void load(List<String> args) {
        Iterator<String> it = args.iterator();
        while (it.hasNext()) {
            message.append(it.next());
            message.append(" ");
        }
    }

    @Override
    public void execute() throws Exception {
        String lastEmitterPvtMessagePseudo = StaticInfo.getLastEmitterPvtMessagePseudo();
        ClientPrivateMessageInterface remoteClient = StaticInfo.getLastEmitterPvtMessageInterface();
        //System.out.println("last emitter pvt msg pseudo => " + lastEmitterPvtMessagePseudo);
        //System.out.println("staticinfo.getchatinterface() => " + StaticInfo.getChatInterface());
        if (lastEmitterPvtMessagePseudo == null) {
            Logger.getLogger().println(
                    ANSI.RED + "ERROR: " + ANSI.YELLOW
                            + "No Last Emitter of Private Message (as of now)"+ ANSI.SANE);
            return;
        }
        StaticInfo.getChatInterface().addMessageToPvtMsgQueueOfUser(
                lastEmitterPvtMessagePseudo,
                new PrivateMessage(
                        StaticInfo.getOwnPseudo(),
                        message.toString(),
                        StaticInfo.getPvtMessageInterface()));
        //remoteClient.notifyClientOfNewPrivateMessages(StaticInfo.getOwnPseudo());
        Logger.getLogger().println(
                ANSI.CYAN + "\tUser " + ANSI.RED + StaticInfo.getOwnPseudo()
                        + ANSI.CYAN + " successfully sent Direct Private Message to "
                        + ANSI.RED + lastEmitterPvtMessagePseudo + ANSI.SANE);
    }

    @Override
    public String describe() {
        return null;
    }
}