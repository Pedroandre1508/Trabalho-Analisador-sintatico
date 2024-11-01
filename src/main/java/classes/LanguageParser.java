/* Generated By:JavaCC: Do not edit this line. LanguageParser.java */
package classes;

import java.util.List;
import java.util.ArrayList;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import util.OutputData;

public class LanguageParser implements LanguageParserConstants {
    private int contParseError = 0;
    private boolean debugRecovery = true;
    private final static List<ErrorStruct> output = new ArrayList<ErrorStruct>();
    private boolean eof;

    public static List<Token> getTokens(String stream) {
        InputStream target = new ByteArrayInputStream(stream.getBytes());
        LanguageParser parser = new LanguageParser(target);
        return tokenize(parser);
    }

    public static ArrayList<ErrorStruct> analisadorSintatico(String stream) {
        InputStream target = new ByteArrayInputStream(stream.getBytes());
        LanguageParser parser = new LanguageParser(target);
        try {
            parser.programa();
        } catch (ParseException e) {
            output.add(new ErrorStruct("Error parsing the program.\u005cn", e));
        }
        ArrayList<ErrorStruct> tmp = new ArrayList<ErrorStruct>(output);
        output.clear();
        return tmp;
    }

    public static LanguageParser create(String stream) {
        InputStream target = new ByteArrayInputStream(stream.getBytes());
        return new LanguageParser(target);
    }

    public static void main(String[] args) throws TokenMgrError, ParseException {
        LanguageParser parser;
        if (args.length == 0) {
            parser = new LanguageParser(System.in);
        } else if (args.length == 1) {
            try {
                parser = new LanguageParser(new java.io.FileInputStream(args[0]));
            } catch (java.io.FileNotFoundException e) {
                System.out.println("LanguageParser: file " + args[0] + " was not found.");
                return;
            }
        }
    }

    public static List<Token> tokenize(LanguageParser parser) {
        List<Token> tokens = new ArrayList<Token>();
        Token token = parser.getNextToken();

        while (token.kind != LanguageParserConstants.EOF) {
            tokens.add(token);
            token = parser.getNextToken();
        }

        if (!TokenHandler.isClosed()) {
            tokens.add(TokenHandler.createToken());
        }
        return tokens;
    }

    static public String im(int x) {
        String s = tokenImage[x];
        int k = s.lastIndexOf("\u005c"");
        try {
            s = s.substring(1, k);
        } catch (StringIndexOutOfBoundsException e) {
            // Handle exception or log if necessary
        }
        return s;
    }

    public void consumeUntil(RecoverySet g, ParseException e, String met) throws ParseException {
        Token tok;
        if (g == null) {
            throw e;
        }
        tok = getToken(1);
        while (!eof) {
            if (g.contains(tok.kind)) {
                break;
            }
            getNextToken();
            tok = getToken(1);
            if (tok.kind == EOF && !g.contains(EOF)) {
                eof = true;
            }
        }
        contParseError++;
    }

//Analisador Sintatico
// Produções
  final public void programa() throws ParseException {
    trace_call("programa");
    try {
      jj_consume_token(MAKE);
      identificador();
      declaracao_constantes_variaveis();
      lista_comandos();
      jj_consume_token(END);
      jj_consume_token(PONTO);
    } finally {
      trace_return("programa");
    }
  }

// <declaracao de constantes ou variaveis> ::= <declaracao de constante> | <declaracao de variavel> | £
  final public void declaracao_constantes_variaveis() throws ParseException {
    trace_call("declaracao_constantes_variaveis");
    try {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case CONST:
        declaracao_constante();
        break;
      case VAR:
        declaracao_variavel();
        break;
      default:
        jj_la1[0] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } finally {
      trace_return("declaracao_constantes_variaveis");
    }
  }

// <declaracao de constante> ::= const <constantes> end;
  final public void declaracao_constante() throws ParseException {
    trace_call("declaracao_constante");
    try {
      jj_consume_token(CONST);
      constantes();
      jj_consume_token(END);
      jj_consume_token(PONTOVIRGULA);
    } finally {
      trace_return("declaracao_constante");
    }
  }

// <constantes> ::= <tipo> : <lista de identificadores> = <valor> . <constantes'>
  final public void constantes() throws ParseException {
    trace_call("constantes");
    try {
      tipo();
      jj_consume_token(DOISPONTOS);
      lista_identificadores();
      jj_consume_token(IGUAL);
      valor();
      jj_consume_token(PONTO);
      constantes_prime();
    } finally {
      trace_return("constantes");
    }
  }

// <valor> ::= constante inteira | constante real | constante literal
  final public void valor() throws ParseException {
    trace_call("valor");
    try {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case CONSTANTE_INTEIRA:
        jj_consume_token(CONSTANTE_INTEIRA);
        break;
      case CONSTANTE_REAL:
        jj_consume_token(CONSTANTE_REAL);
        break;
      case CONSTANTE_LITERAL:
        jj_consume_token(CONSTANTE_LITERAL);
        break;
      default:
        jj_la1[1] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } finally {
      trace_return("valor");
    }
  }

// <tipo> ::= int | real | char
  final public void tipo() throws ParseException {
    trace_call("tipo");
    try {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case INT:
        jj_consume_token(INT);
        break;
      case REAL:
        jj_consume_token(REAL);
        break;
      case CHAR:
        jj_consume_token(CHAR);
        break;
      default:
        jj_la1[2] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } finally {
      trace_return("tipo");
    }
  }

// <lista de identificadores> ::= identificador <lista de identificadores'>
  final public void lista_identificadores() throws ParseException {
    trace_call("lista_identificadores");
    try {
      identificador();
      lista_identificadores_prime();
    } finally {
      trace_return("lista_identificadores");
    }
  }

// <lista de identificadores'> ::= , identificador <lista de identificadores'> | £
  final public void lista_identificadores_prime() throws ParseException {
    trace_call("lista_identificadores_prime");
    try {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case VIRGULA:
        jj_consume_token(VIRGULA);
        identificador();
        lista_identificadores_prime();
        break;
      default:
        jj_la1[3] = jj_gen;
        ;
      }
    } finally {
      trace_return("lista_identificadores_prime");
    }
  }

// <constantes'> ::= <constantes> | £
  final public void constantes_prime() throws ParseException {
    trace_call("constantes_prime");
    try {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case INT:
      case REAL:
      case CHAR:
        constantes();
        break;
      default:
        jj_la1[4] = jj_gen;
        ;
      }
    } finally {
      trace_return("constantes_prime");
    }
  }

