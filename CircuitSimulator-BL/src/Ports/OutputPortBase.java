package Ports;

import Data.IData;

public abstract class OutputPortBase extends PortBase{

    protected void Send(IData data){
       if (connectedTo instanceof InputPortBase){
           ((InputPortBase) connectedTo).ReceiveData(data);
       }
       else
           throw new IllegalArgumentException("Port is not connected to input port!");
    }

    @Override
    public boolean IsValid() {
        return super.IsValid() && connectedTo instanceof InputPortBase;
    }
}

