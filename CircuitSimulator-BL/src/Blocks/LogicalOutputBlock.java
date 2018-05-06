/**
 * Logical output block class
 *
 * @author Prášek Matěj - xprase07
 * @author Tichý Michal - xtichy26
 */
package Blocks;

import Data.BooleanData;
import Ports.InputBooleanPort;

public class LogicalOutputBlock extends BlockBase {
    private InputBooleanPort input;

    /**
     * Constructor, that create ports and adds them to collection
     */
    public LogicalOutputBlock() {
        this.input = new InputBooleanPort();
        this.inputPorts.add(input);
    }

    /**
     * Getter of block value
     *
     * @return block value
     */
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