// <declaracao de variavel> ::= var <variaveis> end;
  final public void declaracao_variavel() throws ParseException {
    trace_call("declaracao_variavel");
    try {
      jj_consume_token(VAR);
      variaveis();
      jj_consume_token(END);
      jj_consume_token(PONTOVIRGULA);
    } finally {
      trace_return("declaracao_variavel");
    }
  }

// <variaveis> ::= <tipo'> : <lista de identificadores>.<variavel>
  final public void variaveis() throws ParseException {
    trace_call("variaveis");
    try {
      tipo_prime();
      jj_consume_token(DOISPONTOS);
      lista_identificadores();
      jj_consume_token(PONTO);
      variavel();
    } finally {
      trace_return("variaveis");
    }
  }

// <variavel> ::= <variaveis> | £
  final public void variavel() throws ParseException {
    trace_call("variavel");
    try {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case INT:
      case REAL:
      case CHAR:
      case BOOL:
        variaveis();
        break;
      default:
        jj_la1[5] = jj_gen;
        ;
      }
    } finally {
      trace_return("variavel");
    }
  }

// <tipo'> ::= <tipo> | bool
  final public void tipo_prime() throws ParseException {
    trace_call("tipo_prime");
    try {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case INT:
      case REAL:
      case CHAR:
        tipo();
        break;
      case BOOL:
        jj_consume_token(BOOL);
        break;
      default:
        jj_la1[6] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } finally {
      trace_return("tipo_prime");
    }
  }

// <lista de comandos> ::= <comando> <lista de comandos'>
  final public void lista_comandos() throws ParseException {
    trace_call("lista_comandos");
    try {
      comando();
      lista_comandos_prime();
    } finally {
      trace_return("lista_comandos");
    }
  }

// <lista de comandos'> ::= <lista de comandos> | £
  final public void lista_comandos_prime() throws ParseException {
    trace_call("lista_comandos_prime");
    try {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case GET:
      case PUT:
      case IF:
      case WHILE:
      case ADICAO:
      case SUBTRACAO:
      case MULTIPLICACAO:
      case DIVISAO:
      case CONSTANTE_INTEIRA:
      case CONSTANTE_REAL:
      case CONSTANTE_LITERAL:
      case IDENTIFICADOR:
        lista_comandos();
        break;
      default:
        jj_la1[7] = jj_gen;
        ;
      }
    } finally {
      trace_return("lista_comandos_prime");
    }
  }

