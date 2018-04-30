package Ports;

import Common.IResetable;
import Common.IValidatable;

public interface IPort extends IResetable, IValidatable {
    void Disconnect();
    boolean GetWhetherConnected();
    IPort getConnectedPort();
}
