package Blocks;

import Data.BooleanData;
import Ports.InputBooleanPort;
import Ports.OutputBooleanPort;

public class LogicANDBlock extends BlockBase {

    private InputBooleanPort input1;
    private InputBooleanPort input2;
    private OutputBooleanPort output;

    public LogicANDBlock() {
        this.input1 = new InputBooleanPort();
        this.input2 = new InputBooleanPort();
        this.output = new OutputBooleanPort();
        this.inputPorts.add(input1);
        this.inputPorts.add(input2);
        this.outputPorts.add(output);
    }

    @Override
    public void Execute() {
        boolean result = input1.DownloadData().Data && input2.DownloadData().Data;
        output.Send(new BooleanData(result));
    }

}

