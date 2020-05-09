package cli.commands.messagerie;

import api.PDPublicAPI;
import cli.framework.Command;
import interfaces.ChatInterface;
import interfaces.ClientPrivateMessageInterface;
import interfaces.PrivateMessage;
import interfaces.StaticInfo;

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
        System.out.println("un");
        ClientPrivateMessageInterface remoteClientMessageInterface =
                chatInterface.getUserPrivateMessageInterface(pseudo);
        System.out.println("deux");
        remoteClientMessageInterface.addPrivateMessageToQueue(
                new PrivateMessage(
                        StaticInfo.getOwnPseudo(),
                        message.toString(),
                        StaticInfo.getPvtMessageInterface()
                )
        );
        System.out.println("deux bis");
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
