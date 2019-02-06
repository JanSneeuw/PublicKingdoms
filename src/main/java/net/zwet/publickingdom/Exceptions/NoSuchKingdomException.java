package net.zwet.publickingdom.Exceptions;

public class NoSuchKingdomException extends Exception {
    public NoSuchKingdomException(){
        super();
    }
    public NoSuchKingdomException(String message){
        super(message);
    }
    public NoSuchKingdomException(Throwable cause){
        super(cause);
    }
    public NoSuchKingdomException(String message, Throwable cause){
        super(message, cause);
    }
}
