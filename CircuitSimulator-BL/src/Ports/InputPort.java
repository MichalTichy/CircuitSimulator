package Ports;

import Data.IData;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class InputPort implements IPort{
    private IData currentData;
    private IData previousData;
    private IPort connectedTo;

    public void SetData(IData data) {
        this.currentData = data;
    }

    public void Connect(OutputPort port){
        connectedTo=port;
    }

    public void Disconnect(){
        throw new NotImplementedException(); //TODO
    }

    public Boolean GetWhetherDataChanged(){
        return !(currentData == previousData);
    }

    public IData Recieve(){
        previousData=currentData;
        return currentData;
    }


}
