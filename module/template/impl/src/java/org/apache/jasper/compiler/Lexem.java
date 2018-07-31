/*
 * Copyright (c) 2017 Rusi Popov, MDA Tools.net All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Created on 09.09.2017
 */
package org.apache.jasper.compiler;

/**
 * The lexems in the template language.
 * @see TemplateLexer
 * @author Rusi Popov
 */
enum Lexem {
  END("eof",
      new GeneratorFactory() {
        public Generator construct(String text, Mark lexemStart, Mark lexemEnd) {
          Generator result;

          if ( text.isEmpty() ) {
            result = new NullGenerator();
          } else {
            result = new DecorateMethodGenerator(new CharDataGenerator(text),
                                                 lexemStart,
                                                 lexemEnd);
          }
          return result;
        }
      },
      new TextMode()),

  CLOSE_SCRIPTLET("%>",
    new GeneratorFactory() {
      public Generator construct(String text, Mark lexemStart, Mark lexemEnd) {
        return new DecorateMethodGenerator(new ScriptletGenerator(text),
                                           lexemStart,
                                           lexemEnd);
      }
    },
    new TextMode()),
  OPEN_SCRIPTLET("<%",
                 new GeneratorFactory() {
                   public Generator construct(String text, Mark lexemStart, Mark lexemEnd) {
                     Generator result;

                     if ( text.isEmpty() ) {
                       result = new NullGenerator();
                     } else {
                       result = new DecorateMethodGenerator(new CharDataGenerator(text),
                                                            lexemStart,
                                                            lexemEnd);
                     }
                     return result;
                   }
                 },
                 new CodeMode(CLOSE_SCRIPTLET)),

  CLOSE_EXPRESSION("%>",
    new GeneratorFactory() {
      public Generator construct(String text, Mark lexemStart, Mark lexemEnd) {
        return new DecorateMethodGenerator(new ExpressionGenerator(text),
                                           lexemStart,
                                           lexemEnd);
      }
    },
    new TextMode()),
  OPEN_EXPRESSION("<%=",
                  new GeneratorFactory() {
                    public Generator construct(String text, Mark lexemStart, Mark lexemEnd) {
                      Generator result;

                      if ( text.isEmpty() ) {
                        result = new NullGenerator();
                      } else {
                        result = new DecorateMethodGenerator(new CharDataGenerator(text),
                                                             lexemStart,
                                                             lexemEnd);
                      }
                      return result;
                    }
                  },
                  new CodeMode(CLOSE_EXPRESSION)),

  CLOSE_DIRECTIVE("%>",
    new GeneratorFactory() {
      public Generator construct(String text, Mark lexemStart, Mark lexemEnd) {
        return new DecorateDirectiveGenerator(new DirectiveGenerator(text),
                                              lexemStart,
                                              lexemEnd);
      }
    },
    new TextMode()),
  OPEN_DIRECTIVE("<%@",
                 new GeneratorFactory() {
                   public Generator construct(String text, Mark lexemStart, Mark lexemEnd) {
                     Generator result;

                     if ( text.isEmpty() ) {
                       result = new NullGenerator();
                     } else {
                       result = new DecorateMethodGenerator(new CharDataGenerator(text),
                                                            lexemStart,
                                                            lexemEnd);
                     }
                     return result;
                   }
                 },
                 new CodeMode(CLOSE_DIRECTIVE)),

  CLOSE_COMMENT("--%>",  // produce a comment
    new GeneratorFactory() {
      public Generator construct(String text, Mark lexemStart, Mark lexemEnd) {
        return new CommentGenerator(text);
      }
    },
    new TextMode()),
  OPEN_COMMENT("<%--",
               new GeneratorFactory() {
                 public Generator construct(String text, Mark lexemStart, Mark lexemEnd) {
                   Generator result;

                   if ( text.isEmpty() ) {
                     result = new NullGenerator();
                   } else {
                     result = new DecorateMethodGenerator(new CharDataGenerator(text),
                                                          lexemStart,
                                                          lexemEnd);
                   }
                   return result;
                 }
               },
               new CodeMode(CLOSE_COMMENT));

  /**
   * Reference name
   */
  public final String name;

  /**
   * Lexer mode, this lexem switches on, when read
   */
  public final Mode nextMode;

  /**
   * Factory to produce the element of the abstract syntax tree for
   * the just parsed text / lexem
   */
  public final GeneratorFactory generate;

  private Lexem(String name,
                GeneratorFactory generate,
                Mode nextMode) {
    this.name = name;
    this.nextMode = nextMode;
    this.generate = generate;
  }
}