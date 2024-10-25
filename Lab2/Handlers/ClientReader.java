package Handlers;
import java.net.*;
import java.io.*;

public class ClientReader implements Runnable {
	private Socket conn;
    public int number;
	public ClientReader(Socket conn) {
		this.conn = conn;
	}

	@Override
	public void run() {
		try {
			BufferedReader bw = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			this.number = bw.read();
		} catch (Exception e) {
			System.err.println("Error in reading from client: "+e+" connection: "+conn.getInetAddress().getHostName());
		}
	}
}