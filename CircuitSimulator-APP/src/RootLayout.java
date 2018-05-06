import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;

public class RootLayout extends AnchorPane{

	@FXML SplitPane base_pane;
	@FXML AnchorPane right_pane;
	@FXML
	VBox IO_pane;
	@FXML VBox left_pane;
	@FXML
	Label statusLabel;
	@FXML
	Label valueLabel;

	private GuiBlock mDragOverIcon = null;
	
	private EventHandler<DragEvent> mIconDragOverRoot = null;
	private EventHandler<DragEvent> mIconDragDropped = null;
	private EventHandler<DragEvent> mIconDragOverRightPane = null;
    private WorkSpaceConnector workspaceConnector;

	@FXML
	private TextField msPerTick;

    public RootLayout() {
		
		FXMLLoader fxmlLoader = new FXMLLoader(
				getClass().getResource("/RootLayout.fxml")
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
	private void initialize() {

		mDragOverIcon = new GuiBlock();
		
		mDragOverIcon.setVisible(false);
		mDragOverIcon.setOpacity(0.65);
		getChildren().add(mDragOverIcon);

		for (int i = 0; i < 20; i++) {
			
			GuiBlock icn = new GuiBlock();
			
			addDragDetection(icn);
			
			icn.setType(BlockType.values()[i]);
			left_pane.getChildren().add(icn);
		}
		
		buildDragHandlers();
		statusLabel.setText("Status: Stopped");
		valueLabel.setText("Value: ");
		workspaceConnector = new WorkSpaceConnector(right_pane, IO_pane, statusLabel, valueLabel);
	}
	
	private void addDragDetection(GuiBlock block) {
		
		block.setOnDragDetected (new EventHandler <MouseEvent> () {

			@Override
			public void handle(MouseEvent event) {

				// set drag event handlers on their respective objects
				base_pane.setOnDragOver(mIconDragOverRoot);
				right_pane.setOnDragOver(mIconDragOverRightPane);
				right_pane.setOnDragDropped(mIconDragDropped);
				
				// get a reference to the clicked GuiBlock object
				GuiBlock icn = (GuiBlock) event.getSource();
				
				//begin drag ops
				mDragOverIcon.setType(icn.getType());
				mDragOverIcon.relocateToPoint(new javafx.geometry.Point2D (event.getSceneX(), event.getSceneY()));
            
				ClipboardContent content = new ClipboardContent();
				DragContainer container = new DragContainer();
				
				container.addData ("type", mDragOverIcon.getType().toString());
				content.put(DragContainer.AddNode, container);

				mDragOverIcon.startDragAndDrop (TransferMode.ANY).setContent(content);
				mDragOverIcon.setVisible(true);
				mDragOverIcon.setMouseTransparent(true);
				event.consume();					
			}
		});
	}	
	
	private void buildDragHandlers() {
		
		//drag over transition to move widget form left pane to right pane
		mIconDragOverRoot = new EventHandler <DragEvent>() {

			@Override
			public void handle(DragEvent event) {

                javafx.geometry.Point2D p = right_pane.sceneToLocal(event.getSceneX(), event.getSceneY());

				//turn on transfer mode and track in the right-pane's context 
				//if (and only if) the mouse cursor falls within the right pane's bounds.
				if (!right_pane.boundsInLocalProperty().get().contains(p)) {
					
					event.acceptTransferModes(TransferMode.ANY);
					mDragOverIcon.relocateToPoint(new javafx.geometry.Point2D(event.getSceneX(), event.getSceneY()));
					return;
				}

				event.consume();
			}
		};
		
		mIconDragOverRightPane = new EventHandler <DragEvent> () {

			@Override
			public void handle(DragEvent event) {

				event.acceptTransferModes(TransferMode.ANY);
				
				//convert the mouse coordinates to scene coordinates,
				//then convert back to coordinates that are relative to 
				//the parent of mDragIcon.  Since mDragIcon is a child of the root
				//pane, coodinates must be in the root pane's coordinate system to work
				//properly.
				mDragOverIcon.relocateToPoint(
								new javafx.geometry.Point2D(event.getSceneX(), event.getSceneY())
				);
				event.consume();
			}
		};
				
		mIconDragDropped = new EventHandler <DragEvent> () {

			@Override
			public void handle(DragEvent event) {
				
				DragContainer container = 
						(DragContainer) event.getDragboard().getContent(DragContainer.AddNode);
				
				container.addData("scene_coords", 
						new javafx.geometry.Point2D(event.getSceneX(), event.getSceneY()));
				
				ClipboardContent content = new ClipboardContent();
				content.put(DragContainer.AddNode, container);
				
				event.getDragboard().setContent(content);
				event.setDropCompleted(true);
			}
		};

		this.setOnDragDone (new EventHandler <DragEvent> (){
			
			@Override
			public void handle (DragEvent event) {
				
				right_pane.removeEventHandler(DragEvent.DRAG_OVER, mIconDragOverRightPane);
				right_pane.removeEventHandler(DragEvent.DRAG_DROPPED, mIconDragDropped);
				base_pane.removeEventHandler(DragEvent.DRAG_OVER, mIconDragOverRoot);
								
				mDragOverIcon.setVisible(false);
				
				DragContainer container = 
						(DragContainer) event.getDragboard().getContent(DragContainer.AddNode);
				
				if (container != null) {
					if (container.getValue("scene_coords") != null) {

                        BlockType type = BlockType.valueOf(container.getValue("type"));

                        javafx.geometry.Point2D cursorPoint = container.getValue("scene_coords");

                        GuiBlock droppedIcon = GuiBlock.Create(type,workspaceConnector,new Point2D.Double(cursorPoint.getX()-32, cursorPoint.getY()-32));

                        workspaceConnector.DrawWorkspace();
					}
				}

				event.consume();
			}
		});
	}

	private boolean isNumeric(String s) {
		return s != null && s.matches("^[0-9]+$");
	}

	@FXML
	public void StartStopClicked(ActionEvent actionEvent) {
		if (workspaceConnector.GetIsRunning()) {
			statusLabel.setText("Status: Stopped");
			workspaceConnector.Break();
		} else {
			statusLabel.setText("Status: Running");
			if (isNumeric(msPerTick.getText())) {
				workspaceConnector.Run(Integer.parseInt(msPerTick.getText()));
			} else {
				workspaceConnector.Run();
			}
		}
	}

	@FXML
	public void ResetClicked(ActionEvent actionEvent) {
		workspaceConnector.Reset();
	}

	@FXML
	public void StepClicked(ActionEvent actionEvent) {
		workspaceConnector.Step();
	}

	@FXML
	public void NewWorkspace(ActionEvent actionEvent) {
		workspaceConnector.NewWorkspace();
        workspaceConnector.DrawWorkspace();
	}

	@FXML
	public void LoadWorkspace(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
		FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter=new FileChooser.ExtensionFilter("workspace","*.workspace");
        fileChooser.getExtensionFilters().add(extensionFilter);
        fileChooser.setTitle("Open Workspace File");
		Window stage = getScene().getWindow();
		File file = fileChooser.showOpenDialog(stage);

		if (file==null)
		    return;

		workspaceConnector.LoadWorkspace(file);
		workspaceConnector.DrawWorkspace();
	}

	@FXML
	public void SaveWorkspace(ActionEvent actionEvent) throws IOException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter=new FileChooser.ExtensionFilter("workspace","*.workspace");
        fileChooser.getExtensionFilters().add(extensionFilter);
        fileChooser.setTitle("Save Workspace File");

        Window stage = getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);

        if (file==null)
            return;

		workspaceConnector.SaveWorkspace(file);
	}

}
