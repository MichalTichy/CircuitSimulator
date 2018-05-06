package Workspace;

import Blocks.IBlock;
import Common.ITimeTickConsumer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;

public class Workspace implements IWorkspace, Serializable {
    private Collection<ITimeTickConsumer> subscribers = new ArrayList<>();
    private Collection<IBlock> blocks = new ArrayList<>();
    private Timer timer;
    private boolean isRunning;

    @Override
    public void AddBlock(IBlock block) {
        blocks.add(block);
        Subscribe(block);
    }

    @Override
    public void RemoveBlock(IBlock block) {
        Unsubscribe(block);
        blocks.remove(block);
    }


    @Override
    public void Reset() {
        for (IBlock block : blocks) {
            block.Reset();
        }
    }

    @Override
    public Collection<IBlock> GetBlocks() {
        return blocks;
    }

    @Override
    public void Run() {
        Run(500);
    }

    @Override
    public void Run(int msPerTick) { //TODO check multiple runs
        isRunning = true;
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Step();
            }
        }, msPerTick, msPerTick);
    }

    @Override
    public void Break() {
        timer.cancel();
        isRunning = false;
    }

    @Override
    public void Step() {
        for (ITimeTickConsumer subscriber : subscribers) {
            if (subscriber.IsPriorityConsumer()) {
                subscriber.ProcessTick();
            }
        }
        for (ITimeTickConsumer subscriber : subscribers) {
            if (!subscriber.IsPriorityConsumer()) {
                subscriber.ProcessTick();
            }
        }
    }

    @Override
    public boolean GetIsRunning() {
        return isRunning;
    }

    @Override
    public void Subscribe(ITimeTickConsumer tickConsumer) {
        subscribers.add(tickConsumer);
    }

    @Override
    public void Unsubscribe(ITimeTickConsumer tickConsumer) {
        subscribers.remove(tickConsumer);
    }

    private Collection<IBlock> getInvalidBlocks() {
        Collection<IBlock> invalidBlocks = new ArrayList<>();
        for (IBlock block : blocks) {
            if (!block.IsValid()) {
                invalidBlocks.add(block);
            }
        }
        return invalidBlocks;
    }

    @Override
    public boolean IsValid() {
        return getInvalidBlocks().isEmpty();
    }
}
