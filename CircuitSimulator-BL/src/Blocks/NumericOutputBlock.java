package Blocks;

import Data.NumericData;
import Ports.InputNumericPort;

import java.io.Serializable;

public class NumericOutputBlock extends BlockBase {
    private InputNumericPort input;

    public NumericOutputBlock() {
        this.input = new InputNumericPort();
        this.inputPorts.add(input);
    }

    public double GetValue() {
        if (input.GetWhetherDataChanged()) {
            status = BlockStatus.Working;
        } else {
            status = BlockStatus.Idle;
        }
        NumericData data = input.DownloadData();
        if (data == null) return 0;
        return data.Data;
    }

    @Override
    public void ProcessTick() {
    }

    @Override
    public void Execute() {
    }

    @Override
    public void SaveData() {

    }
}
