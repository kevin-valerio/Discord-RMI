package cli.commands;

import api.PDPublicAPI;
import cli.commands.messagerie.*;
import cli.framework.Command;
import cli.framework.Shell;
import interfaces.ChatInterface;
import interfaces.ConnectionInterface;
import interfaces.StaticInfo;
import logging.Logger;

import java.rmi.Naming;
import java.rmi.Remote;
import java.util.List;

public class Connect extends Command<PDPublicAPI> {

    private String login;

    private String password;

    @Override
    public String identifier() {
        return "Connect";
    }

    @Override
    public void load(List<String> args) {
        this.login = args.get(0);
        this.password = args.get(1);
    }

    @Override
    public void execute() throws Exception {
        try
        {
            String address = StaticInfo.getConnection();
            Remote r = Naming.lookup("rmi://" + address + "/Connection");
            ChatInterface chatInterface;
            ConnectionInterface connectionInterface = ((ConnectionInterface) r);
            chatInterface = connectionInterface.connect(
                    login, password, StaticInfo.getPvtMessageInterface(),
                    StaticInfo.getPublicMessageInterface());
            StaticInfo.setOwnPseudo(chatInterface.getPseudo());
            StaticInfo.setConnectionInterface(connectionInterface);

            if (chatInterface == null){
                Logger.getLogger().println("Wrong login / password. Try again !");
            }
            else {
                Logger.getLogger().println("");
                Logger.getLogger().println("Welcome "+login + " !");
                /*
                * TODO: replace "#1" by the channel(s) concerned
                * */
                if (chatInterface.newMsgsFromChannel1(chatInterface.getPseudo()))
                    Logger.getLogger().println("\u001B[32m" + "\tNEW MESSAGES IN TEXT CHANNEL " + "#1" + "\u001B[0m");
                Logger.getLogger().println("Type back to disconnect and ? for help.");
                Logger.getLogger().println("");

                StaticInfo.setChatInterface(chatInterface);

                Shell<PDPublicAPI> shell = new Shell<>();
                shell.system = new PDPublicAPI();
                shell.invite = "Discord";
                shell.register(
                        Return.class,
                        GetListGroup.class,
                        GetMyListGroup.class,
                        VisualiseGroup.class,
                        JoinGroup.class,
                        SendDirectPrivateMessage.class,
                        CheckPrivateMessages.class,
                        ReplyToDirectPrivateMessage.class
                );
                StaticInfo.setCurrentShell(shell);
                shell.run();
            }

        }
        catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }

    @Override
    public String describe() {
        return "Connect to the discord. Arguments : username, password.";
    }

}

