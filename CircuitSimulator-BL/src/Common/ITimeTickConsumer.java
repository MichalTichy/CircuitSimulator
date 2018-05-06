/**
 * Interface of all tick Consumers
 *
 * @author Prášek Matěj - xprase07
 * @author Tichý Michal - xtichy26
 */
package Common;

public interface ITimeTickConsumer {
    /**
     * Method, that determine if is priority consumer
     *
     * @return true if is priority consumer, false otherwise
     */
    boolean IsPriorityConsumer();

    /**
     * Method, that define what to do if process tick comes
     */
    void ProcessTick();
}
