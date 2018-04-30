package Ports;

import Data.DataType;
import Data.IData;

public class OutputAnyPort extends OutputPortBase {

    private DataType currentDataType = DataType.Any;

    public DataType getCurrentDataType() {
        return currentDataType;
    }

    public void setDataType(DataType dataType) {
        this.currentDataType = dataType;
    }

    public void Connect(InputPortBase port)
    {
        super.Connect(port);
    }

    public void Send(IData data)
    {
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
                if (connectedTo instanceof  InputAnyPort)
                {
                    DataType connectedToType = ((InputAnyPort) connectedTo).getCurrentDataType();
                    return connectedToType==DataType.Any || connectedToType==DataType.Numeric;
                }
                return false;
            case Boolean:
                if (connectedTo instanceof InputBooleanPort)
                    return true;
                if (connectedTo instanceof  InputAnyPort)
                {
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
