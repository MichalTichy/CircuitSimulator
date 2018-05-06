/**
 * Counter block class
 *
 * @author Prášek Matěj - xprase07
 * @author Tichý Michal - xtichy26
 */
package Blocks;

import Data.NumericData;
import Ports.InputBooleanPort;
import Ports.OutputNumericPort;

public class CounterBlock extends BlockBase {
    private InputBooleanPort input;
    private InputBooleanPort reset;
    private OutputNumericPort output;
    private double counter = 0;
    private boolean lastValue;

    /**
     * Constructor, that create ports and adds them to collection
     */
    public CounterBlock() {
        input = new InputBooleanPort();
        reset = new InputBooleanPort();
        output = new OutputNumericPort();
        inputPorts.add(input);
        inputPorts.add(reset);
        outputPorts.add(output);
    }

    /**
     * Method, that determine if it was rising edge signal
     *
     * @return true if it war rising edge, false otherwise
     */
    private boolean risingEdge() {
        return !lastValue && input.DownloadData().Data;
    }

    @Override
    public void Reset() {
        counter = 0;
        lastValue = false;
        super.Reset();
    }

    @Override
    public void Execute() {
        if (reset.DownloadData().Data) {
            counter = 0;
        } else if (risingEdge()) {
            counter++;
        }
        lastValue = input.DownloadData().Data;
        output.Send(new NumericData(counter));
    }

    @Override
    public void SaveData() {

    }
}
