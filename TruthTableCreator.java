//*****************************************************************
//    Programmer: John Macken       MAT227 Honors Project
//    Start Date: Febuary 9,2017 Finish Date:
//    Description: This program will create truth tables based on
//       the users equation input.
//    Input: The user is promped to enter the equasion he wants a
//       truth table for.
//    Output: The truth table for an equasion the user entered.
//*****************************************************************

import java.util.Scanner;

class Equation//Holds up to 4 variables
{
   public char[] equation;
   //Variables
   public int[] variableValue;
   public int nVariables;
   public char[] variables;
   
   public int nVariable1;
   public int nVariable2;
   public int nVariable3;
   public int nVariable4;
   
   public int[] variable1Positions;
   public int[] variable2Positions;
   public int[] variable3Positions;
   public int[] variable4Positions;
   //Operators
   public int[] operators;
   public int nOperators;
   public int[] bracketPosition;
}

public class TruthTableCreator
{
   public static Scanner keyboard = new Scanner(System.in);
   
   public static void main(String[] args)
   {
      System.out.println("Welcome to the Truth Table Creator!");
      System.out.println("This program will create truth tables for entered equations");
      System.out.println("with up to 4 variables.");
      
      System.out.println("\n------------------------Symbol meaning-------------------------");
      System.out.println("1)Letters from 'a' to 'z' can be used as variables;");
      System.out.println("2)Braces '(' and ')' can be used to separate different actions;");
      System.out.println("3)'+' - OR; \n4)'*' - AND; \n5)'!' - negation;");
      System.out.println("6)'>' - Implication; \n7)'=' - Double Implication;");
      System.out.println("----Any other symboles are not used and considered illegal!----");
      
      Equation problem = new Equation();
      
      boolean working;
      do
      {
         working = false;
         System.out.print("\nEnter the equation:");
         String input = keyboard.next().toLowerCase();
         
         boolean valid = equationValidation(input, problem);
         if(valid)
         {
            initialize(input, problem);
            
            displayTruthTable(problem);
            
            boolean rightAnswer;
              
            System.out.print("\nWant to enter another equation(y/n):");
            char answer;
            
            do
            {
               rightAnswer = true;
               answer = keyboard.next().toLowerCase().charAt(0);
               
               if((answer != 'y') && (answer != 'n'))
               {
                  System.out.print("\nEnter either y or n:");
                  rightAnswer = false;
               }
            }while(!rightAnswer);
            
            if(answer == 'y')
            {
               working = true;
            }
         }
         else
         {
            working = true;
         }
      
      }while(working);
      
      System.out.println("\nThank you for using the Truth Table Creator!");
   }
   
   //Truth Table
   public static void displayTruthTable(Equation problem)
   {
      char[] equation = problem.equation;
      int nVar = problem.nVariables;
      char[] variables = problem.variables;
      
      for(int n = 0; n < nVar; n++)
      {
         System.out.print("|" + variables[n]);
      }
      
      System.out.println("|X|");
      
      for(int n = 0; n < nVar; n++)
      {
         System.out.print("--");
      }
      
      System.out.println("---");
      
      problem.variableValue = new int[nVar];
      int position = 0;
      
      for(int i = 0; i < Math.pow(2, nVar); i++)
      {
         int variableN = 0;
         
         for(int n = 0; n < nVar; n++)
         {
            displayValues(position + n, variableN, problem);
            variableN++;
         }
         System.out.print("|");
         
         //////////////////////////////////
         displayResult(problem);
         //////////////////////////////////
         
         System.out.println("|");
         
         position += nVar;
      }
   }
   
   //Helping Methods
   
   
   public static void displayValues(int position, int variableN, Equation problem)
   {
      int[] values;
      int nVar = problem.nVariables;
      
      switch(nVar)
      {
         case 1:
            values = new int[]{0, 1};
            break;
         case 2:
            values = new int[]{0, 0, 0, 1, 1, 0, 1, 1};
            break;
         case 3:
            values = new int[]{0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 1, 1, 1, 0, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1};
            break;
         case 4:
            values = new int[]{0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 1, 0, 0,
               1, 1, 1, 1, 0, 0, 0, 1, 0, 0, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 0, 1, 1, 0, 1, 1, 1, 1, 0,
               1, 1, 1, 1};
            break;
         default:
            System.out.println("Error in the number of variables!");
            values = new int[]{0};
            break;
      }
      
      if(variableN < nVar)
      {
         System.out.print("|" + values[position]);
         problem.variableValue[variableN] = values[position];
      }
   }
   
