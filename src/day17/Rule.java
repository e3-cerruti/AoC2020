package day17;

public interface Rule<T extends Cell<T>> {
    boolean applyRule(T cell);
}
