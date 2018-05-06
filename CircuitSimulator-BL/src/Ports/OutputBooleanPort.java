/**
 * Output boolean port class
 *
 * @author Prášek Matěj - xprase07
 * @author Tichý Michal - xtichy26
 */
package Ports;

import Data.BooleanData;

public class OutputBooleanPort extends OutputPortBase {
    /**
     * Method that connect to output port
     *
     * @param port port to connect
     */
    public void Connect(InputBooleanPort port) {
        super.Connect(port);
    }

    /**
     * Send data to connected input port
     *
     * @param data data to send
     * @throws IllegalArgumentException if port is not connected
     */
    public void Send(BooleanData data){
        super.Send(data);
    }
}
