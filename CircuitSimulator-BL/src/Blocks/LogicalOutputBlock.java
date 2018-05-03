package Blocks;

import Data.BooleanData;
import Ports.InputBooleanPort;

public class LogicalOutputBlock extends BlockBase {
    private InputBooleanPort input;

    public LogicalOutputBlock() {
        this.input = new InputBooleanPort();
        this.inputPorts.add(input);
    }

    public boolean GetValue() {
        if (input.GetWhetherDataChanged()) {
            status = BlockStatus.Working;
        } else {
            status = BlockStatus.Idle;
        }
        BooleanData data = input.DownloadData();
        if (data == null) return false;
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
