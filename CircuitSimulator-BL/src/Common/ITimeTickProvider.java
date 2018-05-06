/**
 * Interface of all tick providers
 *
 * @author Prášek Matěj - xprase07
 * @author Tichý Michal - xtichy26
 */
package Common;

public interface ITimeTickProvider {
    /**
     * Method, that subscribe tick consumer
     *
     * @param tickConsumer consumer, that will be added to subscribers list
     */
    void Subscribe(ITimeTickConsumer tickConsumer);

    /**
     * Method, that unsubscribe tick consumer
     * @param tickConsumer consumer, that will be added to unsubscribers list
     */
    void Unsubscribe(ITimeTickConsumer tickConsumer);
}