// <comando> ::= <atribuicao> | <entrada> | <saida> | <selecao> | <repeticao>
  final public void comando() throws ParseException {
    trace_call("comando");
    try {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case ADICAO:
      case SUBTRACAO:
      case MULTIPLICACAO:
      case DIVISAO:
      case CONSTANTE_INTEIRA:
      case CONSTANTE_REAL:
      case CONSTANTE_LITERAL:
      case IDENTIFICADOR:
        atribuicao();
        break;
      case GET:
        entrada();
        break;
      case PUT:
        saida();
        break;
      case IF:
        selecao();
        break;
      case WHILE:
        repeticao();
        break;
      default:
        jj_la1[8] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } finally {
      trace_return("comando");
    }
  }

// <atribuicao> ::= <expressao> -> identificador.
  final public void atribuicao() throws ParseException {
    trace_call("atribuicao");
    try {
      expressao();
      jj_consume_token(ATRIBUICAO);
      identificador();
      jj_consume_token(PONTO);
    } finally {
      trace_return("atribuicao");
    }
  }

// <expressao> ::= <expressao aritmética ou lógica> <expressao'>
  final public void expressao() throws ParseException {
    trace_call("expressao");
    try {
      expressao_aritmetica_ou_logica();
      expressao_prime();
    } finally {
      trace_return("expressao");
    }
  }

// <expressao'> ::= = <expressao aritmética ou lógica> | <> <expressao aritmética ou lógica> | < | > | <= | >= | £
  final public void expressao_prime() throws ParseException {
    trace_call("expressao_prime");
    try {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case IGUAL:
      case DIFERENTE:
      case MENOR:
      case MAIOR:
      case MENOR_IGUAL:
      case MAIOR_IGUAL:
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case IGUAL:
          jj_consume_token(IGUAL);
          expressao_aritmetica_ou_logica();
          break;
        case DIFERENTE:
          jj_consume_token(DIFERENTE);
          expressao_aritmetica_ou_logica();
          break;
        case MENOR:
          jj_consume_token(MENOR);
          expressao_aritmetica_ou_logica();
          break;
        case MAIOR:
          jj_consume_token(MAIOR);
          expressao_aritmetica_ou_logica();
          break;
        case MENOR_IGUAL:
          jj_consume_token(MENOR_IGUAL);
          expressao_aritmetica_ou_logica();
          break;
        case MAIOR_IGUAL:
          jj_consume_token(MAIOR_IGUAL);
          expressao_aritmetica_ou_logica();
          break;
        default:
          jj_la1[9] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
        break;
      default:
        jj_la1[10] = jj_gen;
        ;
      }
    } finally {
      trace_return("expressao_prime");
    }
  }

// <expressao aritmética ou lógica> ::= <termo> <lista de termos>
  final public void expressao_aritmetica_ou_logica() throws ParseException {
    trace_call("expressao_aritmetica_ou_logica");
    try {
      termo();
      lista_termos();
    } finally {
      trace_return("expressao_aritmetica_ou_logica");
    }
  }

// <lista de termos> ::= <operação> <termo> <lista de termos> | £
  final public void lista_termos() throws ParseException {
    trace_call("lista_termos");
    try {
      operacao();
      termo();
      lista_termos();
    } finally {
      trace_return("lista_termos");
    }
  }

// <termo> ::= <fator> <lista de fatores>
  final public void termo() throws ParseException {
    trace_call("termo");
    try {
      fator();
      lista_fatores();
    } finally {
      trace_return("termo");
    }
  }

// <lista de fatores> ::= <operacao> <fator> <lista de fatores> | £
  final public void lista_fatores() throws ParseException {
    trace_call("lista_fatores");
    try {
      fator();
      operacao();
      fator();
    } finally {
      trace_return("lista_fatores");
    }
  }

// <fator> ::= identificador | constante
  final public void fator() throws ParseException {
    trace_call("fator");
    try {
      identificador();
    } finally {
      trace_return("fator");
    }
  }

// <operacao> ::= + | - | * | /
  final public void operacao() throws ParseException {
    trace_call("operacao");
    try {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case ADICAO:
        jj_consume_token(ADICAO);
        break;
      case SUBTRACAO:
        jj_consume_token(SUBTRACAO);
        break;
      case MULTIPLICACAO:
        jj_consume_token(MULTIPLICACAO);
        break;
      case DIVISAO:
        jj_consume_token(DIVISAO);
        break;
      default:
        jj_la1[11] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } finally {
      trace_return("operacao");
    }
  }

// <entrada> ::= read identificador.
  final public void entrada() throws ParseException {
    trace_call("entrada");
    try {
      jj_consume_token(GET);
      identificador();
      jj_consume_token(PONTO);
    } finally {
      trace_return("entrada");
    }
  }

// <saida> ::= write <valor>.
  final public void saida() throws ParseException {
    trace_call("saida");
    try {
      jj_consume_token(PUT);
      valor();
      jj_consume_token(PONTO);
    } finally {
      trace_return("saida");
    }
  }

