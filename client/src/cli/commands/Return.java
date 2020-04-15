package cli.commands;

import api.PDPublicAPI;
import cli.framework.Command;
import main.Client;

public class Return extends Command<PDPublicAPI> {

    public String identifier() {
        return "Return";
    }

    public void execute() throws Exception {
        Client.main(null);
    }

    public String describe() {
        return "return to the main menu. No argument.";
    }

    @Override
    public boolean shouldContinue() {
        return false;
    }

}
