package Gui; /**
 * Controller of Gui
 *
 * @author Prášek Matěj - xprase07
 * @author Tichý Michal - xtichy26
 */

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;

public class RootLayout extends AnchorPane {

    @FXML
    SplitPane base_pane;
    @FXML
    AnchorPane right_pane;
    @FXML
    VBox IO_pane;
    @FXML
    VBox left_pane;
    @FXML
    Label statusLabel;
    @FXML
    Label valueLabel;

    @FXML
    Button StepButton;
    @FXML
    Button ResetButton;

    @FXML
    Menu FileMenu;

    private GuiBlock mDragOverIcon = null;

    private EventHandler<DragEvent> mIconDragOverRoot = null;
    private EventHandler<DragEvent> mIconDragDropped = null;
    private EventHandler<DragEvent> mIconDragOverRightPane = null;
    private WorkSpaceConnector workspaceConnector;

    @FXML
    private TextField msPerTick;

    /**
     * Constructor that load xml file
     */
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

    /**
     * Method that initialize scene
     */
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

    /**
     * Method that detects mouse drag
     *
     * @param block block dragged
     */
    private void addDragDetection(GuiBlock block) {

        block.setOnDragDetected(event -> {

            // set drag event handlers on their respective objects
            base_pane.setOnDragOver(mIconDragOverRoot);
            right_pane.setOnDragOver(mIconDragOverRightPane);
            right_pane.setOnDragDropped(mIconDragDropped);

            // get a reference to the clicked Gui.GuiBlock object
            GuiBlock icn = (GuiBlock) event.getSource();

            //begin drag ops
            mDragOverIcon.setType(icn.getType());
            mDragOverIcon.relocateToPoint(new javafx.geometry.Point2D(event.getSceneX(), event.getSceneY()));

            ClipboardContent content = new ClipboardContent();
            DragContainer container = new DragContainer();

            container.addData("type", mDragOverIcon.getType().toString());
            content.put(DragContainer.AddNode, container);

            mDragOverIcon.startDragAndDrop(TransferMode.ANY).setContent(content);
            mDragOverIcon.setVisible(true);
            mDragOverIcon.setMouseTransparent(true);
            event.consume();
        });
    }

    /**
     * Method that handle mouse drag
     */
    private void buildDragHandlers() {

        //drag over transition to move widget form left pane to right pane
        mIconDragOverRoot = event -> {

            javafx.geometry.Point2D p = right_pane.sceneToLocal(event.getSceneX(), event.getSceneY());

            //turn on transfer mode and track in the right-pane's context
            //if (and only if) the mouse cursor falls within the right pane's bounds.
            if (!right_pane.boundsInLocalProperty().get().contains(p)) {

                event.acceptTransferModes(TransferMode.ANY);
                mDragOverIcon.relocateToPoint(new javafx.geometry.Point2D(event.getSceneX(), event.getSceneY()));
                return;
            }

            event.consume();
        };

        mIconDragOverRightPane = event -> {

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
        };

        mIconDragDropped = event -> {

            DragContainer container =
                    (DragContainer) event.getDragboard().getContent(DragContainer.AddNode);

            container.addData("scene_coords",
                    new javafx.geometry.Point2D(event.getSceneX(), event.getSceneY()));

            ClipboardContent content = new ClipboardContent();
            content.put(DragContainer.AddNode, container);

            event.getDragboard().setContent(content);
            event.setDropCompleted(true);
        };

        this.setOnDragDone(event -> {

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

                    GuiBlock droppedIcon = GuiBlock.Create(type, workspaceConnector, new Point2D.Double(cursorPoint.getX() - 32, cursorPoint.getY() - 32));

                    workspaceConnector.DrawWorkspace();
                }
            }

            event.consume();
        });
    }

    /**
     * Method that determine if is given string numeric
     *
     * @param s string to determine
     * @return true if is string numeric, false otherwise
     */
    private boolean isNumeric(String s) {
        return s != null && s.matches("^[0-9]+$");
    }

    /**
     * Method that handle click on start/stop button
     */
    @FXML
    public void StartStopClicked() {
        if (workspaceConnector.GetIsRunning()) {
            statusLabel.setText("Status: Stopped");
            workspaceConnector.Break();
            EnableToolbar(false);
        } else {
            EnableToolbar(true);
            statusLabel.setText("Status: Running");
            if (isNumeric(msPerTick.getText())) {
                workspaceConnector.Run(Integer.parseInt(msPerTick.getText()));
            } else {
                workspaceConnector.Run();
            }
        }
    }

    /**
     * Method that enable or disable toolbar
     *
     * @param enable true = enable, false = disable
     */
    public void EnableToolbar(boolean enable) {
        FileMenu.setDisable(enable);
        StepButton.setDisable(enable);
        ResetButton.setDisable(enable);
        msPerTick.setDisable(enable);
    }

    /**
     * Method that handle click on reset button
     */
    @FXML
    public void ResetClicked() {
        workspaceConnector.Reset();
    }

    /**
     * Method that handle click on step button
     */
    @FXML
    public void StepClicked() {
        workspaceConnector.Step();
    }

    /**
     * Method that handle click on File-&gt;New in menu and create new workspace
     */
    @FXML
    public void NewWorkspace() {
        workspaceConnector.NewWorkspace();
        workspaceConnector.DrawWorkspace();
    }

    /**
     * Method that handle click on File-&gt;New in menu and load existing workspace
     *
     * @throws ClassNotFoundException if class of a serialized object cannot be found
     */
    @FXML
    public void LoadWorkspace() throws ClassNotFoundException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("workspace", "*.workspace");
        fileChooser.getExtensionFilters().add(extensionFilter);
        fileChooser.setTitle("Open Workspace File");
        Window stage = getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file == null)
            return;

        workspaceConnector.LoadWorkspace(file);
        workspaceConnector.DrawWorkspace();
    }

    /**
     * Method that handle click on File-&gt;New in menu and save workspace
     */
    @FXML
    public void SaveWorkspace() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("workspace", "*.workspace");
        fileChooser.getExtensionFilters().add(extensionFilter);
        fileChooser.setTitle("Save Workspace File");

        Window stage = getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);

        if (file == null)
            return;

        workspaceConnector.SaveWorkspace(file);
    }

}
