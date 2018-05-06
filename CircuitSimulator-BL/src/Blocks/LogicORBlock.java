/**
 * OR block class
 *
 * @author Prášek Matěj - xprase07
 * @author Tichý Michal - xtichy26
 */
package Blocks;

import Data.BooleanData;
import Ports.InputBooleanPort;
import Ports.OutputBooleanPort;

public class LogicORBlock extends BlockBase {

    private InputBooleanPort input1;
    private InputBooleanPort input2;
    private OutputBooleanPort output;
    private BooleanData firstPortData;
    private BooleanData secondPortData;

    /**
     * Constructor that create ports and adds them to collection
     */
    public LogicORBlock() {
        this.input1 = new InputBooleanPort();
        this.input2 = new InputBooleanPort();
        this.output = new OutputBooleanPort();
        this.inputPorts.add(input1);
        this.inputPorts.add(input2);
        this.outputPorts.add(output);
    }

    @Override
    public void Execute() {
        boolean result = firstPortData.Data || secondPortData.Data;
        output.Send(new BooleanData(result));
    }

    @Override
    public void SaveData() {
        firstPortData = input1.DownloadData();
        secondPortData = input2.DownloadData();
    }
}
