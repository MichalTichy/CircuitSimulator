package Ports;

import Common.IResetable;
import Common.IValidatable;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

import java.io.Serializable;

public abstract class PortBase implements IPort,Serializable {
    protected IPort connectedTo;

    @Override
    public IPort getConnectedPort() {
        return connectedTo;
    }

    protected void Connect(PortBase port) throws IllegalArgumentException {
        if(connectedTo == port) return;
        if(connectedTo!=null) Disconnect();
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
