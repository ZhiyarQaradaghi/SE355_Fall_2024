package Week_4;

import java.io.*;
import java.net.*;
// *** if you want to make this secure - check chapter 10
// chapter 3 and 9
public class Complex_DT {
	public static void main(String...args){
		try(ServerSocket server = new ServerSocket(5000);) {
            while(true){
            try {
				Socket connection = server.accept();
                Department dep1 = new Department(null, 0);
                Student s1 = new Student(null, 0, 0, 0, dep1);
                OutputStream os = connection.getOutputStream();
                Writer w = new OutputStreamWriter(os);

                // biggest problem the first write for name should be the same for the read
                // if you move depname write to the top then read it first, it will conflict with student name 
                // w.write(s1.name);
                // w.write(s1.id);
                // w.write(s1.gpa);// problem - this is float
                // w.write(s1.age); 
                // w.write(s1.dept.deptcode);
                // w.write(s1.dept.depname);

                // we have 2 solutions, first solution is both sides should be in JVM/JAVA -- both sides need ObjectInputStream and ObjectOutputStream
                // solution 1 
                BufferedOutputStream bos = new BufferedOutputStream(connection.getOutputStream()); // this will send the entire data over one TCP segment and 1 acknowledgement - it wont send the write until you call flush or if it is full
                ObjectOutputStream oos = new ObjectOutputStream(bos);// if we dont use buffered, the objectoutputstream will keep sending multiple messages for each data to write, buffered will combine these calls into one segment and send it with flush or full
                oos.writeObject(dep1); 
                oos.flush();

                // image this is part of client and he wants to print the data
                ObjectInputStream ois = new ObjectInputStream(connection.getInputStream());
                Student p1 = (Student) ois.readObject();
                System.out.println(p1.name);


                connection.close();
            } catch(Exception ex) {  
                System.err.println(ex);
            }
          }
        } catch (Exception ex) {
            System.err.println("Catch1: "+ex);
        } 
	}
}

// Serializable for Object output stream
class Department implements Serializable {
    public String depname;
    public int depcode;

    public Department(String depname, int code) {
        this.depname = depname;
        this.depcode = code;
    }
}

class Student implements Serializable {
    public int id;
    public String name;
    public float gpa;
    public int age;

    public Department dept;
    // public transient Department dept;
    // use keyword: transient  to tell Serializable to ignore this

    // to give your object data versions, maybe at A student name is updated but at B it is older - this will show you which is newer
    public static final long serialVersionUID = 1234567L;

    public Student(String name, int id, float gpa, int age, Department dept) {
        this.name = name;
        this.id = id;
        this.gpa = gpa;
        this.age = age;
        this.dept = dept;
    }
}
