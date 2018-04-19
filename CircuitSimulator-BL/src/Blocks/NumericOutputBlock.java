package Blocks;

import Ports.InputNumericPort;

public class NumericOutputBlock extends BlockBase {
    private InputNumericPort input;

    public NumericOutputBlock() {
        this.input = new InputNumericPort();
        this.inputPorts.add(input);
    }

    public double GetValue() {
        return input.DownloadData().Data;
    }

    @Override
    public void Execute() {

    }
}
