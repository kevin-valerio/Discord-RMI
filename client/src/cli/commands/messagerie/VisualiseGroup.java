package cli.commands.messagerie;

import Colors.ANSI;
import api.PDPublicAPI;
import cli.commands.Return;
import cli.framework.Command;
import interfaces.PublicMessage;
import interfaces.StaticInfo;
import cli.framework.Shell;


import java.util.List;

public class VisualiseGroup extends Command<PDPublicAPI> {
    private String idTopic = null;
    private String pseudo = null;
    private String login = null;
    private String password = null;


    @Override
    public String identifier() {
        return "Enter a vocal chatroom";
    }

    @Override
    public void load(List<String> args) {
        this.idTopic = args.get(0);
    }

    @Override
    public void execute() throws Exception {

        List<String> list = StaticInfo.getChatInterface().getGroupConnection(idTopic);
        login = list.get(0);
        password = list.get(1);
        pseudo = list.get(2);
        System.out.println("");
        System.out.println("Connected to topic #"+idTopic+" as "+"\u001B[31m"+pseudo+"\u001B[0m");
        System.out.println("Type exit to leave.");
        System.out.println("");
        StaticInfo.enterVisualisingGroup();
        try
        {
            java.io.BufferedReader stdin =
                    new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
            while (true)
            {
                //PROMPT: mauvaise idée /!\
                //System.out.print(ANSI.CYAN + pseudo + "> ");
                String s = stdin.readLine();

                if(s == null){
                    exit();
                }
                else if (s.equals("exit")){
                    exit();
                }
                else if (s.length()>0) {
                    PublicMessage msg = new PublicMessage(pseudo, s);
                    StaticInfo.getChatInterface().publishMsgOnServ(idTopic, msg);
                }
            }
        }
        catch (java.io.IOException ioe)
        {
            ioe.printStackTrace();
        }
    }

    @Override
    public String describe() {
        return "Connect to a topic, see its messages and send messages to it. Arguments: topic id. Type exit to leave.";
    }


    private void exit()
    {
        try {
            StaticInfo.getChatInterface().removeUserFromGroup(pseudo, idTopic);
            System.out.println("");
            System.out.println("\u001B[31m"+pseudo+"\u001B[0m"+" left topic #"+idTopic);
            System.out.println("");
        } catch (Exception e) {
            System.out.println("ERROR: " + "while trying to remove user " + pseudo + " from topic " + idTopic);
            e.printStackTrace();
        }


        StaticInfo.exitVisualisingGroup();
        Shell<PDPublicAPI> shell = new Shell<>();
        shell.system = new PDPublicAPI();
        shell.invite = "Discord";
        /*shell.register(
                GetListGroup.class,
                GetMyListGroup.class,
                VisualiseGroup.class,
                JoinGroup.class
        );*/
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

        //System.exit(0);
    }
}
