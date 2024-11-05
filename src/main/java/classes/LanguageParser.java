/* LanguageParser.java */
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
  private final static List<AErrorStruct> output = new ArrayList<AErrorStruct>();
  private boolean eof;

  public static List<Token> getTokens(String stream) {
    InputStream target = new ByteArrayInputStream(stream.getBytes());
    LanguageParser parser = new LanguageParser(target);
    return tokenize(parser);
  }

  public static ArrayList<AErrorStruct> analisadorSintatico(String input) {
    ArrayList<AErrorStruct> output = new ArrayList<>();
    LanguageParser parser = create(input);
    int maxAttempts = 100; // Limite de tentativas para evitar loop infinito
    int attempts = 0;

    while (attempts < maxAttempts) {
      try {
        parser.programa();
        break; // Saia do loop se a análise for bem-sucedida
      } catch (ParseException e) {
        output.add(new AErrorStruct("\n", e));
        parser.advanceAfterError(); // Avance no fluxo de entrada após encontrar um erro
        attempts++;
      }
    }

    if (attempts >= maxAttempts) {
      output.add(new AErrorStruct("Erro: Limite de tentativas atingido ao tentar avançar após erro.", null));
    }

    return output;
  }

  private void advanceAfterError() {
    int maxAttempts = 100; // Limite de tentativas para evitar loop infinito
    int attempts = 0;

    while (attempts < maxAttempts) {
        try {
            Token token = getNextToken();
            System.out.println("Consumed token: " + token); // Adicione esta linha para depuração
            if (token.kind == LanguageParserConstants.EOF || isSafePoint(token)) {
                break;
            }
        } catch (Exception e) {
            break;
        }
        attempts++;
    }

    if (attempts >= maxAttempts) {
        System.err.println("Erro: Limite de tentativas atingido ao tentar avançar após erro.");
    }
  }

