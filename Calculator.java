import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Calculator
{
    public static void main(String[] args) throws IOException
    {
        int a=0, b=0, result=0;
        
        boolean isRomanA = false, isRomanB = false, intA = false, intB = false;
        
        BufferedReader reader =
                   new BufferedReader(new InputStreamReader(System.in));
        String input = reader.readLine();
        
        String[] inputs = input.split(" ");        
        
        if(isNumeric(inputs[0])) {
            a = Integer.parseInt(inputs[0]);  
            intA = true;          
        } else {
            a = romanInt(inputs[0]);
            isRomanA = true;
        }
        
        if(isNumeric(inputs[2])) {
            b = Integer.parseInt(inputs[2]);
            intB = true;
        } else {
            b = romanInt(inputs[2]);
            isRomanB = true;
        }                
        
        // throwing invalid input exception
        if((isRomanA && intB) || (intA && isRomanB)) 
            throw new IllegalArgumentException("INVALID INPUT");

        if(inputs[1].equals("+")) {
            result = a + b;
        } else if(inputs[1].equals("-")) {
            result = a - b;
        } else if(inputs[1].equals("*")) {
            result = a * b;
        } else if(inputs[1].equals("/")) {
            result = a / b;
        }

        if(intA)
            System.out.println(result);       
        else {
            IntegerToRoman solution = new IntegerToRoman();
            System.out.format(solution.intToRoman(result));
        }
            
    }
    
    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?"); 
    }
    
    public static int romanInt(String s) {
        int intResult = 0;
        if(s.length() == 1) {
            switch(s) {
                case "I": intResult = 1 ; break;
                case "V": intResult = 5 ; break;
                case "X": intResult =10; break;
                default: ;// throw an error
            }
        } else if(s.length() > 1) {
            switch(s) {
                case "II": intResult = 2 ; break;
                case "III": intResult = 3 ; break;
                case "IV": intResult = 4; break;
                case "VI": intResult = 6; break;
                case "VII": intResult = 7; break;
                case "VIII": intResult = 8; break;
                case "IX": intResult = 9; break;
                default: ;// throw an error
            }
        }
        
        return intResult;
    }
}

class IntegerToRoman {
    private Map<Integer, String> romanChars = new HashMap<>();
  
    public IntegerToRoman() {
      romanChars.put(1, "I");
      romanChars.put(5, "V");
      romanChars.put(10, "X");
      romanChars.put(50, "L");
      romanChars.put(100, "C");
      romanChars.put(500, "D");
      romanChars.put(1000, "M");
      romanChars.put(5000, "V|");
   }
  
   public String intToRoman(int num) {
      if (num == 0) {
          return "";
      }
      int decimalFact = 0;
      StringBuilder result = new StringBuilder();
      for (int i = (int)Math.log10(num); i >= 0; i--) {
          int divisor = (int) Math.pow(10, i);
          decimalFact = num - num % divisor;
          result.append(convertDecimalFact(decimalFact));
          num = num % divisor;
      }
      return result.toString();
  }
  
  private String convertDecimalFact(int decimalFact){
    if(decimalFact == 0){return "";}
    int[] keyArray = romanChars.keySet().stream().mapToInt(key -> key) 
         .sorted().toArray(); 
  
    for(int i =0 ; i+1<keyArray.length ; i++){
        if( keyArray[i] <= decimalFact && decimalFact<= keyArray[i+1]  ){
           int bigger1stDgt = getLeftMostNum(keyArray[i+1]);
           int decimalFact1stDgt = getLeftMostNum(decimalFact);
           return decimalFact1stDgt >= bigger1stDgt-1 ? 
                  intToRoman(keyArray[i+1]-decimalFact)+romanChars.get(keyArray[i+1]): 
                  romanChars.get(keyArray[i])+intToRoman(decimalFact - keyArray[i]);
        }
    }      
    return "";
  }
  
  private int getLeftMostNum(int number) {
      int oneDgt = Integer.valueOf(Integer.valueOf(number).toString()
                   .substring(0, 0 +1));
      if(number<10){
          return oneDgt;
      }       
      int twoDgts = Integer.valueOf(Integer.valueOf(number).toString()
                    .substring(0, 0 +2));
      return twoDgts==10 ? twoDgts : oneDgt;
  }

}