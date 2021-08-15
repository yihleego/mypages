package io.leego.mypages.util;

/**
 * @author Yihleego
 */
public class Replacement implements Comparable<Replacement> {
    private final int begin;
    private final int end;
    private final String value;

    public Replacement(int begin, int end) {
        this.begin = begin;
        this.end = end;
        this.value = null;
    }

    public Replacement(int begin, int end, String value) {
        this.begin = begin;
        this.end = end;
        this.value = value;
    }

    public int getBegin() {
        return begin;
    }

    public int getEnd() {
        return end;
    }

    public String getValue() {
        return value;
    }

    @Override
    public int compareTo(Replacement o) {
        return Integer.compare(this.begin, o.begin);
    }
}
