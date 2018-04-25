package GUI;

import Workspace.Workspace;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;


public class GUIController {
    private Workspace workspace = new Workspace();

    @FXML
    private TextField msPerTick;

    private boolean isNumeric(String s) {
        return s != null && s.matches("^[0-9]+$");
    }

    @FXML
    public void StartStopClicked(ActionEvent actionEvent) {
        if (workspace.GetIsRunning()) {
            workspace.Break();
        } else {
            if (isNumeric(msPerTick.getText())) {
                workspace.Run(Integer.parseInt(msPerTick.getText()));
            } else {
                workspace.Run();
            }
        }
    }

    @FXML
    public void ResetClicked(ActionEvent actionEvent) {
        workspace.Reset();
    }

    @FXML
    public void StepClicked(ActionEvent actionEvent) {
        workspace.Step();
    }
}
