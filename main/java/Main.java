import com.fasterxml.jackson.core.JsonProcessingException;

public class Main {
    public static void main(String[] args) {
        try{
            String req = RequestHelper.getAuthRequest("txn_id","serv","acc",2454);
            System.out.println(req);
        }catch (Exception e){
            System.err.println("Oops :( "+e);
        }

    }
}
