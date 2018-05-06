/**
 * Boolean data class
 *
 * @author Prášek Matěj - xprase07
 * @author Tichý Michal - xtichy26
 */
package Data;

import java.util.Objects;

public class NumericData implements IData{
    public double Data;

    /**
     * Constructor that saves data
     *
     * @param data data value
     */
    public NumericData(double data) {
        this.Data = data;
    }

    /**
     * Compare this instance with another
     * @param o instance to compare
     * @return true if given instance equals with this, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NumericData)) return false;
        NumericData that = (NumericData) o;
        return Double.compare(that.Data, Data) == 0;
    }

    /**
     * Returns a hash code value for the data
     * @return hash code value for the data
     */
    @Override
    public int hashCode() {

        return Objects.hash(Data);
    }
}

