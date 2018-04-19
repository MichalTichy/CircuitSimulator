package Blocks;

import Ports.IInputPort;
import Ports.IOutputPort;
import Ports.IPort;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public abstract class BlockBase implements IBlock {
    protected List<IInputPort> inputPorts = new ArrayList<>();
    protected List<IOutputPort> outputPorts = new ArrayList<>();
    protected BlockStatus status = BlockStatus.Idle;
    protected Point2D position;

    public List<IInputPort> GetInputPorts() {
        return inputPorts;
    }

    public List<IOutputPort> GetOutputPorts() {
        return outputPorts;
    }

    public Point2D GetPosition(){
        return position;
    }

    public BlockStatus GetStatus() {
        return status;
    }

    public void Reset(){
        inputPorts.forEach(t->t.Reset());
        outputPorts.forEach(t->t.Reset());

        status=BlockStatus.Idle;
    }

    @Override
    public boolean IsPriorityConsumer() {
        return true;
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

    @Override
    public void ProcessTick() {
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
