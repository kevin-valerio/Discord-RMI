package cli.commands.messagerie;

import api.PDPublicAPI;
import cli.framework.Command;
import interfaces.StaticInfo;
import logging.Logger;

import java.util.List;

public class JoinGroup extends Command<PDPublicAPI>  {

    private String idTopic = null;

    @Override
    public String identifier() {
        return "Join a topic";
    }

    @Override
    public void load(List<String> args) {
        this.idTopic = args.get(0);
    }

    @Override
    public void execute() throws Exception {

        Logger.getLogger().println("");
        Logger.getLogger().println("Joined topic #"+idTopic);
        Logger.getLogger().println("");
        StaticInfo.getChatInterface().joinGroup(idTopic);

    }

    @Override
    public String describe() {
        return "Joins a topic. Arguments: topic id";
    }
}
