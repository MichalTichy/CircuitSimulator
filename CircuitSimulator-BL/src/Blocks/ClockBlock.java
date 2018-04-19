package Blocks;

import Data.BooleanData;
import Ports.InputBooleanPort;
import Ports.OutputBooleanPort;

public class ClockBlock extends BlockBase {
    private InputBooleanPort enable;
    private OutputBooleanPort output;
    private boolean value;

    public ClockBlock() {
        this.enable = new InputBooleanPort();
        this.output = new OutputBooleanPort();
        inputPorts.add(enable);
        outputPorts.add(output);
    }

    @Override
    public void ProcessTick() {
        if (enable.DownloadData().Data) {
            Execute();
        } else {
            output.Send(new BooleanData(false));
        }
    }

    @Override
    public void Reset() {
        value = false;
        super.Reset();
    }

    @Override
    public void Execute() {
        value = !value;
        output.Send(new BooleanData(value));
    }
}
