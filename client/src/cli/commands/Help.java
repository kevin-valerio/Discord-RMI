package cli.commands;

import api.PDPublicAPI;
import cli.framework.Command;
import logging.Logger;

public class Help extends Command<PDPublicAPI> {

    public String identifier() {
        return "Help";
    }

    public void execute() throws Exception {
        Logger.getLogger().println("The menu consists in differents sub-menu.");
        Logger.getLogger().println("Use numbers in the console to get into a sub-menu.");
        Logger.getLogger().println("You can also pass parameters. Example for connection :");
        Logger.getLogger().println("0 <username> <password>");
    }

    public String describe() {
        return "shows help message. No argument.";
    }

    @Override
    public boolean shouldContinue() {
        return false;
    }
}
