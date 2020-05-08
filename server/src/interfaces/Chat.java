package interfaces;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Chat {
    private static Chat chat;

    private  List<String> allGroups;
    private List<User> userList;
    private  Map<User, List<String>> groupSubscribedPerson;

    /* /!\ 2 following variables for textual channel #1 only
    * TODO: implement similar structures for other channels
    * */
    private Map<User, Integer> rankOfUsers;
    private Map<User, Boolean> firstTimeInsideChannel1;
    private Map<String, List<PublicMessage>> allTextualChannelMsgLists;

    Chat()  {
        allGroups = new ArrayList<>();
        groupSubscribedPerson = new HashMap<>();
        rankOfUsers = new HashMap<>();
        firstTimeInsideChannel1 = new HashMap<>();
        allTextualChannelMsgLists = new HashMap<>();
        userList = new ArrayList<>();

        allGroups.add("1"); // here, 1 is the name of a channel (not very good idea, should better have
        allGroups.add("2"); // been channel1, for instance!  But I've kept this so this is in line
        allGroups.add("3"); // with the provided documentation (see the PDF file)
        allTextualChannelMsgLists.put("1", new ArrayList<>());
        allTextualChannelMsgLists.put("2", new ArrayList<>());
        allTextualChannelMsgLists.put("3", new ArrayList<>());

        userList.add(new User("Pierre" , "1234" , "PierroDu06"));
        userList.add(new User("Alice" , "4567" , "AliceDu74"));
        userList.add(new User("Alan", "5678", "AlanDu92"));

        for (User user: userList) {
            groupSubscribedPerson.put(user, new ArrayList<>());
            rankOfUsers.put(user, 0);
            firstTimeInsideChannel1.put(user, true);
        }
    }

    public static Chat getChat()  {
        if (chat == null){
            chat = new Chat();
        }
        return chat;
    }

    public void disconnect(String pseudo) {
        User user = getUserByPseudo(pseudo);
        if (user != null)
            firstTimeInsideChannel1.put(user, true);
    }

    public void addMsgToTextualChannelMsgList(String idTopic, PublicMessage msg) {
        allTextualChannelMsgLists.get(idTopic).add(msg);
        broadcastMsgToAllUsersOfTextualChannel(idTopic);;
    }

    public void broadcastMsgToAllUsersOfTextualChannel(String idTopic) {
        int rank = 0;
        int value = 0;
        List<PublicMessage> pubMsgList = allTextualChannelMsgLists.get(idTopic);
        for (User user : userList) {
            if (groupSubscribedPerson.get(user).contains(idTopic)) {
                value = rankOfUsers.get(user);
                if (value < pubMsgList.size()) {
                    if (firstTimeInsideChannel1.get(user)) {
                        try {
                            for (int i = 0; i < value; ++i) {
                                PublicMessage pm = pubMsgList.get(i);
                                user.getPublicMessageInterface().displayMessage(pm);
                            }
                            user.getPublicMessageInterface().displayMessage(new PublicMessage(
                                    null,
                                    "--------------> YOU HAVE NEW MESSAGES <--------------"));
                        } catch (Exception e) {
                            System.out.println("Error user.getPublicMessageInterface().displayMessage(...)\n\t");
                            e.printStackTrace();
                        }
                    }
                    for (int i = value; i < pubMsgList.size(); ++i) {
                        PublicMessage pm = pubMsgList.get(i);
                        try {
                            user.getPublicMessageInterface().displayMessage(pm);
                        } catch (Exception e) {
                            System.out.println("Error user.getPublicMessageInterface().displayMessage(...)\n\t");
                            e.printStackTrace();
                        }
                    }

                }
                rankOfUsers.put(user, pubMsgList.size());
                firstTimeInsideChannel1.put(user, false);
            }
            /*
            try {
                user.getPublicMessageInterface().displayMessage(new PublicMessage(user.getPseudo(), " rank= " + rankOfUsers.get(user)));
            } catch (Exception e) {
                System.out.println("ERROR AAAAH 1");
            } */
        }
    }

    public boolean removeUserFromGroup(String pseudo, String group) throws RemoteException, InterruptedException {
        User user = getUserByPseudo(pseudo);
        if(allGroups.contains(group)) {
            if (groupSubscribedPerson.get(user).contains(group)) {
                groupSubscribedPerson.get(user).remove(group);
                firstTimeInsideChannel1.put(user, false);
                return true;
            }
        }
        return false;
    }

    private User getUserByPseudo(String pseudo) {
        for (User user: userList) {
            if (user.getPseudo().equals(pseudo))
                return user;
        }
        return null;
    }

    public boolean newMsgsFromChannel1(String pseudo) {
        return rankOfUsers.get(getUserByPseudo(pseudo)) < allTextualChannelMsgLists.get("1").size();
    }

    public String checkNewMsgsFromChannel(String pseudo, String idTopic) {
        //User user = getUserByPseudo(pseudo);
        StringBuilder res = new StringBuilder();
        List<PublicMessage> pubMsgList = allTextualChannelMsgLists.get(idTopic);
        rankOfUsers.forEach((key, value) -> {
            if (key.getPseudo().equals(pseudo)) {
                if (value < pubMsgList.size()) {
                    for (int i = value; i < pubMsgList.size(); ++i) {
                        PublicMessage pm = pubMsgList.get(i);
                    }

                }
            }
        });
        return res.toString();
    }

    public List<String> getAllGroups() {
        return allGroups;
    }

    public Map<User, List<String>> getGroupSubscribedPerson() {
        return groupSubscribedPerson;
    }

    public List<User> getAllUser(){
        return new ArrayList<>(groupSubscribedPerson.keySet());
    }
}
