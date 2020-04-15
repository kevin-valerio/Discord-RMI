package cli.commands;

import api.PDPublicAPI;
import cli.framework.Command;
import logging.Logger;

public class Quit extends Command<PDPublicAPI> {

    public String identifier() {
        return "Quit";
    }

    public void execute() throws Exception {
        Logger.getLogger().println("Exiting. Bye !");
        System.exit(0);
    }

    public String describe() {
        return "quits the client. No argument.";
    }

    @Override
    public boolean shouldContinue() {
        return false;
    }
}
