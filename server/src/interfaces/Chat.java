package interfaces;

import java.rmi.RemoteException;
import java.util.*;
import java.util.stream.Stream;

public class Chat {
    private static Chat chat;

    //Basic attributes given by teacher
    private  final List<String> allGroups;
    private final List<User> userList;
    private  final Map<User, List<String>> groupSubscribedPerson;

    //Storage of msg of each Text Channel
    private final Map<String, List<PublicMessage>> allTextualChannelMsgLists;

    //Each user has a private Message Queue for "Private Messaging" service
    private final Map<User, LinkedList<PrivateMessage>> allPrivateMsgQueues;

    /*
    * Following two attributes are for storing UserRanks and
    * FirstTimeInsideTextChannel for each user and for each
    * textual channel
    * */
    private final Map<String, Map<User, Integer>> allRanksOfUsers;
    private final Map<String, Map<User, Boolean>> allFirstTimeInsideTextChannel;


    Chat()  {
        allGroups = new ArrayList<>();
        groupSubscribedPerson = new HashMap<>();
        userList = new ArrayList<>();
        allRanksOfUsers = new HashMap<>();
        allFirstTimeInsideTextChannel = new HashMap<>();
        allTextualChannelMsgLists = new HashMap<>();
        allPrivateMsgQueues = new HashMap<>();

        allGroups.add("1"); // here, 1 is the name of a channel (not very good idea, should better have
        allGroups.add("2"); // been channel1, for instance!  But I've kept this so this is in line
        allGroups.add("3"); // with the provided documentation (see the PDF file)

        for (String idTopic : allGroups) {
            allRanksOfUsers.put(idTopic, new HashMap<>());
            allFirstTimeInsideTextChannel.put(idTopic, new HashMap<>());
            allTextualChannelMsgLists.put(idTopic, new ArrayList<>());

        }

        userList.add(new User("Pierre" , "1234" , "PierroDu06"));
        userList.add(new User("Alice" , "4567" , "AliceDu74"));
        userList.add(new User("Alan", "5678", "AlanDu92"));

        for (User user: userList) {
            groupSubscribedPerson.put(user, new ArrayList<>());
            for (String idTopic: allGroups) {
                allRanksOfUsers.get(idTopic).put(user, 0);
                allFirstTimeInsideTextChannel.get(idTopic).put(user, true);
                allPrivateMsgQueues.put(user, new LinkedList<>());
            }
        }
    }

    public static Chat getChat()  {
        if (chat == null){
            chat = new Chat();
        }
        return chat;
    }

    public void addMessageToPvtMsgQueueOfUser(String pseudo, PrivateMessage msg) {
        User user = getUserByPseudo(pseudo);
        allPrivateMsgQueues.get(user).add(msg);
        System.out.println("now " + pseudo + " size= "
                + allPrivateMsgQueues.get(user).size());
        ClientPrivateMessageInterface pmInterface = user.getPrivateMessageInterface();
        System.out.println("stub of user= " + pseudo + " null? == " + (pmInterface == null));
        user.setLastEmitterPvtMessageInterface(msg.getPmInterface());
        user.setLastEmitterPvtMessagePseudo(msg.getPseudo());
        try {
            if (pmInterface != null) {
                pmInterface.notifyClientOfNewPrivateMessages(msg.getPseudo());
                pmInterface.setPvtMessageLastEmitterData(
                        new PrivateMessage(
                                user.getLastEmitterPvtMessagePseudo(),
                                null,
                                user.getLastEmitterPvtMessageInterface()));
            }
        } catch (Exception e) {
            System.err.println("Error while attempting to add message in server private queue of user " + pseudo);
            e.printStackTrace();
        }
    }

    public LinkedList<PrivateMessage> consumeAllMsgsFromQueueOfUser(String pseudo) {
        LinkedList<PrivateMessage> res = new LinkedList<>();
        LinkedList<PrivateMessage> toConsume = allPrivateMsgQueues.get(getUserByPseudo(pseudo));
        while (!toConsume.isEmpty())
            res.add(toConsume.remove());
        return res;
    }

    public int numberOfNewPrivateMessages(String pseudo) {
        return allPrivateMsgQueues.get(getUserByPseudo(pseudo)).size();
    }

    public void disconnect(String pseudo) {
        User user = getUserByPseudo(pseudo);
        ClientPrivateMessageInterface userToDisconnect;
        LinkedList<PrivateMessage> toTransfer;
        LinkedList<PrivateMessage> serverPvtMessageQueueOfUser = allPrivateMsgQueues.get(user);
        PrivateMessage msg;
        if (user != null) {
            for (String idTopic: allGroups)
                allFirstTimeInsideTextChannel.get(idTopic).put(user, true);
            //user.setPrivateMessageInterface(null);
            //user.setPublicMessageInterface(null);
        }
    }

    public void addMsgToTextualChannelMsgList(String idTopic, PublicMessage msg) {
        allTextualChannelMsgLists.get(idTopic).add(msg);
        broadcastMsgToAllUsersOfTextualChannel(idTopic);;
    }

    public void broadcastMsgToAllUsersOfTextualChannel(String idTopic) {
        //la liste de messages correspondant au channel textuel #idTopic
        List<PublicMessage> pubMsgList = allTextualChannelMsgLists.get(idTopic);
        Stream<User> userStream = userList.stream().parallel();
        userStream.forEach((user) -> {
            int value = 0;
            //si le user considéré est actuellement présent dans le channel textuel #idTopic
            if (groupSubscribedPerson.get(user).contains(idTopic)) {
                value = allRanksOfUsers.get(idTopic).get(user);
                if (value < pubMsgList.size()) {
                    try {
                        if (allFirstTimeInsideTextChannel.get(idTopic).get(user)) {
                            for (int i = 0; i < value; ++i) {
                                PublicMessage pm = pubMsgList.get(i);
                                user.getPublicMessageInterface().displayMessage(pm);
                            }
                            PublicMessage newNotification = new PublicMessage(null, "new_notif");
                            user.getPublicMessageInterface().displayMessage(newNotification);
                        }
                        for (int i = value; i < pubMsgList.size(); ++i) {
                            PublicMessage pm = pubMsgList.get(i);
                            user.getPublicMessageInterface().displayMessage(pm);
                        }
                    } catch (Exception e) {
                        System.out.println("Error user.getPublicMessageInterface().displayMessage(...)\n\t");
                        e.printStackTrace();
                    }
                }
                allRanksOfUsers.get(idTopic).put(user, pubMsgList.size());
                allFirstTimeInsideTextChannel.get(idTopic).put(user, false);
            }
        });
    }

    public boolean removeUserFromGroup(String pseudo, String group) throws RemoteException, InterruptedException {
        User user = getUserByPseudo(pseudo);
        if(allGroups.contains(group)) {
            if (groupSubscribedPerson.get(user).contains(group)) {
                groupSubscribedPerson.get(user).remove(group);
                allFirstTimeInsideTextChannel.get(group).put(user, false);
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

    public String newMsgsFromTextChannels(String pseudo) {
        StringBuilder res = new StringBuilder();
        User user = getUserByPseudo(pseudo);
        if (user != null) {
            for (String idTopic: allGroups) {
                if (allRanksOfUsers.get(idTopic).get(user) < allTextualChannelMsgLists.get(idTopic).size()) {
                    res.append("#");
                    res.append(idTopic);
                    res.append(" ");
                }
            }
            if (res.length() == 0)
                return null;
            return res.toString();
        }
        return null;
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
