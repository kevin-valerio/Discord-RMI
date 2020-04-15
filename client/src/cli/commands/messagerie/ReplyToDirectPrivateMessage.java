package cli.commands.messagerie;

import api.PDPublicAPI;
import cli.framework.Command;
import interfaces.ChatInterface;
import interfaces.ClientPrivateMessageInterface;
import interfaces.StaticInfo;

import java.util.Iterator;
import java.util.List;

public class ReplyToDirectPrivateMessage extends Command<PDPublicAPI> {

    private StringBuilder message;

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
                StaticInfo.getLastEmitterMessageInterface();
        remoteClientMessageInterface.receivePrivateMessage(StaticInfo.getOwnPseudo(), StaticInfo.getPvtMessageInterface(), message.toString());
    }

    @Override
    public String describe() {
        return null;
    }
}
