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

public class ReplyToDirectPrivateMessage extends Command<PDPublicAPI> {

    private StringBuilder message = new StringBuilder();

    @Override
    public String identifier() { return "Reply to last direct private message";
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
        ClientPrivateMessageInterface remoteClientMessageInterface =
                StaticInfo.getLastEmitterDirectPvtMessageInterface();
        if (remoteClientMessageInterface == null) {
            Logger.getLogger().println(
                    ANSI.RED + "ERROR: " + ANSI.GREEN
                            + "No Last Emitter of Direct Private Message (as of now)"+ ANSI.SANE);
            return;
        }
        remoteClientMessageInterface.addPrivateMessageToQueue(
                new PrivateMessage(
                        StaticInfo.getOwnPseudo(),
                        message.toString(),
                        StaticInfo.getPvtMessageInterface()
                )
        );
        Logger.getLogger().println(
                "\t\u001B[32mUser " + "\u001B[31m" + StaticInfo.getOwnPseudo()
                        + "\u001B[32m successfully sent Direct Private Message to "
                        + ANSI.RED + StaticInfo.getLastEmitterDirectPvtMessagePseudo() + ANSI.SANE);
    }

    @Override
    public String describe() {
        return null;
    }
}
