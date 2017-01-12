package kpack;

/*
Maximum supported version: KV1-1.0
*/

import java.io.*;
import java.util.*;

public class KVar{

	/*---------------------------------------*\
	|--------------- MEATHODS ----------------|
	\*---------------------------------------*/

    //Default initializer
    public KVar(){

		//Initialize lists
		double_descriptors = new ArrayList<String>();
		int_descriptors = new ArrayList<String>();
		matrix_descriptors = new ArrayList<String>();
		string_descriptors = new ArrayList<String>();
		bool_descriptors = new ArrayList<String>();
		double_list = new ArrayList<Double>();
		int_list = new ArrayList<Integer>();
		string_list = new ArrayList<String>();
		bool_list = new ArrayList<Boolean>();
		matrix_list = new ArrayList<KMatrix>();
		double_vnames = new ArrayList<String>();
		matrix_vnames = new ArrayList<String>();
		bool_vnames = new ArrayList<String>();
		int_vnames = new ArrayList<String>();
		string_vnames = new ArrayList<String>();

		//Callsigns
		string_cs = new String("string");
		double_cs = new String("double");
		bool_cs = new String("bool");
		matrix_cs = new String("matrix");
		int_cs = new String("int");

		print_out = true;
		matrix_same_line = true;

		//newline
		newline = System.getProperty("Line.separator");
		if (newline == null){
			newline = "\n";
		}

		//header
		header = new String("");
		version = 1.0;

    }

	public void clear(){

		header = "";

		double_descriptors.clear();
		double_vnames.clear();
		double_list.clear();

		int_descriptors.clear();
		int_list.clear();
		int_vnames.clear();

		bool_descriptors.clear();
		bool_vnames.clear();
		bool_list.clear();

		string_descriptors.clear();
		string_list.clear();
		string_vnames.clear();

		matrix_descriptors.clear();
		matrix_list.clear();
		matrix_vnames.clear();
	}

	//--------------------------------------------------
	//-------------- "ADD_" MEATHODS -------------------

	public void add_string(String varname, String content){

		string_list.add(content);
		string_vnames.add(varname);
		string_descriptors.add("");

	}

	public void add_string(String varname, String content, String descriptor){

		string_list.add(content);
		string_vnames.add(varname);
		string_descriptors.add(descriptor);

	}

	public void add_double(String varname, double content){

		double_list.add( new Double(content) );
		double_vnames.add( varname );
		double_descriptors.add("");
	}

	public void add_double(String varname, double content, String descriptor){

		double_list.add( new Double(content));
		double_vnames.add( varname);
		double_descriptors.add(descriptor);

	}

	public void add_int (String varname, int content){

		int_descriptors.add("");
		int_list.add(new Integer(content));
		int_vnames.add(varname);


	}

	public void add_int(String varname, int content, String descriptor){
		
		int_descriptors.add(descriptor);
		int_list.add(new Integer(content));
		int_vnames.add(varname);

	}

	public void add_bool(String varname, boolean content){

		bool_descriptors.add("");
		bool_list.add(new Boolean(content));
		bool_vnames.add(varname);

	}

	public void add_bool(String varname, boolean content, String descriptor){

		bool_descriptors.add(descriptor);
		bool_list.add(new Boolean(content));
		bool_vnames.add(varname);

	}

	public void add_matrix(String varname, KMatrix content){

		matrix_descriptors.add("");
		matrix_list.add(content);
		matrix_vnames.add(varname);

	}

	public void add_matrix(String varname, KMatrix content, String descriptor){

		matrix_descriptors.add(descriptor);
		matrix_list.add(content);
		matrix_vnames.add(varname);

	}

	public void set_header(String new_header){

		header = new_header;

	}

	public String get_header(){
		return header;
	}

	public double get_version(){
		return version;
	}

	//-----------------------------------------------
	//----------------- WRITE MEATHOD ---------------

