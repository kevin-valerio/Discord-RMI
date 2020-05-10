package main;

import Colors.ANSI;
import api.PDPublicAPI;
import cli.commands.Connect;
import cli.commands.Help;
import cli.commands.Quit;
import cli.framework.Shell;
import interfaces.ClientPrivateMessageImpl;
import interfaces.ClientPublicMessageImpl;
import interfaces.StaticInfo;
import logging.Logger;

import static java.lang.System.exit;

public class Client extends Shell<PDPublicAPI> {

    public Client(){
        super.system = new PDPublicAPI();
        super.invite = ANSI.BLUE + "Discord";

        register(
                Connect.class,
                Quit.class,
                Help.class
        );
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            Logger.getLogger().println(("No args given. Setting default args to localhost"));
            args = new String[]{"localhost:1099"};
        }

        if (args.length == 1) {
            StaticInfo.setConnection(args[0]);
            try {
                StaticInfo.setPvtMessageInterface(new ClientPrivateMessageImpl());
                StaticInfo.setPublicMessageInterface(new ClientPublicMessageImpl());
            } catch (Exception e) {
                e.printStackTrace();
                exit(-1);
            }

            Client client = new Client();
            client.run();
        } else {
            Logger.getLogger().println(("Too many args given"));
        }
    }
}