import Blocks.*;
import Common.ITimeTickConsumer;
import Ports.*;
import Workspace.IWorkspace;
import Workspace.Workspace;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;

import java.util.Collection;
import java.util.HashMap;

public class WorkSpaceConnector implements ITimeTickConsumer {
    private IWorkspace workspace = new Workspace();
    private AnchorPane workPane;
    private VBox IOPane;

    private static HashMap<GuiPort,Line> connections=new HashMap<>();

    public WorkSpaceConnector(AnchorPane workPane, VBox IO_pane) {

        this.workPane = workPane;
        workspace.Subscribe(this);
        this.IOPane = IO_pane;
    }

    public Collection<IBlock> GetBlocks(){
        return workspace.GetBlocks();
    }

    public IBlock CreateBlock(BlockType blockType){
        IBlock block =  CreateBlockInstance(blockType);
        workspace.AddBlock(block);
        return block;
    }
    public void DeleteBlock(IBlock block){
        for (IPort port :block.GetOutputPorts()) {
            port.Disconnect();
        }
        for (IPort port :block.GetInputPorts()) {
            port.Disconnect();
        }
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
    public void ConnectPort(GuiPort connectionSource, GuiPort connectionTarget) {
        if (connectionSource==connectionTarget)
            return;

        switch (connectionTarget.GetType()) {
            case NumericInput:
                switch (connectionSource.GetType()) {
                    case NumericOutput:
                        ((InputNumericPort)connectionTarget.port).Connect((OutputNumericPort)connectionSource.port);
                        break;
                    case AnyOutput:
                        ((OutputAnyPort)connectionSource.port).Connect((InputNumericPort)connectionTarget.port);
                        break;
                    default:
                        throw new IllegalArgumentException();
                }
                break;
            case NumericOutput:
                switch (connectionSource.GetType()) {
                    case NumericInput:
                        ((OutputNumericPort)connectionTarget.port).Connect((InputNumericPort)connectionSource.port);
                        break;
                    case AnyInput:
                        ((InputAnyPort)connectionSource.port).Connect((OutputNumericPort)connectionTarget.port);
                        break;
                    default: throw new IllegalArgumentException();
                }
                break;
            case LogicalInput:
                switch (connectionSource.GetType()) {
                    case LogicalOutput:
                        ((InputBooleanPort)connectionTarget.port).Connect((OutputBooleanPort)connectionSource.port);
                        break;
                    case AnyOutput:
                        ((OutputAnyPort)connectionSource.port).Connect((InputBooleanPort)connectionTarget.port);
                        break;
                    default: throw new IllegalArgumentException();
                }
                break;
            case LogicalOutput:
                switch (connectionSource.GetType()) {
                    case LogicalInput:
                        ((OutputBooleanPort)connectionTarget.port).Connect((InputBooleanPort) connectionSource.port);
                        break;
                    case AnyInput:
                        ((InputAnyPort)connectionSource.port).Connect((OutputBooleanPort)connectionTarget.port);
                        break;
                    default: throw new IllegalArgumentException();
                }
                break;
            case AnyInput:
                switch (connectionSource.GetType()) {
                    case LogicalOutput:
                    case NumericOutput:
                    case AnyOutput:
                        ((InputAnyPort) connectionTarget.port).Connect((OutputPortBase) connectionSource.port);
                        break;
                    default: throw new IllegalArgumentException();
                }
                break;
            case AnyOutput:
                switch (connectionSource.GetType()) {
                    case LogicalInput:
                    case NumericInput:
                    case AnyInput:
                        ((OutputAnyPort) connectionTarget.port).Connect((InputPortBase) connectionSource.port);
                        break;
                    default: throw new IllegalArgumentException();
                }
        }
        GuiPort.connectionSource=null;
        DrawWorkspace();
    }

    public void DrawWorkspace(){
        workPane.getChildren().clear();
        IOPane.getChildren().clear();
        connections.clear();
        GuiBlock.blocks.clear();

        for (IBlock block:GetBlocks()) {
            GuiBlock guiBlock = new GuiBlock(block,this);
            workPane.getChildren().add(guiBlock);
            guiBlock.Update();
        }

        for (GuiPort guiPort :GuiPort.ports.values()) {
            if (guiPort.port.GetWhetherConnected() && guiPort.port instanceof IInputPort)
            CreatePortConnections(guiPort,GuiPort.ports.get(guiPort.port.getConnectedPort()));
        }

        if (GuiPort.connectionSource!=null){
            GuiPort port = GuiPort.connectionSource;
            port.DisableIncompatiblePorts();
        }

        AddInputsAndOutputs();
    }

    private void CreatePortConnections(GuiPort source, GuiPort destination) {

        Line connection=new Line();
        connection.getStyleClass().add("line");

        workPane.getChildren().add(connection);

        connections.put(source,connection);
        connections.put(destination,connection);

        UpdateConnection(source,destination);
    }
    public void UpdateConnection(GuiPort port) {
        Line connection=connections.get(port);
        if (connection==null) return;

        GuiPort source=port;
        GuiPort destination=GuiPort.ports.get(port.port.getConnectedPort());

        UpdateConnection(source,destination);
    }
    private void UpdateConnection(GuiPort source, GuiPort destination) {
        Line connection=connections.get(source);

        Point2D sourcePos= workPane.sceneToLocal(source.possition);
        Point2D destinationPos= workPane.sceneToLocal(destination.possition);

        connection.setStartX(sourcePos.getX()+source.getPrefWidth()/2);
        connection.setStartY(sourcePos.getY()+source.getPrefHeight()/2);
        connection.setEndX(destinationPos.getX()+destination.getPrefWidth()/2);
        connection.setEndY(destinationPos.getY()+destination.getPrefHeight()/2);
    }

    private void AddInputsAndOutputs(){
        for (IBlock block : workspace.GetBlocks()) {
            if (block instanceof LogicalInputBlock) {
                CheckBox checkBox = new CheckBox("Logical Input");
                checkBox.setSelected(((LogicalInputBlock) block).GetValue());
                IOPane.getChildren().add(checkBox);
                checkBox.setOnAction(event -> ((LogicalInputBlock) block).SetValue(checkBox.isSelected()));
            } else if (block instanceof NumericInputBlock) {
                Label label = new Label("Numeric Input");
                IOPane.getChildren().add(label);
                TextField textField = new TextField();
                textField.setText(Double.toString(((NumericInputBlock) block).getValue()));
                IOPane.getChildren().add(textField);
                textField.setOnAction(event -> {
                    String text = textField.getText();
                    if (text != null && text.matches("^[0-9]+$")) {
                        ((NumericInputBlock) block).SetValue(Double.parseDouble(textField.getText()));
                    } else {
                        event.consume();
                    }
                });
            }
        }
        for (IBlock block : GuiBlock.blocks.keySet()) {
            if (block instanceof LogicalOutputBlock) {
                Label label = new Label();
                label.setText("Logical output: " + ((LogicalOutputBlock) block).GetValue());
                IOPane.getChildren().add(label);
            } else if (block instanceof NumericOutputBlock) {
                Label label = new Label();
                label.setText("Numeric output: " + ((NumericOutputBlock) block).GetValue());
                IOPane.getChildren().add(label);
            }
        }
    }

    public boolean GetIsRunning() {
        return workspace.GetIsRunning();
    }

    public void Break() {
        workspace.Break();
    }

    public void Step() {
        workspace.Step();
    }

    public void Reset() {
        workspace.Reset();
    }

    public void Run() {
        workspace.Run();
    }

    public void Run(int i) {
        workspace.Run(i);
    }

    @Override
    public boolean IsPriorityConsumer() {
        return false;
    }

    @Override
    public void ProcessTick() {
        Platform.runLater(() -> DrawWorkspace());
    }
}
