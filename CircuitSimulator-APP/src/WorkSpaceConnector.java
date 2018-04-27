import Blocks.*;
import Workspace.IWorkspace;
import Workspace.Workspace;

import java.awt.geom.Point2D;
import java.util.Collection;

public class WorkSpaceConnector {
    private IWorkspace workspace = new Workspace();

    public Collection<IBlock> GetBlocks(){
        return workspace.GetBlocks();
    }

    public IBlock CreateBlock(BlockType blockType){
        IBlock block =  CreateBlockInstance(blockType);
        workspace.AddBlock(block);
        return block;
    }

    public void DeleteBlock(IBlock block){
        workspace.RemoveBlock(block);
    }

    private IBlock CreateBlockInstance(BlockType blockType) {
        switch (blockType){

            case NumericInput:
                return new NumericInputBlock();
            case NumericOutput:
                return new NumericOutputBlock();
            case Adder:
                return new AdderBlock();
            case Multiplier:
                return new MultiplierBlock();
            case Divider:
                return new DividerBlock();
            case LogicalInput:
                return new LogicalInputBlock();
            case LogicalOutput:
                return new LogicalOutputBlock();
            case IsEqual:
                return new IsEqualBlock();
            case IsGreater:
                return new IsGreaterBlock();
            case IsLess:
                return new IsLessBlock();
            case And:
                return new LogicANDBlock();
            case Or:
                return new LogicORBlock();
            case Not:
                return new LogicNOTBlock();
            case Nand:
                return new LogicNANDBlock();
            case Nor:
                return new LogicNORBlock();
            case Xnor:
                return new LogicXNORBlock();
            case Xor:
                return new LogicXORBlock();
            case Counter:
                return new CounterBlock();
            case Splitter:
                return new SplitterBlock();
            case Clock:
                return new ClockBlock();
                default:
                    throw new IllegalArgumentException("Unknown block type");
        }
    }
}
