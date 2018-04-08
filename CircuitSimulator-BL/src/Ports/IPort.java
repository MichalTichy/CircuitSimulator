package Ports;

public interface IPort {
    void Connect(IPort port) throws IllegalArgumentException;
    void Disconnect();
    boolean GetWhetherConnected();
}
