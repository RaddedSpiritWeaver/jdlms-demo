package dlms;

import org.openmuc.jdlms.AuthenticationMechanism;
import org.openmuc.jdlms.DlmsServer;
import org.openmuc.jdlms.LogicalDevice;
import org.openmuc.jdlms.SecuritySuite;
import utils.ConsoleColors;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import static javax.xml.bind.DatatypeConverter.parseHexBinary;

public class DlmsPhysicalDevice {

    // device specific data used to simulate a Smart Meter (logical device)
    // Note these are just for simulation purposes
    private static final String MANUFACTURE_ID = "ISE";
    private static final long DEVICE_ID = 9999L;
    private static final String LOGICAL_DEVICE_ID = "L_D_I"; // basically the name of the device


    private InetAddress ipAddress;
    private int port;
    private byte[] authKey;

    private SecuritySuite securitySuite;

    public DlmsPhysicalDevice(String ipAddress, int port, String authKey) throws UnknownHostException {
        this.ipAddress = InetAddress.getByName(ipAddress);
        this.port = port;
        this.authKey = parseHexBinary(authKey);
        // set up device security settings
        // for now we only use a security key and use low authentication mechanism
        this.securitySuite = SecuritySuite.builder()
                .setAuthenticationKey(this.authKey)
                .setPassword(this.authKey)
                .setAuthenticationMechanism(AuthenticationMechanism.LOW)
                .build();
        this.run();
    }

    private void run(){
        // DlmsServer class represents a simulated physical device
        DlmsServer serverConnection;
        List<LogicalDevice> logicalDeviceList = new ArrayList<>();

        // now we have to set up our logical devices statically for simulation
        // each COSEM Smart Meter has a Management Logical device installed with ID = 1 which we need to setup first
        LogicalDevice managmentLogicalDevice = new LogicalDevice(1, LOGICAL_DEVICE_ID, MANUFACTURE_ID, DEVICE_ID);
        // set a security suite for public clients (client id = 16)
        managmentLogicalDevice.addRestriction(16, this.securitySuite);
        // note we dont want to add any extra objects to our management logical device so we will just add it to the list
        logicalDeviceList.add(managmentLogicalDevice);

        // generate logical devices and store them
        for(int i = 0; i < SimSettings.getLogicalDeviceCount(); i++){
            int index = SimSettings.getLogicalDeviceStartIndex() + i;
            logicalDeviceList.add(new SampleLogicalDevie(index,LOGICAL_DEVICE_ID, MANUFACTURE_ID, DEVICE_ID, securitySuite));
        }

        // now we have to register our logical devices on the physical device and create a tcp connection
        try {
            serverConnection = DlmsServer.tcpServerBuilder(this.port).registerLogicalDevice(logicalDeviceList).build();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("error in making server connection for port: " + this.port);
        }

        System.out.println(ConsoleColors.GREEN + String.format("DLMS/COSEM simulated physical device running on port: %d", port) + ConsoleColors.RESET);
    }
}