	//Write the current variables to file
	public boolean write(String filename){
        
		BufferedWriter fout = null;

		try{
			fout = new BufferedWriter( new FileWriter(filename) );

			//Write version
			fout.write("#VERSION " + version + newline + newline);

			//Write header
			if (header.length() > 0){
				fout.write("#HEADER" + newline);
				fout.write(header);
				fout.write(newline + "#HEADER" + newline + newline);
			}

			//Write doubles
			for (int i = 0 ; i < double_list.size() ; i++){
				fout.write( double_cs + " " + double_vnames.get(i) + " = " + double_list.get(i) + ";");
				if (double_descriptors.get(i).length() > 0){
					fout.write(" //" + double_descriptors.get(i));
				}
				fout.write(newline);
			}
			if (double_list.size() > 0) fout.write(newline);

			//Write strings
			for (int i = 0 ; i < string_list.size() ; i++){
				fout.write( string_cs + " " + string_vnames.get(i) + " = \"" + string_list.get(i) + "\";");
				if (string_descriptors.get(i).length() > 0){
					fout.write(" //" + string_descriptors.get(i));
				}
				fout.write(newline);
			}
			if (string_list.size() > 0) fout.write(newline);

			//Write ints
			for (int i = 0 ; i < int_list.size() ; i++){
				fout.write( int_cs + " " + int_vnames.get(i) + " = " + int_list.get(i) + ";");
				if (int_descriptors.get(i).length() > 0){
					fout.write(" //" + int_descriptors.get(i));
				}
				fout.write(newline);
			}
			if (int_list.size() > 0) fout.write(newline);

			//Write bools
			for (int i = 0 ; i < bool_list.size() ; i++){
				fout.write( bool_cs + " " + bool_vnames.get(i) + " = " + boolean_to_string(bool_list.get(i)) + ";");
				if (bool_descriptors.get(i).length() > 0){
					fout.write(" //" + bool_descriptors.get(i));
				}
				fout.write(newline);
			}
			if (bool_list.size() > 0) fout.write(newline);

			//Write matricies
			for (int i = 0 ; i < matrix_list.size() ; i++){
				fout.write( matrix_cs + " " + matrix_vnames.get(i) + " = " + matrix_to_string(matrix_list.get(i), matrix_same_line) + ";");
				if (matrix_descriptors.get(i).length() > 0){
					fout.write(" //" + matrix_descriptors.get(i));
				}
				fout.write(newline);
			}
			if (matrix_list.size() > 0) fout.write(newline);

		}catch(Exception e){
			if (print_out){
				System.out.println("ERROR: Failed to open file. " + e);
			}
			return false;
		}finally{
			try{
				fout.close();
			}catch(Exception e){
			}
		}

		return true;
	}

	//-------------------------------------------------
	//---------------- READ MEATHOD -------------------

