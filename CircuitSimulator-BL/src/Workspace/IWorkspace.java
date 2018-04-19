package Workspace;

import Blocks.IBlock;
import Common.IResetable;
import Common.ITimeTickConsumer;
import Common.ITimeTickProvider;
import Common.IValidatable;

import java.util.Collection;

public interface IWorkspace extends ITimeTickProvider, IResetable, IValidatable {
    void AddBlock(IBlock block);

    void RemoveBlock(IBlock block);

    void Reset();

    Collection<IBlock> GetBlocks();

    void Run();

    void Run(int msPerTick);

    void Break();

    void Step();

    boolean GetIsRunning();

    void Subscribe(ITimeTickConsumer tickConsumer);

    void Unsubscribe(ITimeTickConsumer tickConsumer);

    boolean IsValid();
}
