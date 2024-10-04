package Week_3.part_2;
import java.util.*;
import java.net.*;
import java.io.*;

public class HTTPREq {
    public static void main(String[] args) {
        try(Socket socket = new Socket("ipinfo.io", 8000)) {
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();

            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            Writer out = new OutputStreamWriter(os, "ASCII");

            out.write("Get / HTTP/1.0\n");
            out.write("Host: ipinfo.io\n");
            out.write("\n");
            out.flush();


            StringBuilder response = new StringBuilder();
            String line = null;
            while((line = in.readLine())!=null) {
                response.append(line);
                response.append("\r\n");
            }
            System.out.println(response.toString());

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }
}
