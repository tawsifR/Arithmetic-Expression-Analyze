 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arithmeticexpressionalalyze;

/**
 *
 * @author TawsifR
*/

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Stack;

public class ArithmeticExpressionAnalyze {

/**
 * @param args the command line arguments
 */
     
    static String st;
    static int[] pr=new int[48];
    static Stack<Character> stack = new Stack<Character>();
    
    public static String shuntingYard(char[] ch, String[] identifiers){
        st="";
        for(int i=0;i<ch.length;i++){
            switch (ch[i]) {
                case (char)32:
                    break;
                case '+':
                case '-':
                case '*':
                case '/':
                    if(stack.isEmpty()){
                        stack.push(ch[i]);
                    }else if(pr[(int)ch[i]]>pr[(int)stack.peek()]){
                        //output=output+stack.pop();
                        stack.push(ch[i]);
                    }else if(pr[(int)ch[i]]<=pr[(int)stack.peek()]) {
                        st=st+stack.pop();
                        stack.push(ch[i]);
                    }   break;
                default:
                    boolean bl=false;
                    for (String identifier : identifiers){
                        if (ch[i] == identifier.charAt(0)) {
                            st=st+ch[i];
                            bl=true;
                            break;
                        }
                    }if(bl==false){ 
                        System.out.println("Compilation error.");
                        
                    }break;
            }
            
        } 
        while(!stack.isEmpty()){
            st=st+stack.pop();
        }
        return st;
    }
    public static int calculation(String s, String[] identifiers, int[] values){
        char [] result=s.toCharArray();
        int output;
        int operator_a,operator_b;
        boolean cBl;
        int sum,min,mul,div;
        
        Stack pStack=new Stack();
        for(int i=0; i<result.length; i++){
            cBl=false;
            for(int j=0;j< identifiers.length; j++){
                if(result[i]==identifiers[j].charAt(0)){
                    pStack.push(values[j]);
                    cBl=true;
                }
            }
            if(cBl==false){
                switch (result[i]) {
                    case '+':
                        operator_a=(int)pStack.pop();
                        operator_b=(int)pStack.pop();
                        sum=operator_a + operator_b;
                        pStack.push(sum);
                        break;
                    case '-':
                        operator_a=(int)pStack.pop();
                        operator_b=(int)pStack.pop();
                        min=operator_b - operator_a;
                        pStack.push(min);
                        break;
                    case '*':
                        operator_a=(int)pStack.pop();
                        operator_b=(int)pStack.pop();
                        mul=operator_a * operator_b;
                        pStack.push(mul);
                        break;
                    case '/':
                        operator_a=(int)pStack.pop();
                        operator_b=(int)pStack.pop();
                        div=operator_b / operator_a;
                        pStack.push(div);
                        break;
                    default:
                        break;
                }
            }
        }
        output=(int)pStack.pop();
        return output;
    }

    public static void main(String[] args) {
        // TODO code application logic here
        try{
            pr[42]=2; 
            pr[43]=1; 
            pr[45]=1; 
            pr[47]=2; 
            BufferedReader br = new BufferedReader (new InputStreamReader(System.in));
            System.out.println("Number of identifiers: ");
            int numerOfIndetifier=Integer.parseInt(br.readLine());
            
            String[]identifiers=new String[numerOfIndetifier];
            int[] value=new int[numerOfIndetifier];
            for(int i=0;i<numerOfIndetifier;i++){
                System.out.println("Enter variable name: ");
                identifiers[i]=br.readLine();
                System.out.println("Enter the value of the variable: ");
                value[i]=Integer.parseInt(br.readLine());
            }
            System.out.println("Number of Mathematical expression: ");
            int numOfExp=Integer.parseInt(br.readLine());
            String[] exp=new String[numOfExp];
            for(int i=0;i<numOfExp;i++){
                System.out.println("Enter expression: ");
                exp[i]=br.readLine();
            }
            char[] ch=new char[25];
            for(int i=0; i<numOfExp;i++){
                exp[i]=exp[i].replaceAll("\\s", "");
                ch=exp[i].toCharArray();
                String Str;
                Str = shuntingYard(ch,identifiers);
                System.out.println("Postfix: "+Str);
                System.out.println("Output: "+calculation(Str,identifiers,value ));
            }
        }catch(Exception e){
            System.out.println("Exception thrown  :" + e);
        }
    }
}
