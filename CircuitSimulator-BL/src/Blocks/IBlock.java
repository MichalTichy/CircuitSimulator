/**
 * Block interface
 *
 * @author Prášek Matěj - xprase07
 * @author Tichý Michal - xtichy26
 */
package Blocks;

import Common.IResetable;
import Common.ITimeTickConsumer;
import Common.IValidatable;
import Ports.IInputPort;
import Ports.IOutputPort;

import java.awt.geom.Point2D;
import java.util.List;

public interface IBlock extends IValidatable,IResetable,ITimeTickConsumer {
    /**
     * Getter for block status
     *
     * @return block status
     */
    BlockStatus GetStatus();

    /**
     * Execute blocks inner logic
     */
    void Execute();

    /**
     * Getter for List of input ports
     * @return List of input ports
     */
    List<IInputPort> GetInputPorts();

    /**
     * Getter for List of output ports
     * @return List of output ports
     */
    List<IOutputPort> GetOutputPorts();

    /**
     * Getter for block position
     * @return block position
     */
    Point2D getPosition();

    /**
     * Setter for block position
     * @param p new position
     */
    void setPosition(Point2D p);

    /**
     * Method, that saves data form input ports for use it while execution in next tick
     */
    void SaveData();
}

