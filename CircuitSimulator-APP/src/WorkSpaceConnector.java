/**
 * Connector with workspace in block logic module
 *
 * @author Prášek Matěj - xprase07
 * @author Tichý Michal - xtichy26
 */
import Blocks.*;
import Common.ITimeTickConsumer;
import Data.BooleanData;
import Data.IData;
import Data.NumericData;
import Ports.*;
import Workspace.IWorkspace;
import Workspace.Workspace;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;

public class WorkSpaceConnector implements ITimeTickConsumer {
    private IWorkspace workspace;
    private AnchorPane workPane;
    private VBox IOPane;
    private Label statusLabel;
    private Label valueLabel;

    private static HashMap<GuiPort,Line> connections=new HashMap<>();

    /**
     * Constructor that initialize FXML objects
     *
     * @param workPane    work pane
     * @param IO_pane     input and output pane
     * @param statusLabel label with status
     * @param valueLabel  label with block value
     */
    public WorkSpaceConnector(AnchorPane workPane, VBox IO_pane, Label statusLabel, Label valueLabel) {

        NewWorkspace();
        this.workPane = workPane;
        this.IOPane = IO_pane;
        this.statusLabel = statusLabel;
        this.valueLabel = valueLabel;
    }

    /**
     * Getter for block collection
     * @return collection of blocks
     */
    public Collection<IBlock> GetBlocks(){
        return workspace.GetBlocks();
    }

    /**
     * Method that create block and add it to collection
     * @param blockType type of block to create
     * @return created block
     */
    public IBlock CreateBlock(BlockType blockType){
        IBlock block =  CreateBlockInstance(blockType);
        workspace.AddBlock(block);
        return block;
    }

    /**
     * Method that delete block
     * @param block block to delete
     */
    public void DeleteBlock(IBlock block){
        for (IPort port :block.GetOutputPorts()) {
            port.Disconnect();
        }
        for (IPort port :block.GetInputPorts()) {
            port.Disconnect();
        }
        workspace.RemoveBlock(block);
    }

    /**
     * Method that create instance of block
     * @param blockType type of block
     * @return new block
     */
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

    /**
     * Method that connect ports
     * @param connectionSource source port to connect
     * @param connectionTarget target port to connect
     */
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

    /**
     * Method that draw entire workspace
     */
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

    /**
     * Method that draw port connection
     * @param source source port
     * @param destination destination port
     */
    private void CreatePortConnections(GuiPort source, GuiPort destination) {

        Line connection=new Line();
        connection.getStyleClass().add("line");

        workPane.getChildren().add(connection);

        connections.put(source,connection);
        connections.put(destination,connection);

        UpdateConnection(source,destination);

        IData data = ((InputPortBase) source.port).GetData();

        if (data instanceof NumericData) {
            connection.setOnMouseEntered(event -> valueLabel.setText("Value: " + Double.toString(((NumericData) data).Data)));
            connection.setOnMouseExited(event -> valueLabel.setText("Value: "));
        } else if (data instanceof BooleanData) {
            connection.setOnMouseEntered(event -> valueLabel.setText("Value: " + Boolean.toString(((BooleanData) data).Data)));
            connection.setOnMouseExited(event -> valueLabel.setText("Value: "));
        } else if (data == null) {
            connection.setOnMouseEntered(event -> valueLabel.setText("Value: none"));
            connection.setOnMouseExited(event -> valueLabel.setText("Value: "));
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Method that update port connection of port
     * @param port port to update
     */
    public void UpdateConnection(GuiPort port) {
        Line connection=connections.get(port);
        if (connection==null) return;

        GuiPort source=port;
        GuiPort destination=GuiPort.ports.get(port.port.getConnectedPort());

        UpdateConnection(source,destination);
    }

    /**
     * Method that update connection of ports
     * @param source source port
     * @param destination destination port
     */
    private void UpdateConnection(GuiPort source, GuiPort destination) {
        Line connection=connections.get(source);

        Point2D sourcePos= workPane.sceneToLocal(source.possition);
        Point2D destinationPos= workPane.sceneToLocal(destination.possition);

        connection.setStartX(sourcePos.getX()+source.getPrefWidth()/2);
        connection.setStartY(sourcePos.getY()+source.getPrefHeight()/2);
        connection.setEndX(destinationPos.getX()+destination.getPrefWidth()/2);
        connection.setEndY(destinationPos.getY()+destination.getPrefHeight() / 2);
    }

    /**
     * Method that fill right panel to define input and write output blocks
     */
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
                textField.textProperty().addListener((observable)->{
                    String text = textField.getText();
                    if (text != null && text.matches("^-?\\d+(\\.\\d)?$")) {
                        ((NumericInputBlock) block).SetValue(Double.parseDouble(textField.getText()));
                        textField.setStyle("-fx-background-color: #FFFFFF;");
                    }
                    else{
                        textField.setStyle("-fx-background-color: #FF0000;");
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

    /**
     * Getter for is running
     * @return true if is running, false otherwise
     */
    public boolean GetIsRunning() {
        return workspace.GetIsRunning();
    }

    /**
     * Method that stop run
     */
    public void Break() {
        workspace.Break();
    }

    /**
     * Method that provides tick for every subscriber
     */
    public void Step() {
        workspace.Step();
    }

    /**
     * Method that reset all blocks and ports
     */
    public void Reset() {
        workspace.Reset();
    }

    /**
     * Method that do step every 500ms
     */
    public void Run() {
        workspace.Run();
    }

    /**
     * Method that do step every msPerTick value
     * @param i frequency of steps
     */
    public void Run(int i) {
        workspace.Run(i);
    }

    /**
     * Method that return false because workspace connector isn't priority consumer
     * @return false
     */
    @Override
    public boolean IsPriorityConsumer() {
        return false;
    }

    /**
     * Method that Draw workspace at process tick
     */
    @Override
    public void ProcessTick() {
        Platform.runLater(() -> DrawWorkspace());
    }

    /**
     * Method that create new workspace
     */
    public void NewWorkspace() {
        workspace = new Workspace();
        workspace.Subscribe(this);
    }

    /**
     * Method that loads workspace form file
     * @param file file with saved workspace
     * @throws ClassNotFoundException if class of a serialized object cannot be found
     */
    public void LoadWorkspace(File file) throws ClassNotFoundException {
        FileInputStream fileStream = null;
        try {
            fileStream = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileStream);
            workspace = (Workspace) in.readObject();
            workspace.Subscribe(this);
            in.close();
            fileStream.close();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Workspace loading failed.Ensure that selected file exists and is not corrupted").show();
        }
    }

    /**
     * Method that save workspace to file
     * @param file workspace file
     */
    public void SaveWorkspace(File file) {
        try {
            FileOutputStream fileOut = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            workspace.Unsubscribe(this);

            out.writeObject(workspace);

            workspace.Subscribe(this);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Workspace saving failed. /n Ensure that you have permission to save to selected location.").show();
        }
    }
}
