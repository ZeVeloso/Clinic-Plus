package clinic.Helpers;

public interface ITriplet<T, U, V> {

    void setFirst(T first);

    void setSecond(U second);

    void setThird(V third);

    T getFirst();

    U getSecond();

    V getThird();

    ITriplet<T,U,V> clone();
}