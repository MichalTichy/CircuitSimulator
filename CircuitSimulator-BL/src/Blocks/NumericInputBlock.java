package Blocks;

import Data.NumericData;
import Ports.OutputNumericPort;

import java.io.Serializable;

public class NumericInputBlock extends BlockBase {
    private OutputNumericPort output;
    private boolean executed = false;
    private double value = 0;

    public double getValue() {
        return value;
    }

    public NumericInputBlock() {
        this.output = new OutputNumericPort();
        this.outputPorts.add(output);
    }

    public void SetValue(double value) {
        this.value = value;
        executed = false;
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
        output.Send(new NumericData(value));
    }

    @Override
    public void SaveData() {

    }
}
