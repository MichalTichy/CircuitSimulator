package Ports;

import Data.BooleanData;
import Data.NumericData;

public class OutputNumericPort extends OutputPortBase {
    public void Connect(OutputNumericPort port) {
        super.Connect(port);
    }
    public void Send(NumericData data){
        super.Send(data);
    }
}
