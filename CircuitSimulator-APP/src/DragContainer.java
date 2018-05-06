/**
 * Drag and drop container class
 *
 * @author Prášek Matěj - xprase07
 * @author Tichý Michal - xtichy26
 */

import javafx.scene.input.DataFormat;
import javafx.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DragContainer implements Serializable {
    private static final long serialVersionUID = -1890998765646621338L;

    public static final DataFormat AddNode =
            new DataFormat("GUI.DragIconIcon.add");

    private final List <Pair<String, Object> > mDataPairs = new ArrayList <Pair<String, Object> > ();

    /**
     * Method that adds new data
     *
     * @param key   key to add
     * @param value value to add
     */
    public void addData (String key, Object value) {
        mDataPairs.add(new Pair<String, Object>(key, value));
    }

    /**
     * Getter for value with specific key
     * @param key key to find
     * @param <T> object of value
     * @return value with given key
     */
    public <T> T getValue (String key) {

        for (Pair<String, Object> data: mDataPairs) {

            if (data.getKey().equals(key))
                return (T) data.getValue();

        }

        return null;
    }
}
