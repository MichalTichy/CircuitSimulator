package Data;

import java.util.Objects;

public class NumericData implements IData{
    public double Data;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NumericData)) return false;
        NumericData that = (NumericData) o;
        return Double.compare(that.Data, Data) == 0;
    }

    @Override
    public int hashCode() {

        return Objects.hash(Data);
    }
}

