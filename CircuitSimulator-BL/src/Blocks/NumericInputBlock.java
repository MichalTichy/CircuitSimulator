/**
 * Numeric input block class
 *
 * @author Prášek Matěj - xprase07
 * @author Tichý Michal - xtichy26
 */
package Blocks;

import Data.NumericData;
import Ports.OutputNumericPort;

public class NumericInputBlock extends BlockBase {
    private OutputNumericPort output;
    private boolean executed = false;
    private double value = 0;

    /**
     * Constructor that create ports and adds them to collection
     */
    public NumericInputBlock() {
        this.output = new OutputNumericPort();
        this.outputPorts.add(output);
    }

    /**
     * Getter of block value
     *
     * @return block value
     */
    public double getValue() {
        return value;
    }

    /**
     * Setter of block value to send
     *
     * @param value new block value
     */
    public void SetValue(double value) {
        this.value = value;
        executed = false;
    }

    @Override
    public void ProcessTick() {
        if (status == BlockStatus.Working) {
            Execute();
            status = BlockStatus.Idle;
        }
        if (!executed) {
            status = BlockStatus.Working;
            this.executed = true;
        } else {
            status = BlockStatus.Idle;
        }
    }

    @Override
    public void Reset() {
        executed = false;
        super.Reset();
    }

    @Override
    public void Execute() {
        output.Send(new NumericData(value));
    }

    @Override
    public void SaveData() {

    }
}
