package Ports;

import Data.IData;

public abstract class InputPortBase extends PortBase implements IInputPort {
    private IData currentData;
    private IData previousData;

    protected void ReceiveData(IData data) {
        this.currentData = data;
    }

    @Override
    public IData GetData() {
        return currentData;
    }

    @Override
    public boolean GetWhetherDataChanged(){
        if (currentData == null) return false;
        return !(currentData == previousData);
    }

    protected IData downloadData() {
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
