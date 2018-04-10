package Ports;

import Common.IValidatable;
import Data.IData;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public abstract class InputPortBase extends PortBase{
    private IData currentData;
    private IData previousData;

    protected void ReceiveData(IData data) {
        this.currentData = data;
    }


    public boolean GetWhetherDataChanged(){
        return !(currentData == previousData);
    }

    public IData DownloadData(){
        previousData=currentData;
        return currentData;
    }

    @Override
    public boolean IsValid() {
        return super.IsValid() && connectedTo instanceof OutputPortBase;
    }

    public void Reset(){
        this.currentData=null;
        this.previousData=null;
    }
}
