/**
 * Interface of workspace
 *
 * @author Prášek Matěj - xprase07
 * @author Tichý Michal - xtichy26
 */
package Workspace;

import Blocks.IBlock;
import Common.IResetable;
import Common.ITimeTickConsumer;
import Common.ITimeTickProvider;
import Common.IValidatable;

import java.util.Collection;

public interface IWorkspace extends ITimeTickProvider, IResetable, IValidatable {

    /**
     * Method that adds block to the collection and subscribe it
     *
     * @param block block to add and subscribe
     */
    void AddBlock(IBlock block);

    /**
     * Method that removes block to the collection and unsubscribe it
     *
     * @param block block to remove and unsubscribe
     */
    void RemoveBlock(IBlock block);

    /**
     * Method that reset all blocks and ports
     */
    void Reset();

    /**
     * Getter for collection of blocks
     * @return collection of blocks
     */
    Collection<IBlock> GetBlocks();

    /**
     * Method that do step every 500ms
     */
    void Run();

    /**
     * Method that do step every msPerTick value
     * @param msPerTick frequency of steps
     */
    void Run(int msPerTick);

    /**
     * Method that stop run
     */
    void Break();

    /**
     * Method that provides tick for every subscriber
     */
    void Step();

    /**
     * Getter for is running
     * @return true if is running, false otherwise
     */
    boolean GetIsRunning();

    void Subscribe(ITimeTickConsumer tickConsumer);

    void Unsubscribe(ITimeTickConsumer tickConsumer);

    /**
     * Method that validate workspace
     * @return true if is valid, false otherwise
     */
    boolean IsValid();

    /**
     * Sets timer to null;
     */
    void ClearTimer();
}
