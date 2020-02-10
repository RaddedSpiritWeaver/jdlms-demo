package dlms;

import dlms.cosemObjects.VoltageSimObject;
import org.openmuc.jdlms.*;

public class SampleLogicalDevie extends LogicalDevice {


    // create a sample logical device and set its objects
    public SampleLogicalDevie(int logicalDeviceId, String logicalDeviceName, String manufacturerId, long deviceId, SecuritySuite securitySuite) {
        super(logicalDeviceId, logicalDeviceName, manufacturerId, deviceId);
        // make sure to add a security restriction
        this.addRestriction(16, securitySuite);
        init();
    }

    private void init(){
        // use the same dlms interceptor for every object - no need for extra logic just call the proceed method
        DlmsInterceptor interceptor = DlmsInvocationContext::proceed;
        // create COSEM objects and register them on the logical device
        for(int i = 0; i < SimSettings.getCosemObjectCount(); i++){
            int index = i + SimSettings.getCosemObjectStartIndex();
            String code = String.format(SimSettings.getObisFormat(), index);
            VoltageSimObject voltageSim = new VoltageSimObject(code, interceptor);
            this.registerCosemObject(voltageSim);
        }
    }
}
