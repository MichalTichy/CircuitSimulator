package Ports;

import Data.NumericData;

public class InputNumericPort extends InputPortBase {
    public void Connect(OutputNumericPort port) {
        super.Connect(port);
    }

    public NumericData DownloadData() {
        return (NumericData) downloadData();
    }

    @Override
    public void SetDefaultValue() {
        currentData = new NumericData(0);
        previousData = new NumericData(0);
    }
}
