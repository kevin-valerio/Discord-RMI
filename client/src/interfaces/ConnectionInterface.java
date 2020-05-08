package interfaces;

//import javax.jms.JMSException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ConnectionInterface extends Remote {
    public void echo() throws RemoteException, InterruptedException;
    
    public ChatInterface connect(
            String user, String password,
            ClientPrivateMessageInterface clientPrivateMessageInterface,
            ClientPublicMessageInterface clientPublicMessageInterface) throws RemoteException, InterruptedException;//, JMSException;
    public void disconnect(String pseudo) throws RemoteException, InterruptedException;
}