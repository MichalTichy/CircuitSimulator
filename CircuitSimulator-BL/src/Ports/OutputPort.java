package Ports;

import Data.IData;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class OutputPort implements IPort{
    public void Connect(InputPort port){
        throw new NotImplementedException(); //todo
    }

    public void Disconnect(){
        throw new NotImplementedException(); //TODO
    }

    public void Send(IData data){
        throw new NotImplementedException(); //TODO
    }
}

