package veterinaria.exceptions;

public class ExistingClient extends Exception {
    public ExistingClient(String message){
        super(message);
    };
}
