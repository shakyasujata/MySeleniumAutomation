package codeTest;

public class FindLengthOfLastWordOfString {

	public static void main(String[] args) {
		
		String testString = "The longest word in any of the major English language dictionaries is pneumonoultramicroscopicsilicovolcanoconiosis";
		
		String[] splittedArray = testString.split(" ");
		int splittedArrayLength = splittedArray.length;
		System.out.println("Number of words in test string is " + splittedArrayLength);
		
		for(int i=0;i<splittedArray.length;i++){
			System.out.println(splittedArray[i]);
		}
		
		String lastWord = splittedArray[splittedArrayLength-1];
		System.out.println("The last word  is :" + lastWord + " and the length of that last word is " + lastWord.length());
	}

}
