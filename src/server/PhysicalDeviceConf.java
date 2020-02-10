package server;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class PhysicalDeviceConf {

    private InetAddress ipAddress;
    private int port;
    private String auth_key;

    public PhysicalDeviceConf(String ipAddress, int port, String auth_key) throws UnknownHostException {
        this.ipAddress = InetAddress.getByName(ipAddress);
        this.port = port;
        this.auth_key = auth_key;
    }

    public InetAddress getIpAddress() {
        return ipAddress;
    }

    public int getPort() {
        return port;
    }

    public String getAuth_key() {
        return auth_key;
    }

    @Override
    public String toString() {
        return "PhysicalDeviceConf{" +
                "ipAddress=" + ipAddress +
                ", port=" + port +
                '}';
    }
}
