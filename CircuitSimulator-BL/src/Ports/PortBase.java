/**
 * Port base abstract class
 *
 * @author Prášek Matěj - xprase07
 * @author Tichý Michal - xtichy26
 */
package Ports;

public abstract class PortBase implements IPort {
    protected IPort connectedTo;

    @Override
    public IPort getConnectedPort() {
        return connectedTo;
    }

    /**
     * Method, that connect port
     *
     * @param port port to connect
     */
    protected void Connect(PortBase port) {
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
