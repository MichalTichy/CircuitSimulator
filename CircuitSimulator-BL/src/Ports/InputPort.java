package Ports;

import Data.IData;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class InputPort extends PortBase{
    private IData currentData;
    private IData previousData;

    public void SetData(IData data) {
        this.currentData = data;
    }

    @Override
    public void Connect(IPort port) {
        if(port instanceof InputPort) ;    //TODO exception
        super.Connect(port);
    }

    public Boolean GetWhetherDataChanged(){
        return !(currentData == previousData);
    }

    public IData Recieve(){
        previousData=currentData;
        return currentData;
    }


}
