package Blocks;
import Common.IResetable;
import Common.ITimeTickConsumer;
import Ports.IPort;

import java.util.ArrayList;
import java.util.List;

public abstract class BlockBase implements IBlock {
    private List<IPort> inputPorts = new ArrayList<>();
    private List<IPort> outputPorts = new ArrayList<>();

    public List<IPort> getInputPorts() {
        return inputPorts;
    }

    public List<IPort> getOutputPorts() {
        return outputPorts;
    }

    BlockStatus status=BlockStatus.Idle;

    public BlockStatus GetStatus() {
        return status;
    }

    public void Reset(){
        inputPorts.forEach(t->t.Reset());
        outputPorts.forEach(t->t.Reset());

        status=BlockStatus.Idle;
    }

    public boolean IsValid() {
        for (IPort item:inputPorts) {
            if (!item.IsValid())
                return false;
        }
        for (IPort item:outputPorts) {
            if (!item.IsValid())
                return false;
        }
        return true;
    }
}
