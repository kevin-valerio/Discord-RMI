package cli.commands.messagerie;

import api.PDPublicAPI;
import cli.framework.Command;
import interfaces.ChatInterface;
import interfaces.ClientPrivateMessageInterface;
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

        ClientPrivateMessageInterface remoteClientMessageInterface =
                chatInterface.getUserPrivateMassageInterface(pseudo);

        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    remoteClientMessageInterface.receivePrivateMessage(StaticInfo.getOwnPseudo(), StaticInfo.getPvtMessageInterface(), message.toString());
                } catch (Exception e) {
                    System.out.println("Error Sending Message:\n");
                    e.printStackTrace();
                }
            }
        });

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
