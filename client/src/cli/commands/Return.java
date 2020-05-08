package cli.commands;

import api.PDPublicAPI;
import cli.framework.Command;
import interfaces.StaticInfo;
import main.Client;

public class Return extends Command<PDPublicAPI> {

    public String identifier() {
        return "Return";
    }

    public void execute() throws Exception {
        StaticInfo.getConnectionInterface().disconnect(StaticInfo.getOwnPseudo());
        Client.main(new String[]{""});
    }

    public String describe() {
        return "return to the main menu. No argument.";
    }

    @Override
    public boolean shouldContinue() {
        return false;
    }

}
