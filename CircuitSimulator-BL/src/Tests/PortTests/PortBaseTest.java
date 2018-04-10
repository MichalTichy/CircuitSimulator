package Tests.PortTests;

import Ports.InputBooleanPort;
import Ports.InputPortBase;
import Ports.OutputBooleanPort;
import Ports.OutputPortBase;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;;

public class PortBaseTest {

    @Test
    public void disconnect() {
        InputBooleanPort inputPort=new InputBooleanPort();
        OutputBooleanPort outputPort=new OutputBooleanPort();

        inputPort.Connect(outputPort);
        Assert.assertTrue(inputPort.GetWhetherConnected());
        outputPort.Disconnect();

        Assert.assertFalse(inputPort.GetWhetherConnected());
    }

    @Test
    public void disconnectWhenNotConnected() {
        InputPortBase inputPort=new InputBooleanPort();
        Assert.assertFalse(inputPort.GetWhetherConnected());
        inputPort.Disconnect();
        Assert.assertFalse(inputPort.GetWhetherConnected());
    }
}