package Ports;

import Data.IData;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class OutputPort extends PortBase{

    @Override
    public void Connect(IPort port) {
        if(port instanceof OutputPort)
            throw new IllegalArgumentException("Its not possible to connect OutputPort to OutputPort.");
        super.Connect(port);
    }

    public void Send(IData data){
       if (connectedTo instanceof InputPort){
           ((InputPort) connectedTo).ReceiveData(data);
       }
       else
           throw new IllegalArgumentException("Port is not connected to input port!");
    }
}

