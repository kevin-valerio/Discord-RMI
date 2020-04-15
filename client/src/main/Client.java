package main;

import api.PDPublicAPI;
import cli.commands.Connect;
import cli.commands.Help;
import cli.commands.Quit;
import cli.framework.Shell;
import interfaces.ClientPrivateMessageImpl;
import interfaces.StaticInfo;
import logging.Logger;

public class Client extends Shell<PDPublicAPI> {

    public Client(){
        super.system = new PDPublicAPI();
        super.invite = "Discord";

        register(
                Connect.class,
                Quit.class,
                Help.class
        );
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            Logger.getLogger().println(("No args given. Setting default args to localhost"));
            args = new String[]{"127.0.0.1"};
        }

        if (args.length == 1) {
            StaticInfo.setConnection(args[0]);
            StaticInfo.setPvtMessageInterface(new ClientPrivateMessageImpl());
            Client client = new Client();
            client.run();
        } else {
            Logger.getLogger().println(("Too many args given"));
        }
    }
}