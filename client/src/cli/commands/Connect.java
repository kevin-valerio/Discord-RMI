package cli.commands;

import api.PDPublicAPI;
import cli.commands.messagerie.*;
import cli.framework.Command;
import cli.framework.Shell;
import interfaces.*;
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
            int numberOfNewMsgs;
            LinkedList<PrivateMessage> newMessagesQueue = null;

            if (chatInterface == null){
                Logger.getLogger().println("Wrong login / password. Try again !");
            }
            else {
                Logger.getLogger().println("");
                Logger.getLogger().println("Welcome " + login + " !");
                Logger.getLogger().println("Type back to disconnect and ? for help.");
                Logger.getLogger().println("");

                String newMsgsNotification = chatInterface.newMsgsFromTextChannels(chatInterface.getPseudo());
                if (newMsgsNotification != null)
                    Logger.getLogger().println("\u001B[32m" + "\tNEW MESSAGES IN TEXT CHANNEL: \u001B[31m" + newMsgsNotification + "\u001B[0m");

                numberOfNewMsgs = chatInterface.numberOfNewPrivateMessages(chatInterface.getPseudo());
                System.out.println("nummmber côté server => " + numberOfNewMsgs);

                numberOfNewMsgs = StaticInfo.getPvtMessageInterface().getPmQueue().size();
                System.out.println("number côté client => " + numberOfNewMsgs);

                if (numberOfNewMsgs > 0) {
                    System.out.println("1");;
                    newMessagesQueue = chatInterface.consumeAllMsgsFromQueueOfUser(chatInterface.getPseudo());
                    Logger.getLogger().flush();
                    System.out.println("2");
                    if (numberOfNewMsgs == 1) {
                        Logger.getLogger().println(
                                "\u001B[32m" + "New private message received from \u001B[31m"
                                        + newMessagesQueue.getFirst().getPseudo() + "\u001B[0m");
                    } else {
                        Logger.getLogger().println(
                                "\u001B[32m" + "New private messages received" + "\u001B[0m");
                    }
                    Logger.getLogger().println("Use the appropriate command to check pending private messages");
                }

                System.out.println("3");
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

