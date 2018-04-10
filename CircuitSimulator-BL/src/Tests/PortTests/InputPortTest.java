package Tests.PortTests;

import Data.BooleanData;
import Data.IData;
import Ports.InputPort;
import Ports.OutputPort;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class InputPortTest {

    @Test
    public void getWhetherDataChangedNoPreviousData() {
        InputPort in=new InputPort();
        OutputPort out=new OutputPort();

        in.Connect(out);

        Assert.assertFalse(in.GetWhetherDataChanged());
    }

    @Test
    public void getWhetherDataChanged() {
        InputPort in=new InputPort();
        OutputPort out=new OutputPort();

        in.Connect(out);

        IData data=new BooleanData();
        out.Send(data);

        Assert.assertTrue(in.GetWhetherDataChanged());
    }

    @Test
    public void getWhetherDataChangedSameData() {
        InputPort in=new InputPort();
        OutputPort out=new OutputPort();

        in.Connect(out);

        IData data=new BooleanData();
        out.Send(data);
        in.DownloadData();

        Assert.assertFalse(in.GetWhetherDataChanged());
    }

    @Test
    public void downloadData() {
        InputPort in=new InputPort();
        OutputPort out=new OutputPort();

        in.Connect(out);

        BooleanData data = new BooleanData();
        data.Data=true;

        out.Send(data);

        Assert.assertEquals(data,in.DownloadData());
    }
}