package Ports;

import Data.DataType;
import Data.IData;

public class InputAnyPort extends InputPortBase {

    private DataType currentDataType = DataType.Any;

    public void Connect(OutputPortBase port) {
        super.Connect(port);
    }

    public DataType getCurrentDataType() {
        return currentDataType;
    }

    public void setDataType(DataType dataType) {
        this.currentDataType = currentDataType;
    }

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
                if (connectedTo instanceof  OutputAnyPort)
                {
                    DataType connectedToType = ((OutputAnyPort) connectedTo).getCurrentDataType();
                    return connectedToType==DataType.Any || connectedToType==DataType.Numeric;
                }
                return false;
            case Boolean:
                if (connectedTo instanceof OutputBooleanPort)
                    return true;
                if (connectedTo instanceof  OutputAnyPort)
                {
                     DataType connectedToType = ((OutputAnyPort) connectedTo).getCurrentDataType();
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
