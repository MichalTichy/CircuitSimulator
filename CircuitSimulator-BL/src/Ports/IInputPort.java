package Ports;

import Data.IData;

public interface IInputPort extends IPort {
    boolean GetWhetherDataChanged();

    IData GetData();

    void SetDefaultValue();
}
