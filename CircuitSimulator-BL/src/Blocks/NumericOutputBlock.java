/**
 * Numeric output block class
 *
 * @author Prášek Matěj - xprase07
 * @author Tichý Michal - xtichy26
 */
package Blocks;

import Data.NumericData;
import Ports.InputNumericPort;

import java.io.Serializable;

public class NumericOutputBlock extends BlockBase {
    private InputNumericPort input;

    /**
     * Constructor, that create ports and adds them to collection
     */
    public NumericOutputBlock() {
        this.input = new InputNumericPort();
        this.inputPorts.add(input);
    }

    /**
     * Getter of block value
     *
     * @return block value
     */
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
