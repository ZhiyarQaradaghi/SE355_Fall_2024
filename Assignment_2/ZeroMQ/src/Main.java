package src;

import org.zeromq.*;
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a file path:");
        String filePath = sc.nextLine();
        Set<String> processesAcknowledged = new HashSet<>();

        try (ZContext context = new ZContext(); FileInputStream fileInput = new FileInputStream(new File(filePath))) {
            ZMQ.Socket socket = context.createSocket(SocketType.PUB);
            socket.bind("tcp://127.0.0.1:5555");
            socket.bind("tcp://127.0.0.1:5556");
            socket.bind("tcp://127.0.0.1:5557");
            socket.bind("tcp://127.0.0.1:5558");
            socket.bind("tcp://127.0.0.1:5559");
            System.out.println("Connecting to subs...");
            Thread.sleep(1000);

            Random rand = new Random();
            byte[] buffer = new byte[10];
            int bytesRead;
            int counter = 0;
            long lamportClock = 0;
            
            String topic = "";
            List<Message> messageList = new ArrayList<>();

            while ((bytesRead = fileInput.read(buffer)) != -1) {
                String[] topics = {"file-p1", "file-p2", "file-p3", "file-p4", "file-p5"};
                topic = topics[rand.nextInt(topics.length)];  
                byte[] chunk = Arrays.copyOf(buffer, bytesRead);
                Message message = new Message(chunk, lamportClock, lamportClock + 1, false);
                messageList.add(message);
                byte[] serializedMessage = serializeMessage(message);
                socket.sendMore(topic);
                socket.send(serializedMessage, 0);
                System.out.println("Sent chunk " + counter + " to: " + topic + " (" + new String(chunk) + ")");
                lamportClock++;
                Thread.sleep(100);
                counter++;
            }
            
            
            Message allChunksMessage = new Message("END");
            byte[] allChunksMessageBytes = serializeMessage(allChunksMessage);            
            String[] topics = {"file-p1", "file-p2", "file-p3", "file-p4", "file-p5"};
            for (String Endtopic : topics) {
                socket.sendMore(Endtopic); 
                socket.send(allChunksMessageBytes, 0); 
                System.out.println("Sent allChunksMessage to " + Endtopic + ": " + allChunksMessage);
            }
            lamportClock++;
            
            
            
            try (ZContext contextAckToP = new ZContext()) {
                ZMQ.Socket ackToP = contextAckToP.createSocket(SocketType.PUB);
                ackToP.connect("tcp://127.0.0.1:8001");
                ackToP.connect("tcp://127.0.0.1:8002");
                ackToP.connect("tcp://127.0.0.1:8003");
                ackToP.connect("tcp://127.0.0.1:8004");
                ackToP.connect("tcp://127.0.0.1:8005");
                    Thread.sleep(1000);
                    String request = "ackToP ";
                    System.out.println("Sending " + request);
                    ackToP.sendMore("ackToP");
                    ackToP.send(request.getBytes(ZMQ.CHARSET), 1);
                    System.out.println("Waiting 15 seconds...");
                    Thread.sleep(1500);
                    lamportClock++;
                } catch (Exception ex) {
                    System.err.println(ex);
                }
                
                
                ZMQ.Socket receiveSocket = context.createSocket(SocketType.SUB);
                receiveSocket.bind("tcp://*:7001");
                receiveSocket.bind("tcp://*:7002");
                receiveSocket.bind("tcp://*:7003");
                receiveSocket.bind("tcp://*:7004");
                receiveSocket.bind("tcp://*:7005");
                receiveSocket.subscribe(""); 
                Message receivedMessage = null;
                boolean allChunksReceived = false;
                lamportClock++;
                
                ArrayList<Message> receivedMessages = new ArrayList<>();

                while (receivedMessages.size() < messageList.size()) {
                    Thread.sleep(100); 
                    byte[] messageBytes = receiveSocket.recv(0);
                    
                    System.out.println("Received message size: " + messageBytes.length);
                
                    try {
                        receivedMessage = deserializeMessage(messageBytes);
                    } catch (Exception e) {
                        System.out.println("Error deserializing message: " + e.getMessage());
                        continue;
                    }
                
                    byte[] content = receivedMessage.getFileContent();
                    if (content == null) {
                        System.out.println("Received message with null fileContent, skipping...");
                        continue;
                    }
                
                    if (content.length == 0) {
                        System.out.println("Received message with empty fileContent, skipping...");
                        continue;
                    }
                    
                    String messageContent = new String(content);
                    if ("END".equals(messageContent)) {
                        System.out.println("Received 'END' message, skipping...");
                        continue;
                    }
                    System.out.println("Received chunk: " + messageContent);
                    System.out.println("Old Lamport clock: " + receivedMessage.getOldLamportClock());
                    System.out.println("New Lamport clock: " + receivedMessage.getNewLamportClock());
                
                    long receivedLamportClock = receivedMessage.getNewLamportClock();
                    lamportClock = Math.max(lamportClock, receivedLamportClock) + 1;
                    receivedMessage.setNewLamportClock(lamportClock);
                
                    receivedMessages.add(receivedMessage);
                    System.out.println("Message list: "+messageList.size());
                    System.out.println("Received Message list: "+receivedMessages.size());
                    System.out.println("Received Message Content: " + messageContent);
                    System.out.println("Received Message Lamport Clock: " + receivedMessage.getNewLamportClock());

                }
                
                System.out.println("All chunks received. Total messages: " + receivedMessages.size());
                // System.out.println("Before sorting:");
                // for (Message msg : receivedMessages) {
                //     System.out.println("Message content: " + new String(msg.getFileContent()));
                //     System.out.println("Lamport Clock: " + msg.getNewLamportClock());
                // }
                
                mergeSort(receivedMessages);

                System.out.println("After sorting:");

                StringBuilder originalMessage = new StringBuilder();
                for (Message msg : receivedMessages) {
                    System.out.println("Message content: " + new String(msg.getFileContent()));
                    System.out.println("Lamport Clock: " + msg.getNewLamportClock());
                    originalMessage.append(new String(msg.getFileContent())); 
                }

                System.out.println("Reconstructed Original Message: " + originalMessage);

                String desktopPath = System.getProperty("user.home") + "/Desktop/reconstructed_message.txt";
                try (FileWriter fileWriter = new FileWriter(desktopPath)) {
                    fileWriter.write(originalMessage.toString());
                }
            
                System.out.println("Reconstructed message saved to: " + desktopPath);
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

    public static void mergeSort(List<Message> messages) {
        if (messages.size() <= 1) return; 
    
        int mid = messages.size() / 2;
        List<Message> left = new ArrayList<>(messages.subList(0, mid));
        List<Message> right = new ArrayList<>(messages.subList(mid, messages.size()));
    
        mergeSort(left);
        mergeSort(right);
    
        merge(messages, left, right);
    }
    
    private static void merge(List<Message> messages, List<Message> left, List<Message> right) {
        int i = 0, j = 0, k = 0;
        while (i < left.size() && j < right.size()) {
            if (left.get(i).getOldLamportClock() <= right.get(j).getOldLamportClock()) {
                messages.set(k++, left.get(i++));
            } else {
                messages.set(k++, right.get(j++));
            }
        }
    
        while (i < left.size()) messages.set(k++, left.get(i++));
        while (j < right.size()) messages.set(k++, right.get(j++));
    }

}
