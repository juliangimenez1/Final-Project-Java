package veterinaria.models.client;

import veterinaria.models.pet.PetCollection;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashSet;
import java.util.Objects;

public class Client extends Person {
    private static int clientsQuantity = setClientsQuantity();
    private int id;
    private String paymentMethod;
    private PetCollection clientPetCollection;
    private boolean status;
    private static File file;

    public Client() {
        super();
        clientsQuantity++;
        id = clientsQuantity;
        paymentMethod = "";
        clientPetCollection = new PetCollection();
        status = true;
    }

    public Client(String name, String lastName, String DNI, String phone, String address, String paymentMethod) {
        super(name, lastName, DNI, phone, address);
        clientsQuantity++;
        this.id = clientsQuantity;
        this.paymentMethod = paymentMethod;
        clientPetCollection = new PetCollection();
        status = true;
    }


    public String getPaymentMethod() {
        return paymentMethod;
    }

    public boolean isStatus() {
        return status;
    }

    public PetCollection getClientPetCollection() {
        return clientPetCollection;
    }

    /**
     * Metodo para actualizar la cantidad de clientes existentes.
     * Verifica si el archivo de clientes ya existe al inicializar el sistema.
     * Si existe, configura el valor de la cantidad de clientes existentes en base al último id del archivo.
     * Si no existe, configura el valor en 0.
     * @return
     */
    private static int setClientsQuantity() {
        file = new File("Clients.dat");
        int id = 0;
        if (file.exists()){
            try{
                FileInputStream fis = new FileInputStream("Clients.dat");
                ObjectInputStream ois = new ObjectInputStream(fis);
                HashSet<Client> clientSet = (HashSet<Client>) ois.readObject();
                id = clientSet.size();
            }catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return id;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Client client = (Client) o;
        return id == client.id && Objects.equals(paymentMethod, client.paymentMethod) && Objects.equals(clientPetCollection, client.clientPetCollection);
    }

    @Override
    public String toString() {
        return "[Cliente ID: " + id + "] {" +
                super.toString() +
                ", Método de Pago: '" + paymentMethod + '\'' +
                '}' + "\nMascotas: \n" + clientPetCollection.showCollection();
    }
}
