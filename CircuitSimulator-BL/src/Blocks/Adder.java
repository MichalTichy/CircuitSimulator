package Blocks;

import Ports.InputPort;
import Ports.OutputPort;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.xml.bind.NotIdentifiableEvent;
import java.awt.geom.Point2D;

public class Adder implements IBlock {
    private Point2D position;
    private BlockStatus status;
    private InputPort inputPortA;
    private InputPort inputPortB;
    private OutputPort outputPort;

    public Adder(Point2D position, InputPort inputPortA, InputPort inputPortB, OutputPort outputPort) {
        this.position = position;
        this.status = BlockStatus.Idle;
        this.inputPortA = inputPortA;
        this.inputPortB = inputPortB;
        this.outputPort = outputPort;
    }

    @Override
    public Point2D GetPosition() {
        return position;
    }

    @Override
    public BlockStatus GetStatus() {
        return status;
    }

    @Override
    public void TickDetected() {
        throw new NotImplementedException(); // TODO
    }
}
