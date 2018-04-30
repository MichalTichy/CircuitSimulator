package Ports;

import Common.IResetable;
import Common.IValidatable;

public abstract class PortBase implements IPort {
    protected IPort connectedTo;

    @Override
    public IPort getConnectedPort() {
        return connectedTo;
    }

    protected void Connect(PortBase port) throws IllegalArgumentException {
        if(connectedTo == port) return;
        connectedTo = port;
        port.Connect(this);
    }

    public void Disconnect(){
        if(connectedTo == null) return;
        IPort toDisconnect = connectedTo;
        connectedTo = null;
        toDisconnect.Disconnect();
    }

    public boolean GetWhetherConnected() {
        return connectedTo!=null;
    }

    public boolean IsValid(){
        return connectedTo!=null;
    }
}
