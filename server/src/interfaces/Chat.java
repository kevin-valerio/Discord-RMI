package interfaces;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Chat {
    private static Chat chat;

    private  List<String> allGroups;
    private  Map<User, List<String>> groupSubscribedPerson;
    private Map<User, Integer> rankOfUsers;
    private List<String> textualChannelMsgList;
    private Map<String, List<PublicMessage>> allTextualChannelMsgLists;
    private List<User> userList;
    Chat()  {
         allGroups = new ArrayList<>();
         groupSubscribedPerson = new HashMap<>();
         rankOfUsers = new HashMap<>();
         //textualChannelMsgList = new ArrayList<>();
         allTextualChannelMsgLists = new HashMap<>();
         userList = new ArrayList<>();


        allGroups.add("1"); // here, 1 is the name of a channel (not very good idea, should better have 
        allGroups.add("2"); // been channel1, for instance!  But I've kept this so this is in line
        allGroups.add("3"); // with the provided documentation (see the PDF file)
        allTextualChannelMsgLists.put("1", new ArrayList<>());
        allTextualChannelMsgLists.put("2", new ArrayList<>());
        allTextualChannelMsgLists.put("3", new ArrayList<>());

        User user = new User("Pierre" , "1234" , "PierroDu06");
        User user2 = new User("Alice" , "4567" , "AliceDu74");

        userList.add(user);
        userList.add(user2);
        groupSubscribedPerson.put(user, new ArrayList<>());
        groupSubscribedPerson.put(user2, new ArrayList<>());
        rankOfUsers.put(user, 0);
        rankOfUsers.put(user2, 0);
    }

    public static Chat getChat()  {
        if (chat == null){
            chat = new Chat();
        }
        return chat;
    }

    public void addMsgToTextualChannelMsgList(String idTopic, PublicMessage msg) {
        allTextualChannelMsgLists.get(idTopic).add(msg);
        broadcastMsgToAllUsersOfTextualChannel(idTopic);;
    }

    private void broadcastMsgToAllUsersOfTextualChannel(String idTopic) {
        int rank = 0;
        int value = 0;
        List<PublicMessage> pubMsgList = allTextualChannelMsgLists.get(idTopic);
        for (User user : userList) {
            if (groupSubscribedPerson.get(user).contains(idTopic)) {
                value = rankOfUsers.get(user);
                if (value < pubMsgList.size()) {
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
            }
        }
    }

    public boolean removeUserFromGroup(String pseudo, String group) throws RemoteException, InterruptedException {
        User user = getUserByPseudo(pseudo);
        if(allGroups.contains(group)) {
            if (groupSubscribedPerson.get(user).contains(group)) {
                groupSubscribedPerson.get(user).remove(group);
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
