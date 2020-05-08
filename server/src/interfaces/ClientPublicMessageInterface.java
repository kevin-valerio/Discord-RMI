package interfaces;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientPublicMessageInterface extends Remote {
    void displayMessage(PublicMessage message) throws RemoteException, InterruptedException;
}

