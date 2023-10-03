import java.util.*;

public class HangmanManager
{
	Set<String> s = new TreeSet<String>();
	Set<Character> guessedChars = new TreeSet<Character>();
	int GuessesLeft;
	int len;
	
	//this method fills up the set s with words that are the specified length if the max value is greater than 0, and the length value is greater than 1
	public HangmanManager( List<String> dictionary, int length, int max )throws IllegalArgumentException
	{
    if(max < 0 || length < 1){
      throw new IllegalArgumentException();
    }
    else{
      for(String str1 : dictionary){
        if(str1.length() == length){
          s.add(str1);
        }
      }
    }
    GuessesLeft = max;
	}
	//this method returns the set s
	public Set<String> words()
	{
		return s;
	}	
	//this method returns the amount of guesses the player has left
	public int guessesLeft()
	{
		return GuessesLeft;
	}
	//this method returns the set guessedChars
	public Set<Character> guesses()
	{
		return guessedChars;
	}
	//if the set s is not empty, this method will call method toStr on each string in set s
	public String pattern()
	{
    if(s.isEmpty()){
      throw new IllegalArgumentException();
    }
    return toStr(s.iterator().next());
	}
	
	public int record( char guess )
	{
		//this checks to see if the set s is empty, or if the player lost or won, and if any of those are true, the program will end with an IllegalArgumentException
		if(s.isEmpty() || GuessesLeft == 0) {
			throw new IllegalArgumentException();
		}
		else if(!s.isEmpty() && guessedChars.contains(guess)) {
			throw new IllegalArgumentException();
		}

		//this creates the family map
    Map <String, Set<String>> family = new TreeMap<String, Set<String>>();
    String firstpat = this.pattern();
    guessedChars.add(guess);

    //this checks to see if the pattern created by the guess is already in the family map, if its not then it will put it in the map, then it will get the value of that key and add a word to the value bound to that key
    for(String str2 : s){
      String curentPat = toStr(str2);
      
      if(!family.containsKey(curentPat)){
        family.put(curentPat, new TreeSet<String>());
      }
      family.get(curentPat).add(str2);
    }
    //this finds and removes the largest sized key
    String maxkey = "";
    int maxlength = 0;
    
    for(String k : family.keySet()){
      if(family.get(k).size() > maxlength){
        maxlength = family.get(k).size();
        maxkey = k;
      }
    }

    s = family.get(maxkey);

    //this subtracts the amount of guesses left by one if the player got their guess wrong
    if(this.pattern().equals(firstpat)){
      GuessesLeft--;
    }
    //returns the amount of times the players guessed character shows up in the correct word
		return letterAmt(this.pattern(), guess);
	}
//this method puts the guessed character in the right place when guessed
  public String toStr(String wrd){
    String strfin = "";

    for(int i = 0; i < wrd.length(); i++){
      if(guessedChars.contains(wrd.charAt(i))){
        strfin += wrd.substring(i, i+1);
      }
      else{
        strfin += "-";
      }
    }
    return strfin;
  }
//this method counts the times that the guessed character appears in the word you're trying to guess
  public int letterAmt(String wrd, char guess){
    int letterAmount = 0;
    for(int i = 0; i < wrd.length(); i++){
      if(wrd.charAt(i) == guess){
        letterAmount++;
      }
    }
    return letterAmount;
  }
}

