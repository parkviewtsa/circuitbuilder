/*
 * LICENCE BOILERPLATE
 */

/**
 ** JAVADOC
 **/

import java.io.*;

package line;

public enum TokenType {
  DELIM,
  QUOTED_SYM,
  NATIVE_SYM,
  NUMBER,
  STRING,
  IDENTIFIER
}

class Lexer {

  ArrayList<String> tokenize (FileReader in) throws IOException {
    ArrayList<String> list = new ArrayList<String>();
    char c;
    String current
    while ((c = in.read()) != -1) {
      if (c == '(' || c == ')') { // Parens form tokens on their own regardless
        ret.add(current);
        ret.add(Character.toString(c));
      } else if (Character.isWhitespace(c)) {
        ret.add(current);
      } else { // All other characters form parts of tokens
        current += Character.toString(c);
      }
      /* NOTE: In ordinary Common Lisp, only certain characters are allowed in
       * variable names, etc. Here we allow any Unicode character because it's
       * foolish to do extra work just to place useless restrictions on the
       * user. */
    }
    return list;
  }

  TokenType classifyToken (String token) {
    char firstChar = token.charAt(0);
    if (token == '(' || token == ')') {
      return DELIM;
    } else if (firstChar == '\'') {
      return QUOTED_SYM;
    } else if (firstChar == '_') {
      return NATIVE_SYM;
    } else if (Character.isDigit(firstChar)) {
      return NUMBER;
    } else if (firstChar == '\"') {
      return STRING;
    } else {
      return IDENTIFIER;
    }
  }

}