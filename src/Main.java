/*
    IMPORTANT NOTE: the project should be built with java 8
 */

import dlms.Simulation;
import server.CsvHandler;
import server.Server;

public class Main {

    public static void main(String[] args) {
        // TODO: 7/27/19 simulation server setup based on the config file
        new Simulation();
        // TODO: 7/27/19 run our actual server class that reads from devices and write a log file
        new Server();
    }
    
}
