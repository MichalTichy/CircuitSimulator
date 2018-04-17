package Ports;

import Data.BooleanData;

public class InputBooleanPort extends InputPortBase {
    public void Connect(OutputBooleanPort port) {
        super.Connect(port);
    }

    public BooleanData DownloadData() {
        return (BooleanData) downloadData();
    }
}
