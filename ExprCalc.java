/******************************************************************
  Class to calculate expressions. It implements a simple LL(1)
  grammar to calculate simple expressions with the basic 
  operators +,-,*,/ and brackets.

  the grammar in EBNF notation:

  <expr>   -> <term> { '+' <term> } | <term> { '-' <term> } .
  <term>   -> <fact> { '*' <fact> } | <fact> { '/' <fact> } .
  <fact>   -> <nmeral> | '(' <expr> ')' .


  Author:       Arnold Beck
  e-mail:       beck@informatik.htw-dresden
  
******************************************************************/
// package beck.CalcTextField;
import java.lang.*;
import java.util.*;

public class ExprCalc
{
  private StringTokenizer T;

  private static final int MC_SYMB=1;
  private static final int MC_NUM =2;
  private static final int MC_NONE=0;
  private static final int MC_EOT =-1;

  // a token is represented by an tokencode (MCode)
  // and a tokenvalue (MSym or MNum) depending on
  // the tokencode

  private int	 MCode;
  private char   MSymb;
  private double MNum;

  private double Result;

  /***************************************************************
  Construcor, takes a String representing the expression
  ***************************************************************/

  public ExprCalc(String input) throws SyntaxErrorException
  {
    T=new StringTokenizer(input,"+-*/() \t",true);
    MCode=MC_NONE;
    Result = expr();
    if (MCode!=MC_EOT) throw new SyntaxErrorException();
  }

  /**************************************************************
  returns the calculated value of the expression
  **************************************************************/

  public double getResult() {return Result;}

  /**************************************************************
  the lexer to prduce a token when MCode is MC_NONE
  **************************************************************/

  private boolean lex() throws SyntaxErrorException
  {
    String Token;
    if (MCode==MC_NONE)
    {
      MCode=MC_EOT;MSymb='\0';MNum=0.0;
      try
      {
        do 
          Token=T.nextToken();
        while (Token.equals(" ")||Token.equals("\t"));
      }
      catch(NoSuchElementException e)  {return false;}
      // numeral
      if (Character.isDigit(Token.charAt(0)))
      {
        int i;
        for(i=0;i<Token.length() && 
                (Character.isDigit(Token.charAt(i)) ||
                 Token.charAt(i)=='.');i++);
        if (i!=Token.length())throw new SyntaxErrorException();
        try {MNum=(Double.valueOf(Token)).doubleValue();}
        catch (NumberFormatException e)
        {throw new SyntaxErrorException();}
        MCode=MC_NUM;
      }
      // symbol
      else
      {
        MSymb=Token.charAt(0);
        MCode=MC_SYMB;
      }
    }
    return true;
  }

  /****************************************************************
  expr implements the rule 
  <expr>   -> <term> { '+' <term> } | <term> { '-' <term> } .
  ****************************************************************/

  private double expr() throws SyntaxErrorException
  {
    double tmp=term();
    while (lex()
        && MCode==MC_SYMB
        && (MSymb=='+' || MSymb=='-'))
    {
      MCode=MC_NONE;
      if (MSymb=='+')tmp+=term(); else
                     tmp-=term();
    }
    if (MCode==MC_SYMB && MSymb=='(' 
    ||  MCode==MC_SYMB && MSymb==')' 
    ||  MCode==MC_EOT);
    else throw new SyntaxErrorException();
    return tmp;
  }

  /****************************************************************
  term implements the rule 
  <term>   -> <fact> { '*' <fact> } | <fact> { '/' <fact> } .
  ****************************************************************/
  
  private double term() throws SyntaxErrorException
  {
    double tmp=fac();
    while (lex()
        && MCode==MC_SYMB
        && (MSymb=='*' || MSymb=='/'))
    {
      MCode=MC_NONE;
      if (MSymb=='*')tmp*=fac(); else
                     tmp/=fac();
    }
    return tmp;
  }

  /****************************************************************
  fac implements the rule 
  <fact>   -> <nmeral> | '(' <expr> ')' .
  ****************************************************************/

  private double fac() throws SyntaxErrorException
  {
    double tmp;
    boolean minus=false;
   
    if(lex()&& MCode==MC_SYMB && MSymb=='-')
    {
      MCode=MC_NONE;
      minus=true;
    }
    if(lex() && MCode==MC_SYMB && MSymb=='(')
    {
      MCode=MC_NONE;
      tmp=expr();
      if(lex() && MCode!=MC_SYMB || MSymb!=')') 
        throw new SyntaxErrorException();
      MCode=MC_NONE;
    }else
    if (MCode==MC_NUM)
    {
      MCode=MC_NONE;
      tmp=MNum;
    }else throw new SyntaxErrorException();
    if (minus) tmp=-tmp;
    return tmp;
  }
  
}
