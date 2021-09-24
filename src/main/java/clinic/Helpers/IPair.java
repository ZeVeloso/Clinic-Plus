package clinic.Helpers;

public interface IPair<T, U> {
    void setFirst(T first);

    void setSecond(U second);

    T getFirst();

    U getSecond();

    IPair<T,U> clone();
}