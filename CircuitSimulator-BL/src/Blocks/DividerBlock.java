package Blocks;

import Data.NumericData;
import Ports.InputNumericPort;
import Ports.OutputNumericPort;

public class DividerBlock extends BlockBase {

    private InputNumericPort input1;
    private InputNumericPort input2;
    private OutputNumericPort output;
    private NumericData firstPortData;
    private NumericData secondPortData;

    public DividerBlock() {
        this.input1 = new InputNumericPort();
        this.input2 = new InputNumericPort();
        this.output = new OutputNumericPort();
        this.inputPorts.add(input1);
        this.inputPorts.add(input2);
        this.outputPorts.add(output);
    }

    @Override
    public void Execute() {
        double result = firstPortData.Data / secondPortData.Data;
        output.Send(new NumericData(result));
    }

    @Override
    public void SaveData() {
        firstPortData = input1.DownloadData();
        secondPortData = input2.DownloadData();
    }
}
