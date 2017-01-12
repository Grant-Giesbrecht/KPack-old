class Test2{


	public static void main(String[] args){

		String str = new String("[Hello] th;ere,");

		String targets = new String(",;[]");

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

		System.out.println(str);
	}

}