/**
 * Input boolean port class
 *
 * @author Prášek Matěj - xprase07
 * @author Tichý Michal - xtichy26
 */
package Ports;

import Data.BooleanData;

public class InputBooleanPort extends InputPortBase {
    /**
     * Method that connect to output port
     *
     * @param port port to connect
     */
    public void Connect(OutputBooleanPort port) {
        super.Connect(port);
    }

    /**
     * Set previous data to current data and return it
     *
     * @return current data
     */
    public BooleanData DownloadData() {
        return (BooleanData) downloadData();
    }

    @Override
    public void SetDefaultValue() {
        currentData = new BooleanData(false);
        previousData = new BooleanData(false);
    }
}
