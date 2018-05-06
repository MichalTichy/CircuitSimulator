/**
 * Output any port class
 *
 * @author Prášek Matěj - xprase07
 * @author Tichý Michal - xtichy26
 */
package Ports;

import Data.DataType;
import Data.IData;

public class OutputAnyPort extends OutputPortBase {

    private DataType currentDataType = DataType.Any;

    /**
     * Getter for current data type
     *
     * @return current data type
     */
    public DataType getCurrentDataType() {
        return currentDataType;
    }

    /**
     * Method that connect to output port
     *
     * @param port port to connect
     */
    public void Connect(InputPortBase port) {
        super.Connect(port);
    }

    /**
     * Send data to connected input port
     *
     * @param data data to send
     * @throws IllegalArgumentException if port is not connected
     */
    public void Send(IData data) {
        super.Send(data);
    }

    @Override
    public boolean IsValid() {
        if (!super.IsValid())
            return false;

        switch (currentDataType) {
            case Numeric:
                if (connectedTo instanceof InputNumericPort)
                    return true;
                if (connectedTo instanceof InputAnyPort) {
                    DataType connectedToType = ((InputAnyPort) connectedTo).getCurrentDataType();
                    return connectedToType==DataType.Any || connectedToType==DataType.Numeric;
                }
                return false;
            case Boolean:
                if (connectedTo instanceof InputBooleanPort)
                    return true;
                if (connectedTo instanceof InputAnyPort) {
                    DataType connectedToType = ((InputAnyPort) connectedTo).getCurrentDataType();
                    return connectedToType==DataType.Any || connectedToType==DataType.Numeric;
                }
                return false;
            case Any:
                return true;
            default:
                return false;
        }
    }
}
