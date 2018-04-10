package Ports;

import Data.IData;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class InputPort extends PortBase{
    private IData currentData;
    private IData previousData;

    public void ReceiveData(IData data) {
        this.currentData = data;
    }

    @Override
    public void Connect(IPort port) {
        if(port instanceof InputPort)
            throw new IllegalArgumentException("Its not possible to connect InputPort to InputPort.");
        super.Connect(port);
    }

    public Boolean GetWhetherDataChanged(){
        return !(currentData == previousData);
    }

    public IData DownloadData(){
        previousData=currentData;
        return currentData;
    }


}
