package interfaces;

//import javax.jms.JMSException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;

public interface ChatInterface extends Remote {
    public void echo() throws RemoteException, InterruptedException;

    public List<String> getListGroup() throws RemoteException, InterruptedException;

    public List<String> getMyListGroup() throws RemoteException, InterruptedException;

    public boolean joinGroup(String group) throws RemoteException, InterruptedException;

    public List<String> getGroupConnection(String group) throws RemoteException, InterruptedException;//, JMSException;

    public ClientPrivateMessageInterface getUserPrivateMessageInterface(String pseudo) throws RemoteException, InterruptedException;

    public String getPseudo() throws RemoteException, InterruptedException;

    public void publishMsgOnServ(String idTopic, PublicMessage msg) throws RemoteException, InterruptedException;

    public void addMessageToPvtMsgQueueOfUser(String pseudo, PrivateMessage msg) throws RemoteException, InterruptedException;

    public PrivateMessage getCopyOfUniqueMessage(String pseudo) throws RemoteException, InterruptedException;

    public LinkedList<PrivateMessage> consumeAllMsgsFromQueueOfUser(String pseudo) throws RemoteException, InterruptedException;

    public int numberOfNewPrivateMessages(String pseudo) throws RemoteException, InterruptedException;

    public void removeUserFromGroup(String pseudo, String group) throws RemoteException, InterruptedException;

    public String newMsgsFromTextChannels(String pseudo) throws RemoteException, InterruptedException;
}
