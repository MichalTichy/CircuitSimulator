package Blocks;

import Data.NumericData;
import Ports.OutputNumericPort;

public class NumericInputBlock extends BlockBase {
    private OutputNumericPort output;
    private boolean executed = false;
    private double value = 0;

    public NumericInputBlock() {
        this.output = new OutputNumericPort();
        this.outputPorts.add(output);
    }

    public void SetValue(double value) {
        this.value = value;
    }

    @Override
    public void ProcessTick() {
        if (!executed) {
            this.executed = true;
            Execute();
        }
    }

    @Override
    public void Execute() {
        output.Send(new NumericData(value));
    }
}
