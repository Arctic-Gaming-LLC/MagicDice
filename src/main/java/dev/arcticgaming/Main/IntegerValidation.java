package dev.arcticgaming.Main;

public class IntegerValidation{
    public boolean isInteger(String s){
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }
}
