/************************************************
  Author:       Arnold Beck
  e-mail:       beck@informatik.htw-dresden
************************************************/

// package beck.CalcTextField;
import java.util.*;
import java.lang.*;

class SyntaxErrorException extends Exception
{
  SyntaxErrorException(){}
  SyntaxErrorException(String s) {
    super(s);
  }
}