	//Read the specified file and fill the object's variables
	public boolean read(String filename){

		BufferedReader fin = null;

		try{
			
			//Open file stream
			fin = new BufferedReader(new FileReader(filename));

			//Clear variable if read success
			clear();

			//Syntax check variables
			boolean first_item = true;
			boolean in_header = false;

			//Interpret file
			String line;
			int line_num = 0;
			while ((line = fin.readLine()) != null){
				
				//Increment line number
				line_num++;

				//Skip blank lines
				if (line.length() == 0) continue;

				//Parse string
				ensure_whitespace(line, "[],;");
				String[] words = line.split("\\s");

				// for (int i = 0 ; i < words.length ; i++){
				// 	System.out.println(words[i]);
				// }

				//Interpret line
				if (words[0].equals("#VERSION")){ //Version macro

					//Ensure is first element
					if (!first_item){
						if (print_out){
							System.out.println("ERROR: Version macro deteced not as first element.");
						}		
						return false;
					}

					//Read & save version
					try{
						version = Double.parseDouble(words[1]);
					}catch(Exception e){
						return false;
					}

					//Ensure version is compatible with this program
					if (version > MAX_VERSION){
						if (print_out) System.out.println("ERROR: Version exceeds program maximum");
						return false;
					}

				}else if(words[0].equals("#HEADER")){

					in_header = !in_header;

				}else if(words[0].equals(double_cs)){

					try{

						//Syntax check
						if (!words[2].equals("=")) return false;
						if (words[3].charAt(words[3].length()-1) != ';'){
							if (words[4].equals(";")){
								words[4] = "";
							}else{
								if (print_out) System.out.println("ERROR: No semicolon detected in line " + line_num);
								return false;
							}
						}else{
							words[3] = words[3].substring(0, words[3].length()-1);
						}

						//Find description
						String descr = new String("");
						for (int i = 4 ; i < words.length ; i++){
							if (descr.length() > 0 && words[i].length() > 0) descr = descr + " ";
							descr = descr + words[i];
						}
						if (descr.length() > 2) descr = descr.substring(2); //Remove double slash

						add_double(words[1], Double.parseDouble(words[3]), descr);

					}catch(Exception e){
						if (print_out) System.out.println("ERROR: Failed on line " + line_num + "\n" + e);
						return false;
					}

				}else if(words[0].equals(int_cs)){

					try{

						//Syntax check
						if (!words[2].equals("=")) return false;
						if (words[3].charAt(words[3].length()-1) != ';'){
							if (words[4].equals(";")){
								words[4] = "";
							}else{
								if (print_out) System.out.println("ERROR: No semicolon detected in line " + line_num);
								return false;
							}
						}else{
							words[3] = words[3].substring(0, words[3].length()-1);
						}

						//Find description
						String descr = new String("");
						for (int i = 4 ; i < words.length ; i++){
							if (descr.length() > 0 && words[i].length() > 0) descr = descr + " ";
							descr = descr + words[i];
						}
						if (descr.length() > 2) descr = descr.substring(2); //Remove double slash

						add_int(words[1], (int)Double.parseDouble(words[3]), descr);

					}catch(Exception e){
						if (print_out) System.out.println("ERROR: Failed on line " + line_num + "\n" + e);
						return false;
					}

				}else if(words[0].equals(bool_cs)){

					try{

						//Syntax check
						if (!words[2].equals("=")) return false;
						if (words[3].charAt(words[3].length()-1) != ';'){
							if (words[4].equals(";")){
								words[4] = "";
							}else{
								if (print_out) System.out.println("ERROR: No semicolon detected in line " + line_num);
								return false;
							}
						}else{
							words[3] = words[3].substring(0, words[3].length()-1);
						}

						//Find description
						String descr = new String("");
						for (int i = 4 ; i < words.length ; i++){
							if (descr.length() > 0 && words[i].length() > 0) descr = descr + " ";
							descr = descr + words[i];
						}
						if (descr.length() > 2) descr = descr.substring(2); //Remove double slash

						add_bool(words[1], string_to_boolean(words[3]), descr);


					}catch(Exception e){
						if (print_out) System.out.println("ERROR: Failed on line " + line_num + "\n" + e);
						return false;
					}

				}else if(words[0].equals(string_cs)){

					try{

						//Syntax check
						if (!words[2].equals("=")) return false;

						//Reel line
						boolean in_content = true;
						boolean last_word = false;
						boolean in_descriptor = false;
						String descriptor = new String("");
						String content_str = new String("");
						for (int i = 3 ; i < words.length ; i++){

							//Is first component of content
							if (i == 3 && words[3].charAt(0) != '\"'){
								if (print_out) System.out.println("ERROR: Line " + line_num + " - String requires encompasing double quotes.");
								return false;
							}else if (i == 3){
								words[3] = words[3].substring(1); //Remove double quote on first element of content
							}
							

							//Is last element of content
							for (int j = 0 ; j < words[i].length() ; j++){
								if ( words[i].charAt(j) == '\"'){									
									last_word = true; //Indicate that this is the last word of content
									
									//Verify semicolon syntax
									if ( words[i].length() >= j+2 && words[i].charAt(j+1) == ';' ){// Semicolon is next character 
										words[i] = words[i].substring(0, words[i].length() - 2); //Eliminate double quote and semicolon
									}else if( words.length >= i+2 && words[i+1].charAt(0) == ';' ){ //Semicolon is first character of next word
										words[i] = words[i].substring(0, words[i].length() -  2); //Eliminate double quote from word
										words[i+1] = words[i+1].substring(1, words[i+1].length()); //Eliminate semicolon from next word
									}else{ //No semicolon - fail
										if (print_out) System.out.println("ERROR: Failed to locate ending semicolon in line " + line_num);
										return false;
									}

								}
							}

							//Check if beginning descriptor
							if (words[i].charAt(0) == '/' && words[i].length() >= 2 && words[i].charAt(1) == '/'){
								in_descriptor = true;
								words[i] = words[i].substring(2);
							}

							//Check final categories for word categorezation
							if (in_content){ //In content

								//Check if is the last word
								if (last_word) in_content = false;

								if (content_str.length() > 0) content_str = content_str + " ";
								content_str = content_str + words[i];

							}else if(in_descriptor){ //In descriptor
								if (descriptor.length() > 0) descriptor = descriptor + " ";
								descriptor = descriptor + words[i];
							}else{ //unreconginzed word
								if (print_out) System.out.println("ERROR: Unrecognized token \"" + words[i] + "\" in line " + line_num + ".");
								return false;
							}	

						}

						add_string(words[1], content_str, descriptor);

					}catch(Exception e){
						if (print_out) System.out.println("ERROR: Failed on line " + line_num + "\n" + e);
						return false;
					}

				}else if(words[0].equals(matrix_cs)){

					try{

						// //Syntax check
						// if (!words[2].equals("=")) return false;
						// if (words[3].charAt(0) != '[') return false;
						// // if (wordswords[3] = words[3].substring(1);
						
						// String interpret = new String();

						// for (int i  = 3 ; i < words.length ; i++){
						// 	interpret = interpret + " " + words[i];
						// 	if (words[i].charAt(0) == ']'){
						// 		break;
						// 	}
						// }

						// KMatrix nm = new KMatrix(interpret);

						// String descriptor = new String();

						// add_matrix(words[1], nm, descriptor);

					}catch(Exception e){
						if (print_out) System.out.println("ERROR: Failed on line " + line_num + "\n" + e);
						return false;
					}

				}else if(in_header){

					if (header.length() > 0) header = header + '\n';
					header = header + line;

				}else{
					
				}

				first_item = false;
			}

		}catch(Exception e){
			if (print_out){
				System.out.println("ERROR: Failed to read file. " + e);
			}
			return false;
		}finally{
			try{

			}catch(Exception e){
			}
		}

		return true;
	}

