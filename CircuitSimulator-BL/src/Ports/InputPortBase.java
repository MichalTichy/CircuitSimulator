/**
 * Input port base abstract class, all input port extend this
 *
 * @author Prášek Matěj - xprase07
 * @author Tichý Michal - xtichy26
 */
package Ports;

import Data.IData;

public abstract class InputPortBase extends PortBase implements IInputPort {
    protected IData currentData;
    protected IData previousData;

    /**
     * Receive data and save it as current data
     *
     * @param data received data
     */
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
        return !(currentData.equals(previousData));
    }

    /**
     * Set previous data to current data and return it
     * @return current data
     */
    protected IData downloadData() {
        previousData=currentData;
        return currentData;
    }

    /**
     * Method that validate port
     * @return true if is valid, false otherwise
     */
    @Override
    public boolean IsValid() {
        return super.IsValid() && connectedTo instanceof OutputPortBase;
    }

    /**
     * Set port data to null
     */
    public void Reset(){
        this.currentData=null;
        this.previousData=null;
    }
}
