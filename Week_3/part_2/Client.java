package Week_3.part_2;
import java.util.*;
import java.net.*;
import java.io.*;

// WHOIS server application - whois.internic.net - port 43 
// sending them a domain name will give you contact details
public class Client {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a domain name: "); 
        String domainName = scanner.nextLine();

        try(Socket socket = new Socket("whois.internic.net", 43)) {
    
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            OutputStream os = socket.getOutputStream();
            Writer out = new OutputStreamWriter(os,"ASCII"); // ascii is the unicode we will  use to convert the characters to binary (encoding stuff - we decode in reader by converting binary to char) 
    

            out.write(domainName+"\r\n");
            out.flush(); // hey java im done so send it to the OS

            StringBuilder response = new StringBuilder();
            String line = null;
            while((line = in.readLine())!=null) {
                response.append(line);
                response.append("\r\n");
            }
            System.out.println(response.toString());
            
        }catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }
}
