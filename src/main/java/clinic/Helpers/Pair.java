package clinic.Helpers;

import java.io.Serializable;

public class Pair<T, U> implements IPair<T, U>, Serializable {

    private  T first;
    private  U second;

    public Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }

    public Pair(Pair<T,U> outroPair) {
        this.first = outroPair.getFirst();
        this.second = outroPair.getSecond();
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
    public T getFirst() { return first; }
    @Override
    public U getSecond() { return second; }

    @Override
    public Pair<T,U> clone() {
        return new Pair<>(this);
    }

    @Override
    public String toString() {
        return
                "1:" + first +
                        " | 2:" + second
                ;
    }
}

