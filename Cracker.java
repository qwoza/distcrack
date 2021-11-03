import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/*
 * This is absolutely NOT FINISHED....far from it. Development has only recently begun. Working on this in the very 
 * short free time I have.
 * Very basic start to finding permutations.
 * */
public class Cracker implements Runnable {
	private String targetHash;
	private String hashType;
	
	private String password;
	
	private int lengthMin;
	private int lengthMax;

	private boolean foundPass;

	private static final char[] ALPHABET_LOWERCASE = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
	//private static final char[] ALPHABET_UPPERCASE = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
	//private static final char[] NUMBERS = {'0','1','2','3','4','5','6','7','8','9'};
	//private static final char[] SYMBOLS = {'+',',','-','.','/','0','1','2','3','4','5','6','7','8','9',':',';','<','=','>','?','@','[','\\',']','^','_','`','{','|','}','~'};

	public Cracker(String targetHash, String hashType, int lengthMin, int lengthMax) {
		this.targetHash = targetHash;
		this.hashType = hashType;
		this.foundPass = false;
		this.lengthMin = lengthMin;
		this.lengthMax = lengthMax;
		this.password = null;
	}

	public void findPermutation(StringBuilder stringBuilder, int charNum) throws NoSuchAlgorithmException {
		if(this.foundPass) {
			System.out.println("Found password");
			return;
		}
		if(charNum == stringBuilder.length()) {
			this.foundPass = getHash(stringBuilder.toString()).equals(this.targetHash);
			this.password = stringBuilder.toString();
			return;
		} else {
			// obviously, ALPHABET_LOWERCASE will not be hard-coded into the loop like this
			// the character set to use will be configurable.
			for(int i = 0; i < ALPHABET_LOWERCASE.length && (!this.foundPass); i++) {
				char currentLetter = ALPHABET_LOWERCASE[i];
				stringBuilder.setCharAt(charNum, currentLetter);
				findPermutation(stringBuilder, charNum + 1);
			}
		}
	}	
	
	public String getHash(String text) throws NoSuchAlgorithmException {
		MessageDigest md;
		md = MessageDigest.getInstance(this.hashType);
		md.update(text.getBytes());
		return new String(md.digest());
	}

	@Override
	public void run() {
		try {
			for(int i = lengthMin; i <= lengthMax && !(this.foundPass); i++) {
				StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.setLength(i);
				findPermutation(stringBuilder, 0);
			}
			if(this.foundPass) {
				System.out.println("Found password: " + this.password);
			} else {
				System.out.println("Couldn't find password :(");
			}
		} catch(NoSuchAlgorithmException e) {
			System.err.println("Error with Hash Type given");
		}
	}
}