import interfaces.ConnectionObject;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class LaunchServer {

    public static void main(String[] args) {
        try {

            LocateRegistry.createRegistry(1099);
            ConnectionObject informationImpl = new ConnectionObject();
            String url = "rmi://" + InetAddress.getLocalHost().getHostAddress() + "/Connection";
            Naming.rebind(url, informationImpl);
            System.out.println("Serveur launched");
            System.out.println(InetAddress.getLocalHost().getHostAddress());

        } catch (RemoteException | MalformedURLException | UnknownHostException e) {
            e.printStackTrace();
        }
    }
}