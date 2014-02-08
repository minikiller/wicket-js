/**
 *    Copyright 2014 Thomas Rausch
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.rauschig.wicketjs;

/**
 * JsStatement
 */
public class JsStatement implements IJsStatement {

    private static final long serialVersionUID = -5422270276826187017L;

    private CharSequence statement;

    public JsStatement(CharSequence statement) {
        this.statement = statement;
    }

    public CharSequence getStatement() {
        return statement;
    }

    public static IJsStatement of(IJavaScript javaScript) {
        if (javaScript instanceof IJsStatement) {
            return (IJsStatement) javaScript;
        } else if (javaScript instanceof IJsExpression) {
            return of((IJsExpression) javaScript);
        } else {
            return null;
        }
    }

    public static JsExpressionStatement of(IJsExpression expression) {
        return new JsExpressionStatement(expression);
    }

    @Override
    public void accept(IJsStatementVisitor visitor) {
        visitor.visit(this);
    }
}
