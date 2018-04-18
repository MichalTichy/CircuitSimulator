package Blocks;

import Data.BooleanData;
import Ports.InputNumericPort;
import Ports.OutputBooleanPort;

public class IsEqualBlock extends BlockBase {

    private InputNumericPort input1;
    private InputNumericPort input2;
    private OutputBooleanPort output;

    public IsEqualBlock() {
        this.input1 = new InputNumericPort();
        this.input2 = new InputNumericPort();
        this.output = new OutputBooleanPort();
        this.inputPorts.add(input1);
        this.inputPorts.add(input2);
        this.outputPorts.add(output);
    }

    @Override
    public void Execute() {
        boolean result = input1.DownloadData().Data == input2.DownloadData().Data;
        output.Send(new BooleanData(result));
    }

}
