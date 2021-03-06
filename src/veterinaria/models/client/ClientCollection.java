package veterinaria.models.client;

import veterinaria.exceptions.ExistingClient;
import veterinaria.exceptions.NotAClientObjectException;
import veterinaria.exceptions.NotAnExistingClient;
import veterinaria.util.ICollection;

import java.io.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Colección genérica para objetos que hereden de la clase persona.
 * @param <E> Recibe un objeto que herede de la clase abstracta persona.
 */
public class ClientCollection<E extends Person> implements ICollection, Serializable {
    private HashSet<E> clientSet;
    private static Scanner scan = new Scanner(System.in);
    File file;

    public ClientCollection() {
        clientSet = new HashSet<E>();
    }

    /**
     *  Método para agregar objetos a la colección HashSet de clientes.
     *
     * @param obj Recibe un cliente para agregar a la colección.
     */
    @Override
    public void add(Object obj) {
        try {
            if (obj instanceof Client) {
                clientSet.add((E) obj);
            } else {
                throw new NotAClientObjectException("El objeto no es un cliente o ya existe en el sistema.");
            }
        }catch(NotAClientObjectException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
    * Este metodo se encarga de crear todos los datos de un cliente para agregarlo a la coleccion
    * */
    public void create() {
        try {
            int result;
            Client c;

            String name, lastName, DNI, phone, address, paymentMethod;
            System.out.println("~~~~~~~ Veterinaria Walrus ~~~~~~~\n");
            do {
                System.out.println("Ingrese el nombre del cliente: ");
                name = scan.nextLine();
            }while(name.length() == 0);
            do {
                System.out.println("Ingrese el apellido del cliente: ");
                lastName = scan.nextLine();
            }while(lastName.length() == 0);
            do {
                System.out.println("Ingrese el DNI del cliente: ");
                DNI = scan.nextLine();
            }while(DNI.length() == 0);
            do {
                System.out.println("Ingrese el teléfono del cliente: ");
                phone = scan.nextLine();
            }while(phone.length() == 0);
            do {
                System.out.println("Ingrese la dirección del cliente: ");
                address = scan.nextLine();
            }while(address.length() == 0);
            do {
                System.out.println("Ingrese el método de pago del cliente(Tarjeta/Efectivo): ");
                paymentMethod = scan.nextLine();
            }while (!paymentMethod.equals("Tarjeta") && !paymentMethod.equals("tarjeta") && !paymentMethod.equals("Efectivo") && !paymentMethod.equals("efectivo"));

            c = new Client(name, lastName, DNI, phone, address, paymentMethod);
            result = searchInt(c.getDNI());
            if(c != null && result == 0){

                clientSet.add((E) c);
            } else {
                throw new ExistingClient("Ya existe un cliente con ese DNI.");
            }
        }catch(ExistingClient ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Método para recuperar la colección de clientes en forma de String.
     * @return Un String con la información de todos los clientes.
     */
    @Override
    public String showCollection() {
        StringBuilder builder = new StringBuilder();
        Iterator<E> it = clientSet.iterator();
        while (it.hasNext()) {
            Client aux = (Client) it.next();
            if (aux.isStatus()) {
                builder.append(aux).append("\n");
            }
        }
        return builder.toString();
    }

    @Override
    public String showSpecific(int data) {
    return "";
    }

    /**
     * Método para actualizar la información de los clientes.
     */
    public void update() {
        int option;
        String DNI;
        Client found;
        System.out.println("Ingrese el DNI del cliente a modificar: ");
        DNI = scan.nextLine();
        try {
            found = search(DNI);
            if (found != null) {
                System.out.println("1 - Modificar nombre.");
                System.out.println("2 - Modificar apellido.");
                System.out.println("3 - Modificar teléfono.");
                System.out.println("4 - Modificar dirección.");
                System.out.println("0 - Regresar.");
                option = scan.nextInt();
                scan.nextLine();

                switch (option) {
                    case 1:
                        nameModify(found);
                        break;
                    case 2:
                        lastNameModify(found);
                        break;
                    case 3:
                        phoneModify(found);
                        break;
                    case 4:
                        addressModify(found);
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Ingrese una opción válida.");
                        break;
                }
            }
                } catch(NotAnExistingClient notAnExistingClient){
                notAnExistingClient.printStackTrace();
            }


        }


    /**
     * Método que actualiza el nombre de un objeto cliente por un nombre nuevo.
     * @param c Recibe un objeto cliente para modificar.
     */
    private void nameModify(Client c) {
        String name;

      // Buscamos el cliente y lo guardamos.
        clientSet.remove(c);        // Removemos el cliente del hashSet genérico.
        do {
            System.out.println("Ingrese el nuevo nombre del Cliente: ");
            name = scan.nextLine();
        }while(name.length() == 0);
        c.setName(name);         // Cambiamos el nombre del cliente.
        clientSet.add((E) c);           // Ingresamos el cliente actualizado en la colección.
    }

    /**
     * Método que actualiza el apellido de un objeto cliente por un apellido nuevo.
     * @param c Recibe un objeto cliente para modificar.
     */
    private void lastNameModify(Client c) {
        String lastName;

        // Buscamos el cliente y lo guardamos.
        clientSet.remove(c);        // Removemos el cliente del hashSet genérico.
        do {
            System.out.println("Ingrese el nuevo apellido del Cliente: ");
            lastName = scan.nextLine();
        }while(lastName.length() == 0);
        c.setLastName(lastName);         // Cambiamos el nombre del cliente.
        clientSet.add((E) c);           // Ingresamos el cliente actualizado en la colección.
    }
    /**
     * Método que actualiza el teléfono de un objeto cliente por un teléfono nuevo.
     * @param c Recibe un objeto cliente para modificar.
     */
    private void phoneModify(Client c) {
        String phone;

        // Buscamos el cliente y lo guardamos.
        clientSet.remove(c);        // Removemos el cliente del hashSet genérico.
        do {
            System.out.println("Ingrese el nuevo teléfono del Cliente: ");
            phone = scan.nextLine();
        }while(phone.length() == 0);
        c.setPhone(phone);         // Cambiamos el nombre del cliente.
        clientSet.add((E) c);           // Ingresamos el cliente actualizado en la colección.
    }

    /**
     * Método que actualiza la dirección de un objeto cliente por una dirección nueva.
     * @param c Recibe un objeto cliente para modificar.
     */
    private void addressModify(Client c) {
        String address;

        // Buscamos el cliente y lo guardamos.
        clientSet.remove(c);        // Removemos el cliente del hashSet genérico.
        do {
            System.out.println("Ingrese la nueva dirección del Cliente: ");
            address = scan.nextLine();
        }while(address.length() == 0);
        c.setAddress(address);         // Cambiamos el nombre del cliente.
        clientSet.add((E) c);           // Ingresamos el cliente actualizado en la colección.
    }

    /**
     * Método que busca dentro del HashSet de clientes si un cliente ya existe dentro de la misma.
     * @param DNI Recibe el DNI del ciente en formato String.
     * @return Devuelve el cliente encontrado.
     * @throws NotAnExistingClient Invoca una excepción si el cliente no existe.
     */
    public Client search(String DNI) throws NotAnExistingClient {
        Iterator<E> it = clientSet.iterator();
        Client found = null;
        while (it.hasNext())
        {
            Client aux = (Client)it.next();
            if(aux.getDNI().equals(DNI)) {
                found = aux;
                break;
            }
        }if(found == null){
            throw new NotAnExistingClient("El cliente ingresado no existe");
        }

        return found;
    }

    private int searchInt(String DNI) {
        Iterator<E> it = clientSet.iterator();
        int result = 0;
        while(it.hasNext()){
            Client aux = (Client)it.next();
            if(aux.getDNI().equals(DNI)) {
                result = 1;
                break;
            }
        }
        return result;
    }

    /**
     * Método para remover un cliente de la colección.
     * @param obj Recibe un cliente a eliminar.
     */
    private void remove(E obj) {
        if(obj instanceof Client) {
            clientSet.remove(obj);
        }
    }

    /**
     * Método para remover un cliente de la colección.
     * Busca el cliente en base a su DNI y elo elimina.
     */
    public void removeClient() {
        String DNI;
        Client found;
        System.out.println("Ingrese el DNI del cliente a remover: ");
        DNI = scan.nextLine();
        try {
            found = search(DNI);  // Buscamos el cliente y lo guardamos.
            remove((E) found);
            found.setStatus(false);
            clientSet.add((E) found);
        } catch (NotAnExistingClient notAnExistingClient) {
            System.out.println(notAnExistingClient.getMessage());
        }

    }

    /**
     * Método para copiar los clientes del sistema a un archivo Clients.dat
     * Si el archivo no existe, lo crea.
     */
    public void collectionToFile() {
        file = new File("Clients.dat");
        if (!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            FileOutputStream fos = new FileOutputStream("Clients.dat");

            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(clientSet);
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método para cargar los clientes del archivo hacia el sistema de la veterinaria.
     */
    public void fileToCollection() {
        file = new File("Clients.dat");
        if (file.exists()) {
            try {
                FileInputStream fis = new FileInputStream("Clients.dat");
                ObjectInputStream ois = new ObjectInputStream(fis);
                clientSet = (HashSet<E>) ois.readObject();
                ois.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
