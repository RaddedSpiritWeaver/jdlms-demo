package dlms;

import server.CsvHandler;

import java.net.UnknownHostException;
import java.util.List;

public class Simulation {

    private static String serverListPath = SimSettings.getDefalutServerList();

    public Simulation(){
        CsvHandler.generateSimulationData(serverListPath, SimSettings.getPhysicalDeviceCount(), 50000);
        this.runServers();
    }

    private void runServers(){
        List<String[]> csv = CsvHandler.read(serverListPath);
        for(int i = 1; i < csv.size(); i++){
            String[] line = csv.get(i);
            try {
                new DlmsPhysicalDevice(line[0], Integer.parseInt(line[1]), line[2]);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
    }

}
