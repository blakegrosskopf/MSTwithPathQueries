
public class Link {
    
    int v1, v2;
    float w;

 
    public Link() {
	v1 = 0;
	v2 = 0;
	w = 0;
	}

    public Link(int x, int y, float z) {
	    v1 = x;
            v2 = y;
            w = z;
	}

    public void Set(int x, int y, float z) {
	    v1 = x;
            v2 = y;
            w = z;
	}

    public int v1() {
	return v1;
	}

    public int v2() {
	return v2;
	}

    public float w() {
	return w;
	}

    public void fix() {
	  if (v1 > v2) {
		int temp = v1;
		v1 = v2;
		v2 = temp;
	   }

	}

    public void print() {
	System.out.print("(" + v1 + " -- " + v2 + " : " + w + ")");


    }
}

