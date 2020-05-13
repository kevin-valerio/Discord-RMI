package cli.commands;

import Colors.ANSI;
import api.PDPublicAPI;
import cli.commands.messagerie.*;
import cli.framework.Command;
import cli.framework.Shell;
import interfaces.ChatInterface;
import interfaces.ConnectionInterface;
import interfaces.PrivateMessage;
import interfaces.StaticInfo;
import logging.Logger;

import java.rmi.Naming;
import java.rmi.Remote;
import java.util.LinkedList;
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
        try {
            String address = StaticInfo.getConnection();
            Remote r = Naming.lookup("rmi://" + address + "/Connection");
            ChatInterface chatInterface;
            ConnectionInterface connectionInterface = ((ConnectionInterface) r);
            chatInterface = connectionInterface.connect(
                    login, password, StaticInfo.getPvtMessageInterface(),
                    StaticInfo.getPublicMessageInterface());

            if (chatInterface == null) {
                Logger.getLogger().println("Wrong login / password. Try again !");
                return;
            }

            StaticInfo.setOwnPseudo(chatInterface.getPseudo());
            StaticInfo.setConnectionInterface(connectionInterface);
            PrivateMessage uniqueMsg;
            int numberOfNewPvtMsgs;

            Logger.getLogger().println("");
            Logger.getLogger().println("Welcome " + login + " !");
            Logger.getLogger().println("Type back to disconnect and ? for help.");
            Logger.getLogger().println("");

            String newMsgsNotification = chatInterface.newMsgsFromTextChannels(chatInterface.getPseudo());
            if (newMsgsNotification != null)
                Logger.getLogger().println(
                        ANSI.YELLOW + "\tNEW MESSAGES IN TEXT CHANNEL: \u001B[31m" + newMsgsNotification + ANSI.SANE);

            //Pvt Messages stores on server
            numberOfNewPvtMsgs = chatInterface.numberOfNewPrivateMessages(chatInterface.getPseudo());
            //System.out.println("nummmber côté server => " + numberOfNewPvtMsgs);

            if (numberOfNewPvtMsgs > 0) {
                //System.out.println("1");
                Logger.getLogger().flush();
                //System.out.println("2");
                if (numberOfNewPvtMsgs == 1) {
                    uniqueMsg = chatInterface.getCopyOfUniqueMessage(chatInterface.getPseudo());
                    Logger.getLogger().println(
                            ANSI.YELLOW + "New private message received from \u001B[31m"
                                    + uniqueMsg.getPseudo() + ANSI.SANE);
                } else {
                    Logger.getLogger().println(
                            ANSI.YELLOW + "New private messages received" + ANSI.SANE);
                }
                Logger.getLogger().println(
                        ANSI.CYAN + "Use the appropriate command to check pending private messages\n"
                                + ANSI.SANE);
            }

            //System.out.println("3");
            StaticInfo.setChatInterface(chatInterface);

            Shell<PDPublicAPI> shell = new Shell<>();
            shell.system = new PDPublicAPI();
            shell.invite = ANSI.BLUE + "Discord";
            shell.register(
                    Return.class,
                    GetListGroup.class,
                    GetMyListGroup.class,
                    VisualiseGroup.class,
                    JoinGroup.class,
                    SendDirectPrivateMessage.class,
                    CheckDirectPrivateMessages.class,
                    ReplyToDirectPrivateMessage.class,
                    SendPrivateMessage.class,
                    CheckPrivateMessages.class,
                    ReplyToPrivateMessage.class
            );
            StaticInfo.setCurrentShell(shell);
            shell.run();

        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }

    @Override
    public String describe() {
        return "Connect to the discord. Arguments : username, password.";
    }

}

