package Blocks;
import Ports.IInputPort;
import Ports.IOutputPort;
import Ports.IPort;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class BlockBase implements IBlock {
    private List<IInputPort> inputPorts = new ArrayList<>();
    private List<IOutputPort> outputPorts = new ArrayList<>();

    public List<IInputPort> getInputPorts() {
        return inputPorts;
    }

    public List<IOutputPort> getOutputPorts() {
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

    public void TickDetected(){
        boolean execute = false;
        if(inputPorts.isEmpty())    execute = true;
        for (IInputPort port: inputPorts) {
            if(port.GetWhetherDataChanged()){
                execute = true;
                break;
            }
        }
        if (execute){
            status = BlockStatus.Working;
            this.Execute();
        }else{
            status = BlockStatus.Idle;
        }
    }
}
