package server;


import dlms.SimSettings;
import org.openmuc.jdlms.*;
import utils.ConsoleColors;

import java.io.IOException;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Vector;

import static javax.xml.bind.DatatypeConverter.parseHexBinary;

public class Server {

    private static String serverListPath = SimSettings.getDefalutServerList();

    private static String mongoAddr = SimSettings.getMongoAddress();
    private static String dbName = SimSettings.getDatabaseName();
    private static String collectionName = SimSettings.getCollectionName();

    private Vector<PhysicalDeviceConf> physicalDevices = null;
    private DatabaseHandler databaseHandler;

    public Server(){
        this.physicalDevices = new Vector<>();
        // read the config file for physical devices
        this.loadPhysicalDevices();
        this.databaseHandler = new DatabaseHandler(mongoAddr, dbName, collectionName);
        this.collectData();
    }

    private void managerObjList(){
        // reach our to each physical devices logical manager and get its object list
        for(PhysicalDeviceConf conf : this.physicalDevices) {
            // TODO: 7/27/19 check if we first need to check its security mechanism thing
            try {

                SecuritySuite securitySuite = SecuritySuite.builder()
                        .setAuthenticationKey(parseHexBinary(conf.getAuth_key()))
                        .setPassword(parseHexBinary(conf.getAuth_key()))
                        .setAuthenticationMechanism(AuthenticationMechanism.LOW)
                        .build();

                TcpConnectionBuilder connectionBuilder = new TcpConnectionBuilder(conf.getIpAddress())
                        .setLogicalDeviceId(1)
                        .setPort(conf.getPort())
                        .setSecuritySuite(securitySuite);

                DlmsConnection connection = connectionBuilder.build();

                GetResult objectResult = connection.get(new AttributeAddress(15, "0.0.40.0.0.255", 2));
                GetResult logicalDevicesResult = connection.get(new AttributeAddress(17, "0.0.41.0.0.255", 2));

                System.out.println(ConsoleColors.PURPLE + "Object List Reply" + ConsoleColors.RESET);
                System.out.println(ConsoleColors.CYAN + conf.toString() + ConsoleColors.RESET);
                System.out.println(objectResult.getResultData().toString());

                System.out.println(ConsoleColors.BLUE_BACKGROUND + ConsoleColors.BLUE + "###########################" + ConsoleColors.RESET);

                System.out.println(ConsoleColors.PURPLE + "Logical Device Reply" + ConsoleColors.RESET);
                System.out.println(ConsoleColors.CYAN + conf.toString() + ConsoleColors.RESET);
                System.out.println(logicalDevicesResult.getResultData().toString());

                // terminate the connection after use
                connection.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void collectData(){

        while (true){
            System.out.println(ConsoleColors.BLUE_BACKGROUND + ConsoleColors.BLUE + "###########################" + ConsoleColors.RESET);
            try{
                for(PhysicalDeviceConf conf: this.physicalDevices){
                    SecuritySuite securitySuite = SecuritySuite.builder()
                            .setAuthenticationKey(parseHexBinary(conf.getAuth_key()))
                            .setPassword(parseHexBinary(conf.getAuth_key()))    // change to 12345678 ?
                            .setAuthenticationMechanism(AuthenticationMechanism.LOW)
                            .build();

                    TcpConnectionBuilder connectionBuilder = new TcpConnectionBuilder(conf.getIpAddress())
                            .setLogicalDeviceId(1)
                            .setPort(conf.getPort())
                            .setSecuritySuite(securitySuite);

                    DlmsConnection connection = connectionBuilder.build();


                    //access each logical device
                    for (int i = 0; i < SimSettings.getLogicalDeviceCount(); i++) {
                        int logicalDeviceId = SimSettings.getLogicalDeviceStartIndex() + i;
                        connectionBuilder = new TcpConnectionBuilder(conf.getIpAddress())
                                .setLogicalDeviceId(logicalDeviceId)
                                .setPort(conf.getPort())
                                .setSecuritySuite(securitySuite);
                        connection = connectionBuilder.build();

                        // access each Cosem object
                        for (int j = 0; j < SimSettings.getCosemObjectCount(); j++) {
                            int objIndex = j + SimSettings.getCosemObjectStartIndex();
                            String code = String.format(SimSettings.getObisFormat(), objIndex);
                            System.out.print(ConsoleColors.PURPLE_BOLD_BRIGHT);
                            System.out.println(String.format("logical device id:%d, Physical device id:", i) + conf.toString());
                            System.out.print(ConsoleColors.GREEN_BOLD_BRIGHT);
                            System.out.println("Voltage meter instance:" + code);
                            System.out.print(ConsoleColors.RESET);
                            // TODO: 7/28/19 check if the request was successful first
                            String result = connection.get(new AttributeAddress(SimSettings.getCosemClassId(), code, 2)).getResultData().toString();
                            String[] tokens = result.split(":");
                            String value = tokens[1].trim();
                            String dateTime = LocalDateTime.now().toString();
                            databaseHandler.saveRecord(conf, i, code, value, dateTime);
                            System.out.println(result);
                        }

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            // wait for some time before collecting another set of data
            try {
                Thread.sleep(SimSettings.getDataGatherSleepTime());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadPhysicalDevices(){
        List<String[]> csv = CsvHandler.read(serverListPath);
        for(int i = 1; i < csv.size(); i++){
            String[] line = csv.get(i);
            try {
                physicalDevices.add(new PhysicalDeviceConf(line[0], Integer.parseInt(line[1]), line[2]));
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
    }

}
