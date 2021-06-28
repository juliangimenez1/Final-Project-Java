package veterinaria.models.schedule;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import veterinaria.exceptions.NotAnExistingClient;
import veterinaria.exceptions.NotAnExistingTurn;
import veterinaria.models.client.Client;
import veterinaria.models.client.ClientCollection;
import veterinaria.util.ICollection;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Coleccion generica para objetos de la clase turn
 * @param Integer como clave de mapa
 * @param Turn como valor de mapa
 *
 */
public class Schedule implements ICollection, Serializable {
    // La clave del mapa es el numero de los turnos.
    private HashMap<Integer, Turn> turnHashMap;
    private static Scanner scan = new Scanner(System.in);

    public Schedule() {
        turnHashMap = new HashMap<Integer, Turn>();
    }

    /**
     *
     * @param obj Recibe un turno para agregar a la coleccion
     */
    @Override
    public void add(Object obj) {
        //Turn turno = (Turn) obj;
        if(obj instanceof Turn){
            if(!exist(((Turn) obj).getTurnNumber())){
                turnHashMap.put(((Turn) obj).getTurnNumber(), (Turn) obj);
            }
        }
    }

    /**
     * Metodo para mostrar los elementos de tipo Turn que tiene el HashMap
     * @return retorna un String con los elementos
     */
    @Override
    public String showCollection() {
        Iterator<Map.Entry<Integer, Turn>> it = turnHashMap.entrySet().iterator();
        StringBuilder builder = new StringBuilder();

        while(it.hasNext()){
            Map.Entry<Integer, Turn> entry = (Map.Entry<Integer, Turn>) it.next();
            if(turnHashMap.get((Integer)entry.getKey()).isStatus()) {
                builder.append("[" + entry.getKey() + "] " + entry.getValue().toString() + "\n");
            }
        }
        return builder.toString();
    }

    @Override
    public String showSpecific(int data) {
    return "";
    }

    /**
     * Metodo para saber si un turno existe
     * @param key Recibe un int para usar como key para buscar en el hashMap
     * @return retorna true si existe el Turno
     */
    public Boolean exist(Integer key){
        return turnHashMap.containsKey(key);
    }

    /**
     * Metodo para crear un turno y lo guarda en la coleccion
     * @param collection Recibe por parametro la coleccion
     */
    public void createTurn(ClientCollection collection){
        Turn turn;
        Client client;
        String dni="", reason, fecha;
        Date date;

        System.out.println("Ingrese el DNI del cliente: ");
        dni = scan.nextLine();
        try {
            client = collection.search(dni);
            if(client != null){
                do {
                    System.out.println("Ingrese la razon del turno: ");
                    reason = scan.nextLine();
                }while (reason.length()==0);
                do {
                    System.out.println("Ingrese la fecha del turno: dd/mm/yyyy");
                    fecha = scan.nextLine();
                    //
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
                    String dateInString = fecha;
                    date = null;
                    try {
                        date = sdf.parse(dateInString);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }while(fecha.length() != 10);
                turn = new Turn(client, reason, date);
                turnHashMap.put(turn.getTurnNumber(),turn);
            }
        } catch (NotAnExistingClient notAnExistingClient) {
            notAnExistingClient.printStackTrace();
        }

    }

    /**
     * Meotodo para pasar los elementos del HashMap a JSON
     * @return Retorna un JSONArray con los datos del HashMap
     */
    public JSONArray turnMapToJson() {

        JSONArray result = null;
        ArrayList<Turn> turnsArrayList = new ArrayList<>(turnHashMap.values());
        try {
            JSONArray turnJSONArray = new JSONArray();

            for (int i = 0; i < turnsArrayList.size(); i++) {
                JSONObject clientJSONObject = new JSONObject();
                JSONObject turnJSONObject = new JSONObject();
                clientJSONObject.put("name", turnsArrayList.get(i).getClient().getName());
                clientJSONObject.put("lastName", turnsArrayList.get(i).getClient().getLastName());
                clientJSONObject.put("DNI", turnsArrayList.get(i).getClient().getDNI());
                clientJSONObject.put("phone", turnsArrayList.get(i).getClient().getPhone());
                clientJSONObject.put("address", turnsArrayList.get(i).getClient().getAddress());
                clientJSONObject.put("paymentMethod", turnsArrayList.get(i).getClient().getPaymentMethod());

                turnJSONObject.put("turnNumber", turnsArrayList.get(i).getTurnNumber());
                turnJSONObject.put("client", clientJSONObject);
                turnJSONObject.put("reason", turnsArrayList.get(i).getReason());
                turnJSONObject.put("date", turnsArrayList.get(i).getDate());
                turnJSONObject.put("status",turnsArrayList.get(i).isStatus());

                turnJSONArray.put(turnJSONObject);
            }
            result = turnJSONArray;
        }catch(JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Metodo para hacer borrado logico del HashMap de los turnos
     * @param turnNumber recibe la key
     * @throws NotAnExistingTurn lanza la excepcion si el turno no existe
     */
    public void deleteTurn(int turnNumber) throws NotAnExistingTurn {
        if (turnHashMap.containsKey(turnNumber)) {
            turnHashMap.get(turnNumber).setStatus(false);
        }else{
            throw new NotAnExistingTurn("El turno ingresado no existe!");
        }
    }

    /**
     * Metodo para modificar la razon de un turno
     * @param turnNumber Recibe la key (int)
     * @param newReason Recibe la nueva razon (String)
     * @throws NotAnExistingTurn Lanza la excepcion en caso de que el turno no exista
     */
    public void modifyTurn(int turnNumber, String newReason) throws NotAnExistingTurn{
        if(turnHashMap.containsKey(turnNumber)) {
            turnHashMap.get(turnNumber).setReason(newReason);
        }else{
            throw new NotAnExistingTurn("El turno ingresado no existe!");
        }
    }


    @Override
    public String toString() {
        return "Schedule{" +
                "turnHashMap=" + turnHashMap +
                '}';
    }
}
