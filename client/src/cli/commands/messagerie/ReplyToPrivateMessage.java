package cli.commands.messagerie;

import api.PDPublicAPI;
import cli.framework.Command;
import interfaces.PrivateMessage;
import interfaces.StaticInfo;

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
        StaticInfo.getChatInterface().addMessageToPvtMsgQueueOfUser(
                lastEmitterPvtMessagePseudo,
                new PrivateMessage(
                        StaticInfo.getOwnPseudo(),
                        message.toString(),
                        StaticInfo.getPvtMessageInterface()));
    }

    @Override
    public String describe() {
        return null;
    }
}