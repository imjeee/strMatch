import java.util.Scanner;


public class StrMatch{
	public static void main(String[] args){
		if(args.length != 3){
			throw new IllegalArgumentException("usage: pattern.txt source.txt results.txt");
		}
		
		String pattern = args[0];
		String source = args[1];
		String results = args[2];
		
		Scanner s = new Scanner(pattern);
		s.useDelimiter("//&");
		while(s.hasNext()){
			System.out.println(s.next());
		}
	}

}
