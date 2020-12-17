package day17;

public class ActivateRule<T extends Cell<T>> implements Rule<T> {
    @Override
    public boolean applyRule(T cell) {
        if (cell.isActive()) return false;

        long activeNeighborCount = cell.getActiveNeighborCount();
        if (activeNeighborCount == 3) {
            cell.setFutureActive();
            return true;
        }
        return false;
    }
}
