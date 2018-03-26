package Ports;

import Data.IData;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class InputPort implements IPort{
    private IData currentData;
    private IData previousData;

    public void SetData(IData data) {
        this.currentData = data;
    }

    public void Connect(OutputPort port){
        throw new NotImplementedException(); //todo
    }

    public void Disconnect(){
        throw new NotImplementedException(); //TODO
    }

    public Boolean GetWhetherDataChanged(){
        return !(currentData == previousData);
    }

    public IData Recieve(){
        throw new NotImplementedException(); //TODO
    }
}
