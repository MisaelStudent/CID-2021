package com.challenge.six;

public class TwoDoubleInput
{
    public double x;
    public double y;

    public boolean setInputStrings(String a, String b) {
        try {
            x = Double.parseDouble (a);
            y = Double.parseDouble (b);
        } catch (Exception e) {
            return false;
        }
    }
}
