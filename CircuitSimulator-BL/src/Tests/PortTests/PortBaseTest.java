package Tests.PortTests;

import Ports.InputPort;
import Ports.OutputPort;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;;

public class PortBaseTest {

    @Test
    public void connectInputToOutput() {
        InputPort inputPort=new InputPort();
        OutputPort outputPort=new OutputPort();

        inputPort.Connect(outputPort);

        Assert.assertTrue(inputPort.GetWhetherConnected());
        Assert.assertTrue(outputPort.GetWhetherConnected());
    }
    @Test
    public void connectOutputToInput() {
        InputPort inputPort=new InputPort();
        OutputPort outputPort=new OutputPort();

        outputPort.Connect(inputPort);

        Assert.assertTrue(inputPort.GetWhetherConnected());
        Assert.assertTrue(outputPort.GetWhetherConnected());
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void connectOutputToOutput() {
        OutputPort outputPort=new OutputPort();
        OutputPort outputPort2=new OutputPort();

        thrown.expect(IllegalArgumentException.class);
        outputPort.Connect(outputPort2);
    }

    @Test
    public void connectInputToInput() {
        InputPort inputPort=new InputPort();
        InputPort inputPort2=new InputPort();

        thrown.expect(IllegalArgumentException.class);
        inputPort.Connect(inputPort2);
    }

    @Test
    public void disconnect() {
        InputPort inputPort=new InputPort();
        OutputPort outputPort=new OutputPort();

        inputPort.Connect(outputPort);
        Assert.assertTrue(inputPort.GetWhetherConnected());
        outputPort.Disconnect();

        Assert.assertFalse(inputPort.GetWhetherConnected());
    }

    @Test
    public void disconnectWhenNotConnected() {
        InputPort inputPort=new InputPort();
        Assert.assertFalse(inputPort.GetWhetherConnected());
        inputPort.Disconnect();
        Assert.assertFalse(inputPort.GetWhetherConnected());
    }
}