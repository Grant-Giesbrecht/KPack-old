import kpack.*;

class Test{

	public static void main(String[] args){

		/*
		KMatrix Testing
		*/

		KMatrix km = new KMatrix("1, 2, 3; 4, 5, 6; 7, 8, 9");
		KMatrix kmi = new KMatrix("1, 0, 0;, 0, 1, 0; 0, 0, 1");
		// System.out.println(KVar.matrix_to_string(km, true));
		// System.out.println( (double)km.get(1, 1));



		/*
		KVar Write Testing
		*/
		// KVar kv = new KVar();
		
		// kv.set_header("This is a multiline\nheader.");
		
		// kv.add_double("Pi", 3.1415926535);
		// kv.add_double("two", 2, "This is a descriptor in a KV1 file. Wooooo");
		
		// kv.add_string("quote", "Do. Or do not. There is no try. - Yoda", "<- Best quote ever.");
		// kv.add_string("string_var", "This is a string. Woooo");
		
		// kv.add_int("two", 2, "The rule of 2.");
		// kv.add_int("int_var", 1000000);

		// kv.add_bool("bool_1", true);
		// kv.add_bool("bool_2", false, "FALSE");

		// kv.add_matrix("m1", km);
		// kv.add_matrix("identity", kmi, "3x3 Identity matrix");

		// kv.write("test_out.kv1");

		/*
		KVar Read Testing
		*/
		KVar kv = new KVar();
		// kv.print_descriptors(false);
		if (!kv.read("test_out.kv1")) System.out.println("Failed.");
		kv.print();

	}

}