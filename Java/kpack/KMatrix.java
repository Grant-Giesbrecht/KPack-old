package kpack;

import java.util.*;

public class KMatrix{

	/*---------------------------------------*\
	|--------------- MEATHODS ----------------|
	\*---------------------------------------*/

	public KMatrix(){
		content = new ArrayList<Double>();
		rows = 0;
		columns = 0;
	}

	public KMatrix(String initializer){
		
		content = new ArrayList<Double>();

		make(initializer);

	}

	public void clear(){
		content.clear();
		rows = 0;
		columns = 0;
	}

	public int rows(){
		return rows;
	}

	public int cols(){
		return columns;
	}

	public double get(int r, int c){

		//Verify r & c
		if (r >= rows || c >= columns) throw new IndexOutOfBoundsException("Incorrect row or column in KMatrix.");

		//Return content
		return content.get( r * columns + c );
	}

	public boolean resize(int r, int c){
		return true;
	}

	public boolean make(String input){

		int rows = 1;
		int cols = -1;
		List<Double> vals = new ArrayList<Double>();

 		//Ensure whitespace!
		input = ensure_whitespace(input, "[],;");
		String[] words = input.split("\\s");
	
		boolean was_number;
		double next;
		int incr = 0;
		for (int i = 0 ; i < words.length ; i++){

			if (words[i].length() == 0) continue; //Skip blank lines

			try{
				next = Double.parseDouble(words[i]);
				was_number = true;
				vals.add(next);
				incr++;
			}catch(Exception e){
				was_number = false;
			}

			if (!was_number && words[i].equals(";")){
				
				rows++; // Increment rows
				if (cols == -1){
					cols = incr;
				}else if(incr != cols){
					return false;
				}

				incr = 0; //Reset counter

			}else if(!was_number && words[i].equals(",")){

				//Do nothing

			}else if(!was_number){ //Unidentified character
				System.out.println("UC>" + words[i] + "<");
				return false;
			}

		}

		clear();
		this.rows = rows;
		this.columns = cols;
		for (int i =0 ; i < vals.size() ; i++){
			content.add(vals.get(i));
		}

		return true;
	}

	private String ensure_whitespace(String str, String targets){

		for (int i = 0 ; i < str.length() ; i++){
			for (int j = 0 ; j < targets.length() ; j++){
				if (str.charAt(i) == targets.charAt(j)){
					if ( i != 0 && str.charAt(i-1) != ' '){
						str = str.substring(0, i) + ' ' + str.substring(i);
					}else if(i+1 < str.length() && str.charAt(i+1) != ' '){
						str = str.substring(0, i+1) + ' ' + str.substring(i+1);
					}
				}
			}
		}

		return str;
	}

	/*---------------------------------------*\
	|--------------- VARIABLES ---------------|
	\*---------------------------------------*/

	List<Double> content;
	int rows;
	int columns;
	
}