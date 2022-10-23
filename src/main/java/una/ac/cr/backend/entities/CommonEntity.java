package una.ac.cr.backend.entities;

import java.util.function.Supplier;

public abstract class CommonEntity {
    public boolean areAttributesTheSame(CommonEntity entity) {
        return makeComparison(entity);
    }

    protected abstract boolean makeComparison(CommonEntity entity);
}
