package Ports;

import Data.NumericData;

public class InputNumericPort extends InputPortBase {
    public void Connect(OutputNumericPort port) {
        super.Connect(port);
    }

    public NumericData DownloadData() {
        return (NumericData) downloadData();
    }
}
