package Tests.PortTests;

import Data.BooleanData;
import Data.IData;
import Ports.InputBooleanPort;
import Ports.InputPortBase;
import Ports.OutputBooleanPort;
import Ports.OutputPortBase;
import org.junit.Assert;
import org.junit.Test;

public class InputPortBaseTest {

    @Test
    public void getWhetherDataChangedNoPreviousData() {
        InputBooleanPort in=new InputBooleanPort();
        OutputBooleanPort out=new OutputBooleanPort();

        in.Connect(out);

        Assert.assertFalse(in.GetWhetherDataChanged());
    }

    @Test
    public void getWhetherDataChanged() {
        InputBooleanPort in=new InputBooleanPort();
        OutputBooleanPort out=new OutputBooleanPort();

        in.Connect(out);

        BooleanData data=new BooleanData();
        out.Send(data);

        Assert.assertTrue(in.GetWhetherDataChanged());
    }

    @Test
    public void getWhetherDataChangedSameData() {
        InputBooleanPort in=new InputBooleanPort();
        OutputBooleanPort out=new OutputBooleanPort();

        in.Connect(out);

        BooleanData data=new BooleanData();
        out.Send(data);
        in.DownloadData();

        Assert.assertFalse(in.GetWhetherDataChanged());
    }

    @Test
    public void downloadData() {
        InputBooleanPort in=new InputBooleanPort();
        OutputBooleanPort out=new OutputBooleanPort();

        in.Connect(out);

        BooleanData data = new BooleanData();
        data.Data=true;

        out.Send(data);

        Assert.assertEquals(data,in.DownloadData());
    }
}