package Common;

public interface ITimeTickConsumer {
    boolean IsPriorityConsumer();
    void ProcessTick();
}
