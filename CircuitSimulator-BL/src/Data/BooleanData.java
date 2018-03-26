package Data;

import java.util.Objects;

public class BooleanData implements IData{
    public boolean Data;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BooleanData)) return false;
        BooleanData that = (BooleanData) o;
        return Data == that.Data;
    }

    @Override
    public int hashCode() {

        return Objects.hash(Data);
    }
}

