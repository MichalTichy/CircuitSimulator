/**
 * Logical input block class
 *
 * @author Prášek Matěj - xprase07
 * @author Tichý Michal - xtichy26
 */
package Blocks;

import Data.BooleanData;
import Ports.OutputBooleanPort;

public class LogicalInputBlock extends BlockBase {
    private OutputBooleanPort output;
    private boolean executed = false;
    private boolean value = false;

    /**
     * Constructor that create ports and adds them to collection
     */
    public LogicalInputBlock() {
        this.output = new OutputBooleanPort();
        this.outputPorts.add(output);
    }

    /**
     * Setter of block value to send
     *
     * @param value new block value
     */
    public void SetValue(boolean value) {
        this.value = value;
        executed = false;
    }

    /**
     * Getter of block value
     * @return block value
     */
    public boolean GetValue() {
        return value;
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
        output.Send(new BooleanData(value));
    }

    @Override
    public void SaveData() {

    }
}


