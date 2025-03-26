import java.util.*;

class SortLink implements Comparator<Link>
{
   public int compare(Link a, Link b)
	{
           int result = 0;
	   if (a.v1 > b.v1)
	      result =  1;
	   else if (a.v1 < b.v1)
	      result =  -1;
	   else if (a.v2 > b.v2)
		result =  1;
	   else if (a.v2 < b.v2)
		result =  -1;
/*
           a.print();
           b.print();
           System.out.println(" " + result);
*/
	   return result;

	}

}
