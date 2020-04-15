package cli.commands.messagerie;

import api.PDPublicAPI;
import cli.framework.Command;

public class SendDirectPrivateMessage extends Command<PDPublicAPI> {
    @Override
    public String identifier() {
        return "Send a direct private message";
    }

    @Override
    public void execute() throws Exception {
        
        shell.register(
                GetListGroup.class,
                GetMyListGroup.class,
                VisualiseGroup.class,
                JoinGroup.class,
                SendDirectPrivateMessage.class
        );
    }

    @Override
    public String describe() {
        return null;
    }
}