   //Displays the result of an equation
   
   public static void displayResult(Equation problem)
   {
      Equation subProblem = new Equation();
      subProblem.equation = problem.equation;
      subProblem.variableValue = problem.variableValue;
      subProblem.nVariables = problem.nVariables;
      subProblem.nOperators = problem.nOperators;
      subProblem.operators = problem.operators;
      subProblem.bracketPosition = problem.bracketPosition;
      
      subProblem.nVariable1 = problem.nVariable1;
      subProblem.nVariable2 = problem.nVariable2;
      subProblem.nVariable3 = problem.nVariable3;
      subProblem.nVariable4 = problem.nVariable4;
      
      subProblem.variable1Positions = problem.variable1Positions;
      subProblem.variable2Positions = problem.variable2Positions;
      subProblem.variable3Positions = problem.variable3Positions;
      subProblem.variable4Positions = problem.variable4Positions;
      
      getEquationWithValues(subProblem);
      
      int[] operators = subProblem.operators;
      
      for(int i = operators[0] - 1; i >= 0; i--)
      {
         solveBrackets(subProblem, i);
      }
      for(int n = 0; n < operators[1]; n++)
      {
         getNeg(subProblem);
      }
      for(int n = 0; n < operators[2]; n++)
      {
         getAND(subProblem);
      }
      for(int n = 0; n < operators[3]; n++)
      {
         getImpl(subProblem);
      }
      for(int n = 0; n < operators[4]; n++)
      {
         getOR(subProblem);
      }
      
      int finalResult = subProblem.equation[0];
      System.out.print(finalResult);
   }
   
   public static void getEquationWithValues(Equation problem)
   {
      char[] equation = problem.equation;
      int[] varVal = problem.variableValue;
      
      int nVar1 = problem.nVariable1;
      int nVar2 = problem.nVariable2;
      int nVar3 = problem.nVariable3;
      int nVar4 = problem.nVariable4;
      
      int[] var1Pos = problem.variable1Positions;
      int[] var2Pos = problem.variable2Positions;
      int[] var3Pos = problem.variable3Positions;
      int[] var4Pos = problem.variable4Positions;
      
      for(int i = 0; i < nVar1; i++)
      {
         equation[var1Pos[i]] = (char)varVal[0];
      }
      for(int i = 0; i < nVar2; i++)
      {
         equation[var2Pos[i]] = (char)varVal[1];
      }
      for(int i = 0; i < nVar3; i++)
      {
         equation[var3Pos[i]] = (char)varVal[2];
      }
      for(int i = 0; i < nVar4; i++)
      {
         equation[var4Pos[i]] = (char)varVal[3];
      }
   }
   
   public static void solveBrackets(Equation problem, int ix)
   {
      char[] equation = problem.equation;
      int pos = problem.bracketPosition[ix] + 1;
      int count = 0;
      
      while(equation[pos] != ')')
      {
         count++;
         pos++;
      }
      
      char[] temp = new char[count];
      pos = problem.bracketPosition[ix] + 1;
      int[] operators = {0,0,0,0,0};
      int operatorCount = 0;
      
      for(int i = 0; i < count; i++)
      {
         temp[i] = equation[pos];
         
         if(temp[i] == '!')
         {
            operators[1]++;
            operatorCount++;
         }
         else if(temp[i] == '*')
         {
            operators[2]++;
            operatorCount++;
         }
         else if((temp[i] == '>') || (temp[i] == '='))
         {
            operators[3]++;
            operatorCount++;
         }  
         else if(temp[i] == '+')
         {
            operators[4]++;
            operatorCount++;
         }
         pos++;
      }
      
      Equation subProb = new Equation();
      subProb.equation = temp;
      
      for(int n = 0; n < operators[1]; n++)
      {
         getNeg(subProb);
      }
      for(int n = 0; n < operators[2]; n++)
      {
         getAND(subProb);
      }
      for(int n = 0; n < operators[3]; n++)
      {
         getImpl(subProb);
      }
      for(int n = 0; n < operators[4]; n++)
      {
         getOR(subProb);
      }
      
      int result = subProb.equation[0];
      newEquation(problem, result, problem.bracketPosition[ix], count + 2);
   }  
   
