package interfaces;

//import javax.jms.JMSException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ChatInterface extends Remote {
    public void echo() throws RemoteException, InterruptedException;

    public List<String> getListGroup() throws RemoteException, InterruptedException;

    public List<String> getMyListGroup() throws RemoteException, InterruptedException;

    public boolean joinGroup(String group) throws RemoteException, InterruptedException;

    public List<String> getGroupConnection(String group) throws RemoteException, InterruptedException;//, JMSException;

    public ClientPrivateMessageInterface getUserPrivateMassageInterface(String pseudo) throws RemoteException, InterruptedException;
}
