package Common;

public interface ITimeTickProvider {
    void Subscribe(ITimeTickConsumer tickConsumer);
    void Unsubscribe(ITimeTickConsumer tickConsumer);
}
