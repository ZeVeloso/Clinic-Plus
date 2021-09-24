package clinic.Helpers;

import java.io.Serializable;

public class Triplet<T, U, V> implements ITriplet<T, U, V>, Serializable {

    private  T first;
    private  U second;
    private  V third;

    public Triplet(T first, U second, V third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public Triplet(Triplet<T,U,V> outroPair) {
        this.first = outroPair.getFirst();
        this.second = outroPair.getSecond();
        this.third = outroPair.getThird();
    }

    @Override
    public void setFirst(T first) {
        this.first = first;
    }

    @Override
    public void setSecond(U second) {
        this.second = second;
    }

    @Override
    public void setThird(V third) {
        this.third = third;
    }

    @Override
    public T getFirst() { return first; }
    @Override
    public U getSecond() { return second; }
    @Override
    public V getThird() { return third; }

    public Triplet<T,U,V> clone() {
        return new Triplet<>(this);
    }

    @Override
    public String toString() {
        return "first=" + first +
                " | second=" + second +
                " | third=" + third;
    }
}
