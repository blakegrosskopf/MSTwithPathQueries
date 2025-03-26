import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import java.util.Collections;

public class Prog2test {

    public static void main(String[] args) {

	String filename = "src/test2_1.txt";
	int n = 0, e = 0, m = 0;
	ArrayList<Link> l1 = new ArrayList<Link>();
	ArrayList<Link> l2 = new ArrayList<Link>();
	ArrayList<Link> test = new ArrayList<Link>();

        try (Scanner fin = new Scanner(new File(filename))) {

		n = fin.nextInt();
		e = fin.nextInt();
		for (int i = 0; i < e; i++)
		{
		    int v1 = fin.nextInt();
		    int v2 = fin.nextInt();
		    float w = fin.nextFloat();
		    Link l = new Link(v1, v2, w);
		    l1.add(l);
		    l2.add(l);
		}

		m = fin.nextInt();
		for (int i = 0; i < m; i++)
		{
		    int v1 = fin.nextInt();
		    int v2 = fin.nextInt();
		    float w = fin.nextFloat();
		    Link l = new Link(v1, v2, w);
		    test.add(l);
		}





	} catch (FileNotFoundException ex) {
		System.out.println("File not found");

	}

	MyHelper helper = new MyHelper();

	ArrayList<Link> res1 = Prog2task.Task1(n, l1, helper);
	ArrayList<Link> res1a = new ArrayList<Link>();
	float w1 = (float)0.0;
 	for (int i = 0; i < res1.size(); i++) {
	    Link temp = res1.get(i);
	    w1 += temp.w();
	    temp.fix();
	    res1a.add(temp);
	}

	Collections.sort(res1a, new SortLink());

        System.out.print("Task 1 : [");
        System.out.print("(" + res1a.get(0).v1 + ", " + res1a.get(0).v2 + ")");
        for (int i = 1; i < res1a.size(); i++) {
             System.out.print(", (" + res1a.get(i).v1 + ", " + res1a.get(i).v2 + ")");
        }
        System.out.println("] " + w1);

	for (int i = 0; i < m; i++) {
		ArrayList<Link> res2 = Prog2task.Task2(n, l2, test.get(i), helper);
		test.get(i).print();
		if (res2.size() > 0) {
		   res2.get(0).fix();
		   System.out.print(" replaced edge: ") ;
		   res2.get(0).print();
		}
		else {
		   System.out.print(" not replaced");
		}
		System.out.println("");
	}

     }
}