private boolean isSafePoint(Token token) {
    return token.kind == LanguageParserConstants.PONTOVIRGULA || token.kind == LanguageParserConstants.FECHA_PARENTESES
        || token.kind == LanguageParserConstants.EOF || token.kind == LanguageParserConstants.END
        || token.kind == LanguageParserConstants.PONTO || token.kind == LanguageParserConstants.DOISPONTOS;
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
        System.out.println("LanguageParser: file " + args[0] + " was NAO found.");
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

    if (!ATokenHandler.isClosed()) {
      tokens.add(ATokenHandler.createToken());
    }
    return tokens;
  }

  static public String im(int x) {
    String s = tokenImage[x];
    int k = s.lastIndexOf("\"");
    try {
      s = s.substring(1, k);
    } catch (StringIndexOutOfBoundsException e) {
      // Handle exception OU log if necessary
    }
    return s;
  }

  public void consumeUntil(ARecoverySet g, ParseException e, String met) throws ParseException {
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

  // Analisador Sintatico
  // Produções
  final public void programa() throws ParseException {
    trace_call("programa");
    try {

      jj_consume_token(MAKE);
      identificador_do_programa();
      declaracao_constantes_variaveis();
      lista_comandos();
      jj_consume_token(END);
      jj_consume_token(PONTO);
    } finally {
      trace_return("programa");
    }
  }

  final public void identificador_do_programa() throws ParseException {
    trace_call("identificador_do_programa");
    try {

      switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
        case IDENTIFICADOR: {
          identificador();
          break;
        }
        default:
          jj_la1[0] = jj_gen;
          ;
      }
    } finally {
      trace_return("identificador_do_programa");
    }
  }

  final public void declaracao_constantes_variaveis() throws ParseException {
    trace_call("declaracao_constantes_variaveis");
    try {

      switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
        case CONST:
        case VAR: {
          constantes_e_variaveis();
          break;
        }
        default:
          jj_la1[1] = jj_gen;
          ;
      }
    } finally {
      trace_return("declaracao_constantes_variaveis");
    }
  }

  final public void constantes_e_variaveis() throws ParseException {
    trace_call("constantes_e_variaveis");
    try {

      switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
        case CONST: {
          declaracao_constantes();
          constantes_e_variaveis_prime();
          break;
        }
        case VAR: {
          declaracao_variaveis();
          constantes_e_variaveis_prime();
          break;
        }
        default:
          jj_la1[2] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
      }
    } finally {
      trace_return("constantes_e_variaveis");
    }
  }

  final public void constantes_e_variaveis_prime() throws ParseException {
    trace_call("constantes_e_variaveis_prime");
    try {

      switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
        case CONST:
        case VAR: {
          switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case CONST: {
              declaracao_constantes();
              constantes_e_variaveis_prime();
              break;
            }
            case VAR: {
              declaracao_variaveis();
              constantes_e_variaveis_prime();
              break;
            }
            default:
              jj_la1[3] = jj_gen;
              jj_consume_token(-1);
              throw new ParseException();
          }
          break;
        }
        default:
          jj_la1[4] = jj_gen;
          ;
      }
    } finally {
      trace_return("constantes_e_variaveis_prime");
    }
  }

  final public void declaracao_constantes() throws ParseException {
    trace_call("declaracao_constantes");
    try {

      jj_consume_token(CONST);
      constantes();
      jj_consume_token(END);
      jj_consume_token(PONTOVIRGULA);
    } finally {
      trace_return("declaracao_constantes");
    }
  }

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

  final public void constantes_prime() throws ParseException {
    trace_call("constantes_prime");
    try {

      switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
        case INT:
        case REAL:
        case CHAR:
        case BOOL: {
          constantes();
          break;
        }
        default:
          jj_la1[5] = jj_gen;
          ;
      }
    } finally {
      trace_return("constantes_prime");
    }
  }

  final public void declaracao_variaveis() throws ParseException {
    trace_call("declaracao_variaveis");
    try {

      jj_consume_token(VAR);
      variaveis();
      jj_consume_token(END);
      jj_consume_token(PONTOVIRGULA);
    } finally {
      trace_return("declaracao_variaveis");
    }
  }

  final public void variaveis() throws ParseException {
    trace_call("variaveis");
    try {

      tipo();
      jj_consume_token(DOISPONTOS);
      lista_identificadores();
      jj_consume_token(PONTO);
      variaveis_prime();
    } finally {
      trace_return("variaveis");
    }
  }

  final public void variaveis_prime() throws ParseException {
    trace_call("variaveis_prime");
    try {

      switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
        case INT:
        case REAL:
        case CHAR:
        case BOOL: {
          variaveis();
          break;
        }
        default:
          jj_la1[6] = jj_gen;
          ;
      }
    } finally {
      trace_return("variaveis_prime");
    }
  }

  final public void tipo() throws ParseException {
    trace_call("tipo");
    try {

      switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
        case INT: {
          jj_consume_token(INT);
          break;
        }
        case REAL: {
          jj_consume_token(REAL);
          break;
        }
        case CHAR: {
          jj_consume_token(CHAR);
          break;
        }
        case BOOL: {
          jj_consume_token(BOOL);
          break;
        }
        default:
          jj_la1[7] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
      }
    } finally {
      trace_return("tipo");
    }
  }

  final public void lista_identificadores() throws ParseException {
    trace_call("lista_identificadores");
    try {

      identificador();
      lista_identificadores_prime();
    } finally {
      trace_return("lista_identificadores");
    }
  }

  final public void lista_identificadores_prime() throws ParseException {
    trace_call("lista_identificadores_prime");
    try {

      switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
        case VIRGULA: {
          jj_consume_token(VIRGULA);
          lista_identificadores();
          break;
        }
        default:
          jj_la1[8] = jj_gen;
          ;
      }
    } finally {
      trace_return("lista_identificadores_prime");
    }
  }

  final public void lista_comandos() throws ParseException {
    trace_call("lista_comandos");
    try {

      comando();
      jj_consume_token(PONTO);
      lista_comandos_prime();
    } finally {
      trace_return("lista_comandos");
    }
  }

  final public void lista_comandos_prime() throws ParseException {
    trace_call("lista_comandos_prime");
    try {

      switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
        case GET:
        case PUT:
        case IF:
        case TRUE:
        case FALSE:
        case WHILE:
        case ABRE_PARENTESES:
        case NAO:
        case CONSTANTE_INTEIRA:
        case CONSTANTE_REAL:
        case CONSTANTE_LITERAL:
        case IDENTIFICADOR: {
          lista_comandos();
          break;
        }
        default:
          jj_la1[9] = jj_gen;
          ;
      }
    } finally {
      trace_return("lista_comandos_prime");
    }
  }

  final public void comando() throws ParseException {
    trace_call("comando");
    try {

      switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
        case TRUE:
        case FALSE:
        case ABRE_PARENTESES:
        case NAO:
        case CONSTANTE_INTEIRA:
        case CONSTANTE_REAL:
        case CONSTANTE_LITERAL:
        case IDENTIFICADOR: {
          atribuicao();
          break;
        }
        case GET: {
          entrada();
          break;
        }
        case PUT: {
          saida();
          break;
        }
        case IF: {
          selecao();
          break;
        }
        case WHILE: {
          repeticao();
          break;
        }
        default:
          jj_la1[10] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
      }
    } finally {
      trace_return("comando");
    }
  }

  final public void atribuicao() throws ParseException {
    trace_call("atribuicao");
    try {

      expressao();
      jj_consume_token(ATRIBUICAO);
      identificador();
    } finally {
      trace_return("atribuicao");
    }
  }

  final public void entrada() throws ParseException {
    trace_call("entrada");
    try {

      jj_consume_token(GET);
      jj_consume_token(ABRE_PARENTESES);
      lista_identificadores();
      jj_consume_token(FECHA_PARENTESES);
    } finally {
      trace_return("entrada");
    }
  }

  final public void saida() throws ParseException {
    trace_call("saida");
    try {

      jj_consume_token(PUT);
      jj_consume_token(ABRE_PARENTESES);
      lista_identificadores_e_ou_constantes();
      jj_consume_token(FECHA_PARENTESES);
    } finally {
      trace_return("saida");
    }
  }

  final public void lista_identificadores_e_ou_constantes() throws ParseException {
    trace_call("lista_identificadores_e_ou_constantes");
    try {

      item();
      lista_identificadores_e_ou_constantes_prime();
    } finally {
      trace_return("lista_identificadores_e_ou_constantes");
    }
  }

  final public void lista_identificadores_e_ou_constantes_prime() throws ParseException {
    trace_call("lista_identificadores_e_ou_constantes_prime");
    try {

      switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
        case VIRGULA: {
          jj_consume_token(VIRGULA);
          lista_identificadores_e_ou_constantes();
          break;
        }
        default:
          jj_la1[11] = jj_gen;
          ;
      }
    } finally {
      trace_return("lista_identificadores_e_ou_constantes_prime");
    }
  }

  final public void item() throws ParseException {
    trace_call("item");
    try {

      switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
        case IDENTIFICADOR: {
          identificador();
          break;
        }
        case CONSTANTE_INTEIRA: {
          jj_consume_token(CONSTANTE_INTEIRA);
          break;
        }
        case CONSTANTE_REAL: {
          jj_consume_token(CONSTANTE_REAL);
          break;
        }
        case CONSTANTE_LITERAL: {
          jj_consume_token(CONSTANTE_LITERAL);
          break;
        }
        case TRUE: {
          jj_consume_token(TRUE);
          break;
        }
        case FALSE: {
          jj_consume_token(FALSE);
          break;
        }
        default:
          jj_la1[12] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
      }
    } finally {
      trace_return("item");
    }
  }

  final public void selecao() throws ParseException {
    trace_call("selecao");
    try {

      jj_consume_token(IF);
      expressao();
      jj_consume_token(THEN);
      lista_comandos();
      senao();
      jj_consume_token(END);
      jj_consume_token(PONTO);
    } finally {
      trace_return("selecao");
    }
  }

  final public void senao() throws ParseException {
    trace_call("senao");
    try {

      switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
        case ELSE: {
          jj_consume_token(ELSE);
          lista_comandos();
          break;
        }
        default:
          jj_la1[13] = jj_gen;
          ;
      }
    } finally {
      trace_return("senao");
    }
  }

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

  final public void expressao() throws ParseException {
    trace_call("expressao");
    try {

      expressao_aritmetica_ou_logica();
      expressao_prime();
    } finally {
      trace_return("expressao");
    }
  }

  final public void expressao_prime() throws ParseException {
    trace_call("expressao_prime");
    try {

      switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
        case IGUAL:
        case DIFERENTE:
        case MENOR:
        case MAIOR:
        case MENOR_IGUAL:
        case MAIOR_IGUAL: {
          switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case IGUAL: {
              jj_consume_token(IGUAL);
              expressao_aritmetica_ou_logica();
              break;
            }
            case DIFERENTE: {
              jj_consume_token(DIFERENTE);
              expressao_aritmetica_ou_logica();
              break;
            }
            case MENOR: {
              jj_consume_token(MENOR);
              expressao_aritmetica_ou_logica();
              break;
            }
            case MAIOR: {
              jj_consume_token(MAIOR);
              expressao_aritmetica_ou_logica();
              break;
            }
            case MENOR_IGUAL: {
              jj_consume_token(MENOR_IGUAL);
              expressao_aritmetica_ou_logica();
              break;
            }
            case MAIOR_IGUAL: {
              jj_consume_token(MAIOR_IGUAL);
              expressao_aritmetica_ou_logica();
              break;
            }
            default:
              jj_la1[14] = jj_gen;
              jj_consume_token(-1);
              throw new ParseException();
          }
          break;
        }
        default:
          jj_la1[15] = jj_gen;
          ;
      }
    } finally {
      trace_return("expressao_prime");
    }
  }

  final public void expressao_aritmetica_ou_logica() throws ParseException {
    trace_call("expressao_aritmetica_ou_logica");
    try {

      termo2();
      menor_prioridade();
    } finally {
      trace_return("expressao_aritmetica_ou_logica");
    }
  }

  final public void menor_prioridade() throws ParseException {
    trace_call("menor_prioridade");
    try {

      switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
        case ADICAO:
        case SUBTRACAO:
        case OU: {
          switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case ADICAO: {
              jj_consume_token(ADICAO);
              termo2();
              menor_prioridade();
              break;
            }
            case SUBTRACAO: {
              jj_consume_token(SUBTRACAO);
              termo2();
              menor_prioridade();
              break;
            }
            case OU: {
              jj_consume_token(OU);
              termo2();
              menor_prioridade();
              break;
            }
            default:
              jj_la1[16] = jj_gen;
              jj_consume_token(-1);
              throw new ParseException();
          }
          break;
        }
        default:
          jj_la1[17] = jj_gen;
          ;
      }
    } finally {
      trace_return("menor_prioridade");
    }
  }

  final public void termo2() throws ParseException {
    trace_call("termo2");
    try {

      termo1();
      media_prioridade();
    } finally {
      trace_return("termo2");
    }
  }

  final public void media_prioridade() throws ParseException {
    trace_call("media_prioridade");
    try {

      switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
        case MULTIPLICACAO:
        case DIVISAO:
        case DIVISAO_INTEIRA:
        case RESTO:
        case E: {
          switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
            case MULTIPLICACAO: {
              jj_consume_token(MULTIPLICACAO);
              termo1();
              media_prioridade();
              break;
            }
            case DIVISAO: {
              jj_consume_token(DIVISAO);
              termo1();
              media_prioridade();
              break;
            }
            case DIVISAO_INTEIRA: {
              jj_consume_token(DIVISAO_INTEIRA);
              termo1();
              media_prioridade();
              break;
            }
            case RESTO: {
              jj_consume_token(RESTO);
              termo1();
              media_prioridade();
              break;
            }
            case E: {
              jj_consume_token(E);
              termo1();
              media_prioridade();
              break;
            }
            default:
              jj_la1[18] = jj_gen;
              jj_consume_token(-1);
              throw new ParseException();
          }
          break;
        }
        default:
          jj_la1[19] = jj_gen;
          ;
      }
    } finally {
      trace_return("media_prioridade");
    }
  }

  final public void termo1() throws ParseException {
    trace_call("termo1");
    try {

      elemento();
      maior_prioridade();
    } finally {
      trace_return("termo1");
    }
  }

  final public void maior_prioridade() throws ParseException {
    trace_call("maior_prioridade");
    try {

      switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
        case POTENCIA: {
          jj_consume_token(POTENCIA);
          elemento();
          maior_prioridade();
          break;
        }
        default:
          jj_la1[20] = jj_gen;
          ;
      }
    } finally {
      trace_return("maior_prioridade");
    }
  }

  final public void elemento() throws ParseException {
    trace_call("elemento");
    try {

      switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
        case IDENTIFICADOR: {
          identificador();
          break;
        }
        case CONSTANTE_INTEIRA: {
          jj_consume_token(CONSTANTE_INTEIRA);
          break;
        }
        case CONSTANTE_REAL: {
          jj_consume_token(CONSTANTE_REAL);
          break;
        }
        case CONSTANTE_LITERAL: {
          jj_consume_token(CONSTANTE_LITERAL);
          break;
        }
        case TRUE: {
          jj_consume_token(TRUE);
          break;
        }
        case FALSE: {
          jj_consume_token(FALSE);
          break;
        }
        case ABRE_PARENTESES: {
          jj_consume_token(ABRE_PARENTESES);
          expressao();
          jj_consume_token(FECHA_PARENTESES);
          break;
        }
        case NAO: {
          jj_consume_token(NAO);
          jj_consume_token(ABRE_PARENTESES);
          expressao();
          jj_consume_token(FECHA_PARENTESES);
          break;
        }
        default:
          jj_la1[21] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
      }
    } finally {
      trace_return("elemento");
    }
  }

  final public void identificador() throws ParseException {
    trace_call("identificador");
    try {

      jj_consume_token(IDENTIFICADOR);
    } finally {
      trace_return("identificador");
    }
  }

  final public void valor() throws ParseException {
    trace_call("valor");
    try {

      switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
        case CONSTANTE_INTEIRA: {
          jj_consume_token(CONSTANTE_INTEIRA);
          break;
        }
        case CONSTANTE_REAL: {
          jj_consume_token(CONSTANTE_REAL);
          break;
        }
        case CONSTANTE_LITERAL: {
          jj_consume_token(CONSTANTE_LITERAL);
          break;
        }
        case TRUE: {
          jj_consume_token(TRUE);
          break;
        }
        case FALSE: {
          jj_consume_token(FALSE);
          break;
        }
        default:
          jj_la1[22] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
      }
    } finally {
      trace_return("valor");
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
  final private int[] jj_la1 = new int[23];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
    jj_la1_init_0();
    jj_la1_init_1();
  }

  private static void jj_la1_init_0() {
    jj_la1_0 = new int[] { 0x0, 0x1800, 0x1800, 0x1800, 0x1800, 0x1e000, 0x1e000, 0x1e000, 0x10000000, 0x3ce0000,
        0x3ce0000, 0x10000000, 0xc00000, 0x200000, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x2c00000, 0xc00000, };
  }

  private static void jj_la1_init_1() {
    jj_la1_1 = new int[] { 0x200000, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x2e8000, 0x2e8000, 0x0, 0x2e0000, 0x0,
        0x1f80, 0x1f80, 0x4003, 0x4003, 0x206c, 0x206c, 0x10, 0x2e8000, 0xe0000, };
  }

  {
    enable_tracing();
  }

  /** Constructor with InputStream. */
  public LanguageParser(java.io.InputStream stream) {
    this(stream, null);
  }

  /** Constructor with InputStream and supplied encoding */
  public LanguageParser(java.io.InputStream stream, String encoding) {
    try {
      jj_input_stream = new JavaCharStream(stream, encoding, 1, 1);
    } catch (java.io.UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
    token_source = new LanguageParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 23; i++)
      jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
    ReInit(stream, null);
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
    try {
      jj_input_stream.ReInit(stream, encoding, 1, 1);
    } catch (java.io.UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 23; i++)
      jj_la1[i] = -1;
  }

  /** Constructor. */
  public LanguageParser(java.io.Reader stream) {
    jj_input_stream = new JavaCharStream(stream, 1, 1);
    token_source = new LanguageParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 23; i++)
      jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    if (jj_input_stream == null) {
      jj_input_stream = new JavaCharStream(stream, 1, 1);
    } else {
      jj_input_stream.ReInit(stream, 1, 1);
    }
    if (token_source == null) {
      token_source = new LanguageParserTokenManager(jj_input_stream);
    }

    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 23; i++)
      jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public LanguageParser(LanguageParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 23; i++)
      jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(LanguageParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 23; i++)
      jj_la1[i] = -1;
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null)
      token = token.next;
    else
      token = token.next = token_source.getNextToken();
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
    if (token.next != null)
      token = token.next;
    else
      token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    trace_token(token, " (in getNextToken)");
    return token;
  }

  /** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null)
        t = t.next;
      else
        t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private int jj_ntk_f() {
    if ((jj_nt = token.next) == null)
      return (jj_ntk = (token.next = token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[59];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 23; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1 << j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1 << j)) != 0) {
            la1tokens[32 + j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 59; i++) {
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

  private boolean trace_enabled;

  /** Trace enabled. */
  final public boolean trace_enabled() {
    return trace_enabled;
  }

  private int trace_indent = 0;

  /** Enable tracing. */
  final public void enable_tracing() {
    trace_enabled = true;
  }

  /** Disable tracing. */
  final public void disable_tracing() {
    trace_enabled = false;
  }

  protected void trace_call(String s) {
    if (trace_enabled) {
      for (int i = 0; i < trace_indent; i++) {
        System.out.print(" ");
      }
      System.out.println("Call:	" + s);
    }
    trace_indent = trace_indent + 2;
  }

  protected void trace_return(String s) {
    trace_indent = trace_indent - 2;
    if (trace_enabled) {
      for (int i = 0; i < trace_indent; i++) {
        System.out.print(" ");
      }
      System.out.println("Return: " + s);
    }
  }

  protected void trace_token(Token t, String where) {
    if (trace_enabled) {
      for (int i = 0; i < trace_indent; i++) {
        System.out.print(" ");
      }
      System.out.print("Consumed token: <" + tokenImage[t.kind]);
      if (t.kind != 0 && !tokenImage[t.kind].equals("\"" + t.image + "\"")) {
        System.out.print(": \"" + TokenMgrError.addEscapes(t.image) + "\"");
      }
      System.out.println(" at line " + t.beginLine + " column " + t.beginColumn + ">" + where);
    }
  }

  protected void trace_scan(Token t1, int t2) {
    if (trace_enabled) {
      for (int i = 0; i < trace_indent; i++) {
        System.out.print(" ");
      }
      System.out.print("Visited token: <" + tokenImage[t1.kind]);
      if (t1.kind != 0 && !tokenImage[t1.kind].equals("\"" + t1.image + "\"")) {
        System.out.print(": \"" + TokenMgrError.addEscapes(t1.image) + "\"");
      }
      System.out.println(
          " at line " + t1.beginLine + " column " + t1.beginColumn + ">; Expected token: <" + tokenImage[t2] + ">");
    }
  }

}
