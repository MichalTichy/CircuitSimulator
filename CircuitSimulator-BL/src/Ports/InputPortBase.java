package Ports;

import Data.IData;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public abstract class InputPortBase extends PortBase{
    private IData currentData;
    private IData previousData;

    protected void ReceiveData(IData data) {
        this.currentData = data;
    }

    public Boolean GetWhetherDataChanged(){
        return !(currentData == previousData);
    }

    public IData DownloadData(){
        previousData=currentData;
        return currentData;
    }


}
