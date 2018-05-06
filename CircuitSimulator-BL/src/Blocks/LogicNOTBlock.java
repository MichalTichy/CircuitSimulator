/**
 * NOT block class
 *
 * @author Prášek Matěj - xprase07
 * @author Tichý Michal - xtichy26
 */
package Blocks;

import Data.BooleanData;
import Ports.InputBooleanPort;
import Ports.OutputBooleanPort;

public class LogicNOTBlock extends BlockBase {

    private InputBooleanPort input;
    private OutputBooleanPort output;
    private BooleanData firstPortData;

    /**
     * Constructor, that create ports and adds them to collection
     */
    public LogicNOTBlock() {
        this.input = new InputBooleanPort();
        this.output = new OutputBooleanPort();
        this.inputPorts.add(input);
        this.outputPorts.add(output);
    }

    @Override
    public void Execute() {
        boolean result = !firstPortData.Data;
        output.Send(new BooleanData(result));
    }

    @Override
    public void SaveData() {
        firstPortData = input.DownloadData();
    }
}