   public static void getNeg(Equation problem)
   {
      char[] equation = problem.equation;
      int result = 0;
      boolean found = false;
      
      for(int n = 0; (!found) && (n < equation.length); n++)
      {
         if(equation[n] == '!')
         {
            if(equation[n + 1] == 0)
            {
               result = 1;
            }
            
            if(result > 1)
               result = 1;
            
            newEquation(problem, result, n, 2);
            found = true;
         }
      }
   }
   
   public static void getAND(Equation problem)
   {
      char[] equation = problem.equation;
      int result = 0;
      boolean found = false;
      
      for(int n = 0; (!found) && (n < equation.length); n++)
      {
         if(equation[n] == '*')
         { 
            result += equation[n-1] * equation[n+1];
            
            if(result > 1)
               result = 1;
            
            newEquation(problem, result, n - 1, 3);
            found = true;
         }
      }
   }
   
   public static void getImpl(Equation problem)
   {
      char[] equation = problem.equation;
      int result = 1;
      boolean found = false;
      
      for(int n = 0; (!found) && (n < equation.length); n++)
      {
         if(equation[n] == '>')
         {
            if((equation[n - 1] == 1) && (equation[n + 1] == 0))
            {
               result = 0;
            }
            
            newEquation(problem, result, n - 1, 3);
            found = true;
         }
         else if(equation[n] == '=')
         {
            if((equation[n - 1] == 0) && (equation[n + 1] == 1) || 
               (equation[n - 1] == 1) && (equation[n + 1] == 0))
            {
               result = 0;
            }
            
            newEquation(problem, result, n - 1, 3);
            found = true;
         }
      }
   }
   
   public static void getOR(Equation problem)
   {
      char[] equation = problem.equation;
      int result = 0;
      boolean found = false;
      
      for(int n = 0; (!found) && (n < equation.length); n++)
      {
         if(equation[n] == '+')
         {  
            result += equation[n-1] + equation[n+1];
            
            if(result > 1)
               result = 1;
               
            newEquation(problem, result, n - 1, 3);
            found = true;
         }
      }
   }
   
   //Helping method for Operators
   
   public static void newEquation(Equation problem, int result, int position, int getRid)
   {
      char[] equation = problem.equation;
      char[] newEquation = new char[equation.length - (getRid - 1)];
      int ix = 0;
      
      for(int i = 0; i < newEquation.length; i++)
      {
         if(i == position)
         {
            newEquation[i] = (char)result;
            ix += getRid;
         }
         else
         {
            newEquation[i] = equation[ix];
            ix++;
         }
      }
      problem.equation = newEquation;
   }
   
   //Finds any invalid input
   
   public static boolean equationValidation(String input, Equation problem)
   {
      boolean valid = true;
      int count = 0;
      char character;
      
      int variableCount = 0;
      char[] variables = new char[100];
      
      while((count < input.length()) && (valid))
      {
         character = input.charAt(count);
         
         if((character != '!') && (character != '+') && (character != '*')
            && (character != '(') && (character != ')') && ((character < 'a')
            || (character > 'z')) && (character != '>') && (character != '='))
         {
            System.out.println("\nWe found a character that can't be used in the equation!");
            System.out.println("Please re-enter your equation!");
            valid = false;
         }
         
         if((character >= 'a') && (character <= 'z') && (character != variables[0])
            && (character != variables[1]) && (character != variables[2])
            && (character != variables[3]))
         {
            variables[variableCount] = character;
            variableCount++;
         }
         
         if((character == '!') && (input.charAt(count - 1) >= 'a') && (input.charAt(count - 1) <= 'z'))
         {
            System.out.println("\nNegation can't be placed after a variable!");
            System.out.println("Please re-enter your equation!");
            valid = false;
         }
         else if(((character == '+') || (character == '*') || (character == '>') 
            || (character == '=')) && ((input.charAt(count - 1) == '+') || 
            (input.charAt(count - 1) == '*') || (input.charAt(count - 1) == '>') 
            || (input.charAt(count - 1) == '=') || (input.charAt(count + 1) == '+') || 
            (input.charAt(count + 1) == '*') || (input.charAt(count + 1) == '>') 
            || (input.charAt(count + 1) == '=')))
         {
            System.out.println("\nYou can't put operators '+', '*', '>' and '=' more than once in a row"
               + "\nor one after another!");
            System.out.println("Please re-enter your equation!");
            valid = false;
         }
         else if((count != 0 ) && (count < input.length() - 1) &&
               ((character >= 'a') && (character <= 'z')) && (((input.charAt(count + 1) >= 'a') &&  
               (input.charAt(count + 1) <= 'z')) || ((input.charAt(count - 1) >= 'a') && 
               (input.charAt(count - 1) <= 'z'))))
         {
            System.out.println("\nYou can't make a variable out of two letters!");
            valid = false;
         }
         count++;
      }
      
      if(variableCount > 4)
      {
         System.out.println("\nYou have more than 4 variables!");
         System.out.println("Please re-enter your equation!");
         valid = false;
      }
      else
      {
         char[] variablesFinal = new char[variableCount];
         for(int i = 0; i < variableCount; i++)
         {
            variablesFinal[i] = variables[i];
         }
         problem.nVariables = variableCount;
         problem.variables = variablesFinal;
      }
         
      return valid;
   }
   
