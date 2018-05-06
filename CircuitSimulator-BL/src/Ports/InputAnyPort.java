/**
 * Input any port class
 *
 * @author Prášek Matěj - xprase07
 * @author Tichý Michal - xtichy26
 */
package Ports;

import Data.DataType;
import Data.IData;

public class InputAnyPort extends InputPortBase {

    private DataType currentDataType = DataType.Any;

    /**
     * Method, that connect to output port
     *
     * @param port port to connect
     */
    public void Connect(OutputPortBase port) {
        super.Connect(port);
    }

    /**
     * Getter for current data type
     * @return current data type
     */
    public DataType getCurrentDataType() {
        return currentDataType;
    }

    /**
     *
     * @return
     */
    public IData DownloadData() {
        return downloadData();
    }

    @Override
    public boolean IsValid() {
        if (!super.IsValid())
            return false;

        switch (currentDataType) {
            case Numeric:
                if (connectedTo instanceof OutputNumericPort)
                    return true;
                if (connectedTo instanceof OutputAnyPort) {
                    DataType connectedToType = ((OutputAnyPort) connectedTo).getCurrentDataType();
                    return connectedToType==DataType.Any || connectedToType==DataType.Numeric;
                }
                return false;
            case Boolean:
                if (connectedTo instanceof OutputBooleanPort)
                    return true;
                if (connectedTo instanceof OutputAnyPort) {
                    DataType connectedToType = ((OutputAnyPort) connectedTo).getCurrentDataType();
                    return connectedToType == DataType.Any || connectedToType == DataType.Numeric;
                }
                return false;
            case Any:
                return true;
            default:
                return false;
        }
    }

    @Override
    public void SetDefaultValue() {

    }
}
