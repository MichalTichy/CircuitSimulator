package Blocks;

import Data.BooleanData;
import Ports.OutputBooleanPort;

public class LogicalInputBlock extends BlockBase {
    private OutputBooleanPort output;
    private boolean executed = false;
    private boolean value = false;

    public void SetValue(boolean value) {
        this.value = value;
        executed = false;
    }

    public boolean GetValue() {
        return value;
    }

    public LogicalInputBlock() {
        this.output = new OutputBooleanPort();
        this.outputPorts.add(output);
    }

    @Override
    public void ProcessTick() {
        if (status == BlockStatus.Working) {
            Execute();
            status = BlockStatus.Idle;
        }
        if (!executed) {
            status = BlockStatus.Working;
            this.executed = true;
        } else {
            status = BlockStatus.Idle;
        }
    }

    @Override
    public void Reset() {
        executed = false;
        super.Reset();
    }

    @Override
    public void Execute() {
        output.Send(new BooleanData(value));
    }

    @Override
    public void SaveData() {

    }
}