   //Initializes the equation array and finds the positions of variables
   
   public static void initialize(String input, Equation problem)
   {
      int length = input.length();
      char[] equation = new char[length];
      
      int[] var1Pos = new int[length];
      int[] var2Pos = new int[length];
      int[] var3Pos = new int[length];
      int[] var4Pos = new int[length];
      
      int var1Count = 0;
      int var2Count = 0;
      int var3Count = 0;
      int var4Count = 0;
      
      int operatorCount = 0;
      int[] operatorList = {0, 0, 0, 0, 0};
      int[] bracketPos = new int[100];
      
      for(int n = 0; n < length; n++)
      {
         equation[n] = input.charAt(n);
         
         if((equation[n] >= 'a') && (equation[n] <= 'z'))
         {
            if((var1Count == 0) || (equation[n] == equation[var1Pos[0]]))
            {
               var1Pos[var1Count] = n;
               var1Count++;
            }
            else if((var2Count == 0) || (equation[n] == equation[var2Pos[0]]))
            {
               var2Pos[var2Count] = n;
               var2Count++;
            }
            else if((var3Count == 0) || (equation[n] == equation[var3Pos[0]]))
            {
               var3Pos[var3Count] = n;
               var3Count++;
            }
            else if((var4Count == 0) || (equation[n] == equation[var4Pos[0]]))
            {
               var4Pos[var4Count] = n;
               var4Count++;
            }
         }
         else if(equation[n] == '(')
         {
            bracketPos[operatorList[0]] = n;
            operatorList[0]++;
            operatorCount++;
         }
         else if(equation[n] == '!')
         {
            operatorList[1]++;
            operatorCount++;
         }
         else if(equation[n] == '*')
         {
            operatorList[2]++;
            operatorCount++;
         }
         else if((equation[n] == '>') || (equation[n] == '='))
         {
            operatorList[3]++;
            operatorCount++;
         }  
         else if(equation[n] == '+')
         {
            operatorList[4]++;
            operatorCount++;
         }
      }
      
      finalArray(var1Pos, var1Count);
      finalArray(var2Pos, var2Count);
      finalArray(var3Pos, var3Count);
      finalArray(var4Pos, var4Count);
      finalArray(bracketPos, operatorList[0]);
      
      problem.equation = equation;
      
      problem.nVariable1 = var1Count;
      problem.nVariable2 = var2Count;
      problem.nVariable3 = var3Count;
      problem.nVariable4 = var4Count;
      
      problem.variable1Positions = var1Pos;
      problem.variable2Positions = var2Pos;
      problem.variable3Positions = var3Pos;
      problem.variable4Positions = var4Pos;
      
      problem.nOperators = operatorCount;
      problem.operators = operatorList;
      problem.bracketPosition = bracketPos;
   }
   
   public static void finalArray(int[] array, int pos)
   {
      int[] temp = new int[pos];
      
      for(int i = 0; i < pos; i++)
      {
         temp[i] = array[i];
      }
      
      array = temp;
   }
}