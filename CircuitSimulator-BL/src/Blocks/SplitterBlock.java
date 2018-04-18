package Blocks;

import Ports.InputAnyPort;
import Ports.OutputAnyPort;

public class SplitterBlock extends BlockBase {

    private InputAnyPort input;
    private OutputAnyPort output1;
    private OutputAnyPort output2;

    public SplitterBlock() {
        this.input = new InputAnyPort();
        this.output1 = new OutputAnyPort();
        this.output2 = new OutputAnyPort();
        this.inputPorts.add(input);
        this.outputPorts.add(output1);
        this.outputPorts.add(output2);
    }

    @Override
    public void Execute() {
        output1.Send(input.DownloadData());
        output2.Send(input.DownloadData());
    }

}
