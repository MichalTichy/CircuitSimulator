package Tests.PortTests;

import Data.IData;
import Data.NumericData;
import Ports.InputPort;
import Ports.OutputPort;
import com.sun.corba.se.impl.orbutil.ObjectUtility;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class OutputPortTest {

    @Test
    public void send() {
        InputPort in=new InputPort();
        OutputPort out=new OutputPort();

        out.Connect(in);

        IData data=new NumericData();

        out.Send(data);
        Assert.assertEquals(data,in.DownloadData());
    }
}