	//---------------------------------------------------
	//------------------- PRINT MEATHOD -----------------

	public void print(){

		System.out.println("Version: " + version);
		System.out.println("Header: " + header + "<END HEADER");

		//Doubles
		for (int i = 0 ; i < double_list.size() ; i++){
			if (i == 0) System.out.println("DOUBLES:");
			System.out.print("\t" + double_vnames.get(i) + " = " + double_list.get(i));
			if (double_descriptors.get(i).length() > 0){
				System.out.println(" DESC: " + double_descriptors.get(i));
			}else{
				System.out.println();	
			}
		}

		//Ints
		for (int i = 0 ; i < int_list.size() ; i++){
			if (i == 0) System.out.println("INTEGERS:");
			System.out.print("\t" + int_vnames.get(i) + " = " + int_list.get(i));
			if (int_descriptors.get(i).length() > 0){
				System.out.println(" DESC: " + int_descriptors.get(i));
			}else{
				System.out.println();	
			}
		}

		//String
		for (int i = 0 ; i < string_list.size() ; i++){
			if (i == 0) System.out.println("STRINGS:");
			System.out.print("\t" + string_vnames.get(i) + " = \"" + string_list.get(i) + "\"");
			if (string_descriptors.get(i).length() > 0){
				System.out.println(" DESC: " + string_descriptors.get(i));
			}else{
				System.out.println();	
			}
		}

		//BOOLEANS
		for (int i = 0 ; i < bool_list.size() ; i++){
			if (i == 0) System.out.println("BOOLEANS:");
			System.out.print("\t" + bool_vnames.get(i) + " = " + bool_list.get(i));
			if (bool_descriptors.get(i).length() > 0){
				System.out.println(" DESC: " + bool_descriptors.get(i));
			}else{
				System.out.println();	
			}
		}

		//Matrix
		for (int i = 0 ; i < matrix_list.size() ; i++){
			if (i == 0) System.out.println("Matricies:");
			System.out.print("\t" + matrix_vnames.get(i) + " = " + matrix_to_string(matrix_list.get(i), true));
			if (matrix_descriptors.get(i).length() > 0){
				System.out.println(" DESC: " + matrix_descriptors.get(i));
			}else{
				System.out.println();	
			}
		}		

	}

	//---------------------------------------------------
	//------------------ PRIVATE MEATHODS ---------------

	private String boolean_to_string(boolean x){
		if (x){
			return "True";
		}else{
			return "False";
		}
	}

	private boolean string_to_boolean(String x){
		if (x.equals("True")){
			return true;
		}else{ //Should be "False"
			return false;
		}
	}

	public static String matrix_to_string(KMatrix x, boolean same_line){

		String out = new String("[ ");

		for (int r = 0 ; r+1 < x.rows() ; r++){
			for (int c = 0 ; c+1 < x.cols() ; c++){
				out = out + x.get(r, c) + ", ";
			}
			out = out + x.get(r, x.cols()-1) + "; ";
		}
		for (int c = 0 ; c+1 < x.cols() ; c++){
			out = out + x.get(x.rows()-1, c) + ", ";
		}
		out = out + x.get(x.rows()-1, x.cols()-1) + "]";

		return out;
	}

	private void ensure_whitespace(String str, String targets){

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
	}

	/*---------------------------------------*\
	|--------------- VARIABLES ---------------|
	\*---------------------------------------*/

	double version; //Version of file read or to write
	public static final double MAX_VERSION = 1.0; //Maximum supported version


	boolean print_out;
	boolean matrix_same_line;

	String last_filename; //Name of file to read or write
    String header;
	String newline;

	String double_cs;
	String int_cs;
	String bool_cs;
	String matrix_cs;
	String string_cs;
    
    List<String> double_descriptors;
	List<String> int_descriptors;
	List<String> string_descriptors;
	List<String> bool_descriptors;
	List<String> matrix_descriptors;

	List<Double> double_list;
	List<Integer> int_list;
	List<String> string_list;
	List<Boolean> bool_list;
	List<KMatrix> matrix_list;

	List<String> double_vnames;
	List<String> int_vnames;
	List<String> string_vnames;
	List<String> matrix_vnames;
	List<String> bool_vnames;

}


