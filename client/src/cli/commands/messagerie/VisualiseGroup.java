package cli.commands.messagerie;

import api.PDPublicAPI;
import cli.framework.Command;
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
        try
        {
            java.io.BufferedReader stdin =
                    new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
            while (true)
            {
                String s = stdin.readLine();


                if(s == null){
                    exit();
                }
                else if (s.equals("exit")){
                    exit();
                }
                else if (s.length()>0)
                { String msg="";
                   
                  msg="\u001B[47m"+"\u001B[31m"+pseudo + ": " +"\u001B[30m" + s + "\u001B[0m" + "\u001B[40m";

                  //Publish the message :
                  System.out.println("Recu msg"+msg);
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
        System.out.println("");
        System.out.println("\u001B[31m"+pseudo+"\u001B[0m"+" left topic #"+idTopic);
        System.out.println("");

        Shell<PDPublicAPI> shell = new Shell<>();
        shell.system = new PDPublicAPI();
        shell.invite = "Discord>";
        shell.register(
                GetListGroup.class,
                GetMyListGroup.class,
                VisualiseGroup.class,
                JoinGroup.class
        );
        shell.run();

        //System.exit(0);
    }
}
