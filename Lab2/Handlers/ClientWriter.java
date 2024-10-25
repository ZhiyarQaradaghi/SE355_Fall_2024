package Handlers;
import java.net.*;
import java.io.*;

public class ClientWriter implements Runnable {
	private Socket conn;
    public int number;
	public ClientWriter(Socket conn, int number) {
		this.conn = conn;
		this.number = number;
	}

	@Override
	public void run() {
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			bw.write(this.number);
		} catch (Exception e) {
			System.err.println("Error in reading from client: "+e+" connection: "+conn.getInetAddress().getHostName());
		}
	}
}