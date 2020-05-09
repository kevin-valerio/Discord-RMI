package interfaces;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ChatObject extends UnicastRemoteObject implements ChatInterface {

    User user;
    Chat chat;

    protected ChatObject(int port) throws RemoteException {
        super(port);
    }

    protected ChatObject(User user) throws RemoteException {
        this.user = user;
        this.chat = Chat.getChat();
    }

    public void echo() throws RemoteException, InterruptedException {
        Thread.sleep(10000);
    }

    public List<String> getListGroup() throws RemoteException, InterruptedException{
        return chat.getAllGroups();
    }

    public List<String> getMyListGroup() throws RemoteException, InterruptedException{
        return chat.getGroupSubscribedPerson().get(user);
    }

    @Override
    public void publishMsgOnServ(String idTopic, PublicMessage msg) throws RemoteException, InterruptedException {
        chat.addMsgToTextualChannelMsgList(idTopic, msg);
    }

    @Override
    public void loadDirectPvtMsgQueueOfClientFromServer(User user) {
        chat.loadDirectPvtMsgQueueOfClientFromServer(user);
    }

    @Override
    public void addMessageToPvtMsgQueueOfUser(String pseudo, PrivateMessage msg) {
        chat.addMessageToPvtMsgQueueOfUser(pseudo, msg);
    }

    @Override
    public LinkedList<PrivateMessage> consumeAllMsgsFromQueueOfUser(String pseudo) {
        return chat.consumeAllMsgsFromQueueOfUser(pseudo);
    }

    @Override
    public int numberOfNewPrivateMessages(String pseudo) {
        return chat.numberOfNewPrivateMessages(pseudo);
    }

    @Override
    public String newMsgsFromTextChannels(String pseudo) throws RemoteException, InterruptedException {
        return chat.newMsgsFromTextChannels(pseudo);
    }

        public boolean joinGroup(String group) throws RemoteException, InterruptedException {

        System.out.println(user.getLogin() + " is trying to join topic #" + group);

        if(chat.getAllGroups().contains(group)) {
            if (!chat.getGroupSubscribedPerson().get(user).contains(group)) {
                chat.getGroupSubscribedPerson().get(user).add(group);
            }


            System.out.println(user.getLogin() + " has joined the topic #" + group);
            chat.broadcastMsgToAllUsersOfTextualChannel(group);

            return true;
        }
        return false;
    }

    public void removeUserFromGroup(String pseudo, String group) throws RemoteException, InterruptedException {
        System.out.println(user.getLogin() + " is trying to left topic #" + group);
        if (chat.removeUserFromGroup(pseudo, group))
            System.out.println(user.getLogin() + " has left topic #" + group);
        else
            System.out.println("ERROR: " + user.getLogin() + " still in topic #" + group);
    }

    @Override
    public List<String> getGroupConnection(String group) throws RemoteException, InterruptedException {

        System.out.println(user.getLogin() + " is trying to enter topic #" + group);

        joinGroup(group);

        List<String> list = new ArrayList<>();
        list.add("user");
        list.add("password");
        list.add(user.getPseudo());

        System.out.println(user.getLogin() + " has entered topic #" + group);

        return list;
    }

    @Override
    public ClientPrivateMessageInterface getUserPrivateMessageInterface(String pseudo) throws RemoteException, InterruptedException {
        for (User user : chat.getAllUser()) {
            if (user.getPseudo().equals(pseudo)) {
                return user.getPrivateMessageInterface();
            }
        }
        return null;
    }

    @Override
    public String getPseudo() throws RemoteException, InterruptedException {
        return user.getPseudo();
    }
}
