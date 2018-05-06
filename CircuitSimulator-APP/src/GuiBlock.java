
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.*;

import Blocks.*;
import Ports.IInputPort;
import Ports.IOutputPort;
import Ports.IPort;
import com.sun.crypto.provider.BlowfishCipher;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class GuiBlock extends AnchorPane{
	
	@FXML AnchorPane root_pane;

	private BlockType mType = null;
	public IBlock block;
	private WorkSpaceConnector workSpaceConnector;
	public HashMap<IPort, GuiPort> ports=new HashMap<IPort, GuiPort>();
	public static HashMap<IBlock,GuiBlock> blocks=new HashMap<>();

	public static GuiBlock Create(BlockType type, WorkSpaceConnector workspaceConnector, Point2D point2D){
        IBlock block=workspaceConnector.CreateBlock(type);
        block.setPosition(point2D);
        return new GuiBlock(block,workspaceConnector);
    }

	public GuiBlock(IBlock block, WorkSpaceConnector workspaceConnector) {

	    this.block=block;
	    setType(DetermineType(block));
	    this.workSpaceConnector=workspaceConnector;
		setOnMouseClicked(e -> {
			if (e.getButton()== MouseButton.SECONDARY) {
				Destroy();
			}
		});

		/*setOnMouseDragged(event ->
        {
            if (event.getTarget() instanceof GuiPort) return;
            Point2D p = new Point2D(event.getSceneX(), event.getSceneY());
            block.setPosition(p);
            relocateToPoint(p);
            for (GuiPort port: ports.values()) {
                UpdatePortsPosition();
            }
        });*/

		Init();
		AddPorts();
		blocks.put(block,this);
	}

	public void Destroy(){
	    GuiPort.connectionSource=null;

        for (GuiPort port:ports.values()) {
            GuiPort.ports.remove(port.port);
        }

		workSpaceConnector.DeleteBlock(block);
        workSpaceConnector.DrawWorkspace();
	}

	private boolean getWhetherAdded(){
		return block!=null;
	}

	public GuiBlock() {

		Init();
	}


	public void Update(){
		Point2D position=block.getPosition();
		relocateToPoint(new javafx.geometry.Point2D(position.getX(),position.getY()));
		getStylesheets().clear();
		if (block.IsValid()){
			switch (block.GetStatus()) {
				case Idle:
					getStyleClass().add("icon-status-Idle");
					break;
				case Working:
					getStyleClass().add("icon-status-Working");
					break;
			}
		}
		else {
			getStyleClass().add("icon-status-Error");
		}

		UpdatePortsPosition();
	}

    private void AddPorts(){
		for (IPort inputPort : block.GetInputPorts() ) {
			GuiPort port=new GuiPort(inputPort,workSpaceConnector);
			ports.put(inputPort,port);
			root_pane.getChildren().add(port);
		}
		for (IPort outputPort : block.GetOutputPorts() ) {
			GuiPort port=new GuiPort(outputPort,workSpaceConnector);
			ports.put(outputPort,port);
			root_pane.getChildren().add(port);
		}
	}

	protected void UpdatePortsPosition(){
		Bounds bounds = localToScene(getBoundsInLocal());
		javafx.geometry.Point2D  position = new javafx.geometry.Point2D(bounds.getMinX(),bounds.getMaxY());
		double width=getPrefWidth();
		double height=getPrefHeight();


		List<IInputPort> inputPorts = block.GetInputPorts();
		int countOfInputPorts = inputPorts.size();

		for (int i = 0; i < countOfInputPorts; i++) {
			IPort inputPort = inputPorts.get(i);
			GuiPort port = ports.get(inputPort);
			port.UpdatePosition(position, width, height, i, countOfInputPorts, true);
			workSpaceConnector.UpdateConnection(port);
			port.toFront();
		}

		List<IOutputPort> outputPorts = block.GetOutputPorts();
		int countOfOutputPorts = outputPorts.size();
		for (int i = 0; i < countOfOutputPorts; i++) {
			IPort outputPort = outputPorts.get(i);
			GuiPort port = ports.get(outputPort);
			port.UpdatePosition(position, width, height, i, countOfOutputPorts, false);
            workSpaceConnector.UpdateConnection(port);
			port.toFront();
		}
	}
	private void Init() {
		FXMLLoader fxmlLoader = new FXMLLoader(
				getClass().getResource("/Block.fxml")
				);

		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();

		} catch (IOException exception) {
		    throw new RuntimeException(exception);
		}
	}

	@FXML
	private void initialize() {}
	
	public void relocateToPoint (javafx.geometry.Point2D p) {

		//relocates the object to a point that has been converted to
		//scene coordinates

		javafx.geometry.Point2D localCoords = getParent().sceneToLocal(p);


		relocate ( 
				(int) (localCoords.getX() - (getBoundsInLocal().getWidth() / 2)),
				(int) (localCoords.getY() - (getBoundsInLocal().getHeight() / 2))
			);

		if (block!=null) block.setPosition(new Point2D.Double(p.getX(),p.getY()));
	}
	
	public BlockType getType () { return mType; }
	
	public void setType (BlockType type) {
		
		mType = type;
		
		getStyleClass().clear();
		getStyleClass().add("dragicon");
		
		switch (mType) {
			case Adder:
				getStyleClass().add("icon-Adder");
				break;
			case NumericInput:
				getStyleClass().add("icon-NumericInput");
				break;
			case NumericOutput:
				getStyleClass().add("icon-NumericOutput");
				break;
			case Clock:
				getStyleClass().add("icon-Clock");
				break;
			case Counter:
				getStyleClass().add("icon-Counter");
				break;
			case Divider:
				getStyleClass().add("icon-Divider");
				break;
			case IsEqual:
				getStyleClass().add("icon-IsEqual");
				break;
			case IsGreater:
				getStyleClass().add("icon-IsGreater");
				break;
			case IsLess:
				getStyleClass().add("icon-IsLess");
				break;
			case And:
				getStyleClass().add("icon-And");
				break;
			case LogicalInput:
				getStyleClass().add("icon-LogicalInput");
				break;
			case Nand:
				getStyleClass().add("icon-Nand");
				break;
			case Nor:
				getStyleClass().add("icon-Nor");
				break;
			case Not:
				getStyleClass().add("icon-Not");
				break;
			case Or:
				getStyleClass().add("icon-Or");
				break;
			case LogicalOutput:
				getStyleClass().add("icon-LogicalOutput");
				break;
			case Xnor:
				getStyleClass().add("icon-Xnor");
				break;
			case Xor:
				getStyleClass().add("icon-Xor");
				break;
			case Multiplier:
				getStyleClass().add("icon-Multiplier");
				break;
			case Splitter:
				getStyleClass().add("icon-Splitter");
				break;
		}
	}
	public BlockType DetermineType (IBlock block) {
		if (block instanceof AdderBlock)
		    return BlockType.Adder;
		if (block instanceof ClockBlock)
		    return BlockType.Clock;
		if (block instanceof CounterBlock)
		    return BlockType.Counter;
		if (block instanceof DividerBlock)
		    return BlockType.Divider;
		if (block instanceof IsEqualBlock)
		    return BlockType.IsEqual;
		if (block instanceof IsGreaterBlock)
		    return BlockType.IsGreater;
		if (block instanceof IsLessBlock)
		    return BlockType.IsLess;
		if (block instanceof LogicalInputBlock)
		    return BlockType.LogicalInput;
		if (block instanceof LogicalOutputBlock)
		    return BlockType.LogicalOutput;
		if (block instanceof LogicANDBlock)
		    return BlockType.And;
		if (block instanceof LogicNANDBlock)
		    return BlockType.Nand;
		if (block instanceof LogicNORBlock)
		    return BlockType.Nor;
		if (block instanceof LogicNOTBlock)
		    return BlockType.Not;
		if (block instanceof LogicORBlock)
		    return BlockType.Or;
		if (block instanceof LogicXNORBlock)
		    return BlockType.Xnor;
		if (block instanceof LogicXORBlock)
		    return BlockType.Xor;
		if (block instanceof MultiplierBlock)
		    return BlockType.Multiplier;
		if (block instanceof NumericInputBlock)
		    return BlockType.NumericInput;
		if (block instanceof NumericOutputBlock)
		    return BlockType.NumericOutput;
		if (block instanceof SplitterBlock)
		    return BlockType.Splitter;
		throw new IllegalArgumentException();
	}
}
