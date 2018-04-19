package Blocks;

import Data.NumericData;
import Ports.InputBooleanPort;
import Ports.OutputNumericPort;

public class CounterBlock extends BlockBase {
    private InputBooleanPort input;
    private InputBooleanPort reset;
    private OutputNumericPort output;
    private double counter = 0;
    private boolean lastValue;

    public CounterBlock() {
        input = new InputBooleanPort();
        reset = new InputBooleanPort();
        output = new OutputNumericPort();
        inputPorts.add(input);
        inputPorts.add(reset);
        outputPorts.add(output);
    }

    private boolean risingEdge() {
        return !lastValue && input.DownloadData().Data;
    }

    @Override
    public void Reset() {
        counter = 0;
        lastValue = false;
        super.Reset();
    }

    @Override
    public void Execute() {
        if (reset.DownloadData().Data) {
            counter = 0;
        } else if (risingEdge()) {
            counter++;
        }
        lastValue = input.DownloadData().Data;
        output.Send(new NumericData(counter));
    }
}
