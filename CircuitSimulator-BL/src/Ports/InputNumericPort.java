/**
 * Input numeric port class
 *
 * @author Prášek Matěj - xprase07
 * @author Tichý Michal - xtichy26
 */
package Ports;

import Data.NumericData;

public class InputNumericPort extends InputPortBase {
    /**
     * Method that connect to output port
     *
     * @param port port to connect
     */
    public void Connect(OutputNumericPort port) {
        super.Connect(port);
    }

    /**
     * Set previous data to current data and return it
     *
     * @return current data
     */
    public NumericData DownloadData() {
        return (NumericData) downloadData();
    }

    @Override
    public void SetDefaultValue() {
        currentData = new NumericData(0);
        previousData = new NumericData(0);
    }
}
