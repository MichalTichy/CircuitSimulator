package Blocks;

import Ports.InputBooleanPort;

public class LogicalOutputBlock extends BlockBase {
    private InputBooleanPort input;

    public LogicalOutputBlock() {
        this.input = new InputBooleanPort();
        this.inputPorts.add(input);
    }

    public boolean GetValue() {
        return input.DownloadData().Data;
    }

    @Override
    public void Execute() {

    }
}
