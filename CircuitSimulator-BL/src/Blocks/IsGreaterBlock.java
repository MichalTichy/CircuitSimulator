package Blocks;

import Data.BooleanData;
import Data.NumericData;
import Ports.InputNumericPort;
import Ports.OutputBooleanPort;

public class IsGreaterBlock extends BlockBase {

    private InputNumericPort input1;
    private InputNumericPort input2;
    private OutputBooleanPort output;
    private NumericData firstPortData;
    private NumericData secondPortData;

    public IsGreaterBlock() {
        this.input1 = new InputNumericPort();
        this.input2 = new InputNumericPort();
        this.output = new OutputBooleanPort();
        this.inputPorts.add(input1);
        this.inputPorts.add(input2);
        this.outputPorts.add(output);
    }

    @Override
    public void Execute() {
        boolean result = firstPortData.Data > secondPortData.Data;
        output.Send(new BooleanData(result));
    }

    @Override
    public void SaveData() {
        firstPortData = input1.DownloadData();
        secondPortData = input2.DownloadData();
    }
}

