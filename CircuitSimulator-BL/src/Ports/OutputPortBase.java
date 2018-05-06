/**
 * Output port base abstract class, all output port extend this
 *
 * @author Prášek Matěj - xprase07
 * @author Tichý Michal - xtichy26
 */
package Ports;

import Data.IData;

public abstract class OutputPortBase extends PortBase implements IOutputPort {

    /**
     * Send data to connected input port
     * @param data data to send
     * @throws IllegalArgumentException if port is not connected
     */
    protected void Send(IData data) {
        if (connectedTo instanceof InputPortBase) {
            ((InputPortBase) connectedTo).ReceiveData(data);
        } else
            throw new IllegalArgumentException("Port is not connected to input port!");
    }

    @Override
    public boolean IsValid() {
        return super.IsValid() && connectedTo instanceof InputPortBase;
    }

    /**
     * Method that reset port
     */
    public void Reset(){}
}

