package src;

import org.zeromq.*;
import java.io.*;
import java.util.*;

public class Process2 {
    public static void main(String[] args) {
        List<Message> savedMessages = new ArrayList<>();
        boolean allChunksReceived = false;

        try (ZContext context = new ZContext()) {
            ZMQ.Socket receiveSocket = context.createSocket(SocketType.PULL);
            receiveSocket.bind("tcp://localhost:5557");

            ZMQ.Socket sendSocket = context.createSocket(SocketType.PUSH);
            sendSocket.connect("tcp://localhost:5558");

            while (!Thread.currentThread().isInterrupted()&&!allChunksReceived) {
                byte[] messageBytes = receiveSocket.recv(0);

                if (messageBytes == null) continue;

                Message message = deserializeMessage(messageBytes);
                String content = new String(message.getMessageType());

                if ("END".equals(content)) {
                    System.out.println("Process2 received 'END'.");
                    allChunksReceived = true;
                } else {
                    if (message.getProcess().equals("file-p2")) {
                        savedMessages.add(message);
                        System.out.println("Process2 saved message: " + content);
                    } else {
                        sendSocket.send(messageBytes, 0);
                        System.out.println("Process2 forwarded message: " + content);
                    }
                }
            }
            for (Message savedMessage : savedMessages) {
                byte[] serializedMessage = serializeMessage(savedMessage);
                sendSocket.send(serializedMessage, 0);
                System.out.println("Process2 sent saved message to Process3: " + new String(savedMessage.getFileContent()));
            }
            Message endMessage = new Message("END");
            sendSocket.send(serializeMessage(endMessage), 0);
            System.out.println("Process2 sent 'END' to Process3.");
        } catch (Exception e) {
            System.err.println("Error in Process2: " + e.getMessage());
        }
    }

    public static byte[] serializeMessage(Message message) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(message);
        objectOutputStream.flush();
        return byteArrayOutputStream.toByteArray();
    }

    public static Message deserializeMessage(byte[] messageBytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(messageBytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        return (Message) objectInputStream.readObject();
    }
}
