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
            status = BlockStatus.Working;
            this.executed = true;
            Execute();
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
        output.Send(new NumericData(value));
    }

    @Override
    public void SaveData() {

    }
}
