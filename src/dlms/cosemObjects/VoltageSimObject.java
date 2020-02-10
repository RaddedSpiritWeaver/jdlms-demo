package dlms.cosemObjects;

import dlms.SimSettings;
import org.openmuc.jdlms.*;
import org.openmuc.jdlms.datatypes.DataObject;

import java.util.Random;

@CosemClass(id = 99)
public class VoltageSimObject extends CosemInterfaceObject {


    private static float minLimit = SimSettings.getVolMin();
    private static float maxLimit = SimSettings.getVolMax();
    private static int changeRate = SimSettings.getChangeRate();

    @CosemAttribute(id = 2, type = DataObject.Type.FLOAT32)
    private DataObject voltage;

    public VoltageSimObject(String obis, DlmsInterceptor interceptor){
        super(obis, interceptor);
        run();
    }

    // TODO: 8/3/19 think of a way to terminate the threads after we are done
    private void run(){
        // generate a new random value within the given range for voltage to simulate sampling
        new Thread(() -> {
            while (true){
                float newRand = minLimit + new Random().nextFloat() * (maxLimit - minLimit);
                voltage = DataObject.newFloat32Data(newRand);
                try {
                    Thread.sleep(changeRate);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    // TODO: 7/28/19 make it so that it also returns something maybe
    @CosemMethod(id = 1)
    public void calibrate(){
        this.voltage = DataObject.newFloat32Data(5.0F);
    }
}