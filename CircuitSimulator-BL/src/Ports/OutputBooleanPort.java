package Ports;

import Data.BooleanData;

public class OutputBooleanPort extends OutputPortBase {
    public void Connect(InputBooleanPort port) {
        super.Connect(port);
    }
    public void Send(BooleanData data){
        super.Send(data);
    }
}
