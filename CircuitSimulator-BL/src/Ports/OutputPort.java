package Ports;

import Data.IData;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class OutputPort extends PortBase{
    private IData currentData;
    private IData previousData;

    @Override
    public void Connect(IPort port) {
        if(port instanceof OutputPort)
            throw new IllegalArgumentException("Its not possible to connect OutputPort to OutputPort.");
        super.Connect(port);
    }

    public void Send(IData data){
        throw new NotImplementedException(); //TODO
    }
}

