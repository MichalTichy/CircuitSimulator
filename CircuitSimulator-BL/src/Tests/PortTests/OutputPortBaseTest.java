package Tests.PortTests;

import Data.BooleanData;
import Data.IData;
import Data.NumericData;
import Ports.InputBooleanPort;
import Ports.InputPortBase;
import Ports.OutputBooleanPort;
import Ports.OutputPortBase;
import org.junit.Assert;
import org.junit.Test;

public class OutputPortBaseTest {

    @Test
    public void send() {
        InputBooleanPort in=new InputBooleanPort();
        OutputBooleanPort out=new OutputBooleanPort();

        in.Connect(out);

        BooleanData data=new BooleanData();

        out.Send(data);
        Assert.assertEquals(data,in.DownloadData());
    }
}