package Ports;

public interface IPort {
    void Connect(IPort port);
    void Disconnect();
}
