/**
 * GUI of port
 *
 * @author Prášek Matěj - xprase07
 * @author Tichý Michal - xtichy26
 */

import Ports.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.HashMap;

public class GuiPort extends AnchorPane{

    @FXML AnchorPane root_pane;
    public boolean enabled=true;
    public IPort port;
    public static HashMap<IPort,GuiPort> ports=new HashMap<>();
    public static GuiPort connectionSource;
    private WorkSpaceConnector workSpaceConnector;
    public Point2D possition;

    /**
     * Contructor that create port
     *
     * @param port               port to create
     * @param workspaceConnector workspace connector
     */
    public GuiPort(IPort port, WorkSpaceConnector workspaceConnector) {
        this.workSpaceConnector = workspaceConnector;
        this.port=port;
        Init();
        UpdateStyles();
        ports.put(port,this);

        setOnMouseClicked(event -> {
            if (!enabled){
                return;
            }

            if (GuiPort.connectionSource!=null){
                if (GuiPort.connectionSource==this)
                    GuiPort.connectionSource=null;
                else{
                    workspaceConnector.ConnectPort(GuiPort.connectionSource,this);
                    workspaceConnector.DrawWorkspace();
                }
                EnableAllPorts();
            } else{
                DisableIncompatiblePorts();
                GuiPort.connectionSource=this;
                UpdateStyles();
            }
            event.consume();
        });
    }

    /**
     * Method that update position of port
     * @param blockPosition position of block where port lies
     * @param blockWidth width of block
     * @param blockHeight height of block
     * @param portIndex index of port to update
     * @param totalPorts number of ports in block
     * @param isInput true if port is input port, false otherwise
     */
    public void UpdatePosition(Point2D blockPosition, double blockWidth, double blockHeight, int portIndex, int totalPorts, boolean isInput){
        double x=blockPosition.getX();
        if (!isInput) x += blockWidth;

        double y = blockPosition.getY();
        y+=(blockHeight*(portIndex+1))/(totalPorts+1);

        if (isInput) x += getPrefWidth();
        else x -= getPrefWidth();

        y-=getPrefHeight()/2;

        relocateToPoint(new Point2D(x, y));
    }

    /**
     * Method that update style of ports
     */
    public void UpdateStyles(){

        getStyleClass().clear();
        getStyleClass().add("port");
        if (GuiPort.connectionSource==this)
            getStyleClass().add("icon-status-Selected");
        else if (!enabled){
            getStyleClass().add("icon-status-Disabled");
        } else{
            if (port.IsValid()){
                getStyleClass().add("icon-status-OK");
            } else {
                getStyleClass().add("icon-status-Error");
            }
        }
    }

    /**
     * Method that initialize port
     */
    private void Init() {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/Port.fxml")
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
     * Method that relocate port to given coordinates
     * @param p new coordinates of port
     */
    public void relocateToPoint (Point2D p) {

        possition=p;
        Point2D localCoords = getParent().sceneToLocal(p);

        relocate (
                (int) (localCoords.getX() - (getBoundsInLocal().getWidth() / 2)),
                (int) (localCoords.getY() -  (getBoundsInLocal().getHeight() / 2))
        );
    }

    /**
     * Getter for type of port
     * @return type of port
     */
    public PortType GetType(){
        if (port instanceof InputBooleanPort) return PortType.LogicalInput;
        if (port instanceof OutputBooleanPort) return PortType.LogicalOutput;
        if (port instanceof InputNumericPort) return PortType.NumericInput;
        if (port instanceof OutputNumericPort) return PortType.NumericOutput;
        if (port instanceof InputAnyPort){
            InputAnyPort inputAnyPort=(InputAnyPort)port;
            switch (inputAnyPort.getCurrentDataType()){

                case Numeric:
                    return PortType.NumericInput;
                case Boolean:
                    return PortType.LogicalInput;
                case Any:
                    return PortType.AnyInput;
                default:
                    throw new IllegalArgumentException();
            }
        }
        if (port instanceof OutputAnyPort){
            OutputAnyPort outputPortAny=(OutputAnyPort)port;
            switch (outputPortAny.getCurrentDataType()){
                case Numeric:
                    return PortType.NumericOutput;
                case Boolean:
                    return PortType.LogicalOutput;
                case Any:
                    return PortType.AnyOutput;
                default:
                    throw new IllegalArgumentException();
            }
        }

        throw new IllegalArgumentException();
    }

    /**
     * Method that disable incompatible ports to prevent connecting them
     */
    public void DisableIncompatiblePorts(){
        PortType type =GetType();
        for (GuiPort port : ports.values()) {
            if (port==this) {
                enabled =false;
            } else{

                switch (port.GetType()){
                    case NumericInput:
                        port.enabled =type==PortType.NumericOutput || type==PortType.AnyOutput;
                        break;
                    case NumericOutput:
                        port.enabled =type==PortType.NumericInput || type==PortType.AnyInput;
                        break;
                    case LogicalInput:
                        port.enabled =type==PortType.LogicalOutput || type==PortType.AnyOutput;
                        break;
                    case LogicalOutput:
                        port.enabled =type==PortType.LogicalInput || type==PortType.AnyInput;
                        break;
                    case AnyInput:
                        port.enabled =type==PortType.LogicalOutput || type==PortType.NumericOutput || type==PortType.AnyOutput;
                        break;
                    case AnyOutput:
                        port.enabled =type==PortType.LogicalInput || type==PortType.NumericInput || type==PortType.AnyInput;
                        break;
                }
            }

            port.UpdateStyles();
        }
    }

    /**
     * Method that enable all ports
     */
    protected void EnableAllPorts(){
        for (GuiPort port : ports.values()) {
            enabled =true;
            port.UpdateStyles();
        }
    }
}
