package Ports;

public abstract class PortBase implements IPort {
    protected IPort connectedTo;

    public void Connect(IPort port){
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
}