// <selecao> ::= if <expressao> then <lista de comandos> else <lista de comandos> end.
  final public void selecao() throws ParseException {
    trace_call("selecao");
    try {
      jj_consume_token(IF);
      expressao();
      jj_consume_token(THEN);
      lista_comandos();
      jj_consume_token(ELSE);
      lista_comandos();
      jj_consume_token(END);
      jj_consume_token(PONTO);
    } finally {
      trace_return("selecao");
    }
  }

// <repeticao> ::= while <expressao> do <lista de comandos> end.
  final public void repeticao() throws ParseException {
    trace_call("repeticao");
    try {
      jj_consume_token(WHILE);
      expressao();
      jj_consume_token(DO);
      lista_comandos();
      jj_consume_token(END);
      jj_consume_token(PONTO);
    } finally {
      trace_return("repeticao");
    }
  }

// <identificador> ::= <LETTER> (<LETTER> | <DIGIT>)*
  final public void identificador() throws ParseException {
    trace_call("identificador");
    try {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case IDENTIFICADOR:
        jj_consume_token(IDENTIFICADOR);
        break;
      default:
        jj_la1[12] = jj_gen;
        ;
      }
    } finally {
      trace_return("identificador");
    }
  }

  /** Generated Token Manager. */
  public LanguageParserTokenManager token_source;
  JavaCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private int jj_gen;
  final private int[] jj_la1 = new int[13];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static private int[] jj_la1_2;
  static {
      jj_la1_init_0();
      jj_la1_init_1();
      jj_la1_init_2();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x60000,0x0,0x380000,0x0,0x380000,0x780000,0x780000,0x43800000,0x43800000,0x0,0x0,0x0,0x0,};
   }
   private static void jj_la1_init_1() {
      jj_la1_1 = new int[] {0x0,0x3800000,0x0,0x4,0x0,0x0,0x0,0xb8003c0,0xb8003c0,0x7e000,0x7e000,0x3c0,0x8000000,};
   }
   private static void jj_la1_init_2() {
      jj_la1_2 = new int[] {0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,};
   }

  /** Constructor with InputStream. */
  public LanguageParser(InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public LanguageParser(InputStream stream, String encoding) {
    try { jj_input_stream = new JavaCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new LanguageParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 13; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 13; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public LanguageParser(java.io.Reader stream) {
    jj_input_stream = new JavaCharStream(stream, 1, 1);
    token_source = new LanguageParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 13; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 13; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public LanguageParser(LanguageParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 13; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(LanguageParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 13; i++) jj_la1[i] = -1;
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      trace_token(token, "");
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }


/** Get the next Token. */
  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
      trace_token(token, " (in getNextToken)");
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private List<int[]> jj_expentries = new ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[65];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 13; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
          if ((jj_la1_2[i] & (1<<j)) != 0) {
            la1tokens[64+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 65; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  private int trace_indent = 0;
  private boolean trace_enabled = true;

/** Enable tracing. */
  final public void enable_tracing() {
    trace_enabled = true;
  }

/** Disable tracing. */
  final public void disable_tracing() {
    trace_enabled = false;
  }

  private void trace_call(String s) {
    if (trace_enabled) {
      for (int i = 0; i < trace_indent; i++) { System.out.print(" "); }
      System.out.println("Call:   " + s);
    }
    trace_indent = trace_indent + 2;
  }

  private void trace_return(String s) {
    trace_indent = trace_indent - 2;
    if (trace_enabled) {
      for (int i = 0; i < trace_indent; i++) { System.out.print(" "); }
      System.out.println("Return: " + s);
    }
  }

  private void trace_token(Token t, String where) {
    if (trace_enabled) {
      for (int i = 0; i < trace_indent; i++) { System.out.print(" "); }
      System.out.print("Consumed token: <" + tokenImage[t.kind]);
      if (t.kind != 0 && !tokenImage[t.kind].equals("\"" + t.image + "\"")) {
        System.out.print(": \"" + t.image + "\"");
      }
      System.out.println(" at line " + t.beginLine + " column " + t.beginColumn + ">" + where);
    }
  }

  private void trace_scan(Token t1, int t2) {
    if (trace_enabled) {
      for (int i = 0; i < trace_indent; i++) { System.out.print(" "); }
      System.out.print("Visited token: <" + tokenImage[t1.kind]);
      if (t1.kind != 0 && !tokenImage[t1.kind].equals("\"" + t1.image + "\"")) {
        System.out.print(": \"" + t1.image + "\"");
      }
      System.out.println(" at line " + t1.beginLine + " column " + t1.beginColumn + ">; Expected token: <" + tokenImage[t2] + ">");
    }
  }

}
