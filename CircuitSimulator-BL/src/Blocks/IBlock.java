package Blocks;

import Common.IResetable;
import Common.ITimeTickConsumer;
import Common.IValidatable;
import Ports.IInputPort;
import Ports.IOutputPort;

import java.awt.geom.Point2D;
import java.util.List;

public interface IBlock extends IValidatable,IResetable,ITimeTickConsumer {
    BlockStatus GetStatus();
    void Execute();
    List<IInputPort> GetInputPorts();
    List<IOutputPort> GetOutputPorts();

    java.awt.geom.Point2D getPosition();

    void setPosition(Point2D p);

    void SaveData();
}

