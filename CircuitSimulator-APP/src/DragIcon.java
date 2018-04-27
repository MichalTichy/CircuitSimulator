
import java.io.IOException;

import Blocks.IBlock;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class DragIcon extends AnchorPane{
	
	@FXML AnchorPane root_pane;

	private BlockType mType = null;
	public IBlock block;
	private WorkSpaceConnector workSpaceConnector;

	public DragIcon(BlockType type, WorkSpaceConnector workspaceConnector) {
		this.workSpaceConnector = workspaceConnector;
		setType(type);
		block=workSpaceConnector.CreateBlock(mType);

		setOnMouseClicked(e -> {
			if (e.getButton()== MouseButton.SECONDARY) {
				Destroy();
			}
		});

		Init();
	}

	private void Destroy(){
		workSpaceConnector.DeleteBlock(block);
		((Pane) getParent()).getChildren().remove(this);
	}

	private boolean getWhetherAdded(){
		return block!=null;
	}

	public DragIcon() {

		Init();
	}

	private void Init() {
		FXMLLoader fxmlLoader = new FXMLLoader(
				getClass().getResource("/DragIcon.fxml")
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
	
	public void relocateToPoint (Point2D p) {

		//relocates the object to a point that has been converted to
		//scene coordinates
		Point2D localCoords = getParent().sceneToLocal(p);
		
		relocate ( 
				(int) (localCoords.getX() - (getBoundsInLocal().getWidth() / 2)),
				(int) (localCoords.getY() - (getBoundsInLocal().getHeight() / 2))
			);
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
}
