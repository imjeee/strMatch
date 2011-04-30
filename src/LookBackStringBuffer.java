import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

public class LookBackStringBuffer{

	
	private char[] line1;
	private char[] line2;
	private int lineWidth;
	
	private int offset;
	private int size;
	private boolean end;
	
	private BufferedReader br;
	
	public LookBackStringBuffer(String s) throws IOException{
		StringReader sr = new StringReader(s);
		br = new BufferedReader(sr);
		
		lineWidth = s.length();
		
		line1 = new char[lineWidth];
		line2 = new char[lineWidth];
		
		end = false;
		
		fillLines();
		
	}
	
	public LookBackStringBuffer(FileReader fr, int n) throws IOException{
		br = new BufferedReader(fr);
		
		lineWidth = n;
		
		line1 = new char[lineWidth];
		line2 = new char[lineWidth];
		
		end = false;
		
		fillLines();
		
	}
	
	
	private void printBuffer(){
		System.out.print("line1: ");
		for(char c : line1)
			System.out.print(c);
		System.out.println();
		
		System.out.print("Line2: ");
		for(char c : line2)
			System.out.print(c);
		System.out.println();
		
		System.out.println("Size: " + size);
		System.out.println("End: " + end);
	}
	
	
	
	private void fillLines() throws IOException{
		
		int read = br.read(line1, 0, lineWidth);
		
		
		// first line filled
		if (read == lineWidth){
			read = br.read(line2, 0, lineWidth);
			
			size = lineWidth;
			if (read == lineWidth){
				
				size += lineWidth;
			} else {
				end = true;
				
				if (read != -1)
					size += read;
			}
			
		} else {
			end = true;
			if (read != -1)
				size = read;
		}
		
	}
	
	public char charAt(int x) throws Exception{
		
		if (x < offset)
			throw new Exception("Cannot look farther back");
		
		int read = lineWidth;
		while (x >= offset + size && read == lineWidth){
			offset += lineWidth;
			
			char[] tmp = line1;
			line1 = line2;
			line2 = tmp;
			
			read = br.read(line2, 0, lineWidth);
			
			if (read != lineWidth){
				if (read != -1)
					size = size - lineWidth + read;
				else
					size = size - lineWidth;
			}
			
			
		}
		
		if (x >= offset + size)
			throw new Exception("End of buffer");
		
		return (x < (offset + lineWidth)) ? line1[x - offset] : line2[x - (offset + lineWidth)];
		
		
	}
	
	
	
	public boolean hasAvailable(int x)  {
		int total = x;
		int lookFor = total - (offset+size);
		if (lookFor <= 0)
			return true;
		
		try {
		br.mark(lookFor + 1);
		
		int read;
		
		
		
		while (lookFor > 0){
			read = br.read();
			lookFor--;
			if (read == -1){
				br.reset();
				return false;
			}
			
		}
		
		
		br.reset();
		return true;
		} catch (Exception e){
			return false;
		}
		
		
		
	}
	
	
	
}