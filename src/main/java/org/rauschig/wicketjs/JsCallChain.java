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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A call chain that concatenates multiple expressions with the member operator '<code>.</code>'. It provides several
 * different methods for chaining calls.
 * <p>
 * <table>
 * <tr>
 * <th><strong>Java</strong></th>
 * <th><strong>Compiled JS</strong></th>
 * </tr>
 * <tr>
 * <td><code>new JsCallChain("obj").chain("member")</code></td>
 * <td><code>obj.member</code></td>
 * </tr>
 * <tr>
 * <td><code>new JsCallChain("obj").chain("member").call("fun")</code></td>
 * <td><code>obj.member.fun()</code></td>
 * </tr>
 * <tr>
 * <td><code>new JsCallChain("console").call("log", "logging", JsExpression.THIS);</code></td>
 * <td><code>console.log('logging', this)</code></td>
 * </tr>
 * </table>
 * </p>
 */
public class JsCallChain extends AbstractJsExpression {

    private static final long serialVersionUID = -5447236481139701035L;

    private List<IJsExpression> expressions;

    public JsCallChain() {
        this(new ArrayList<IJsExpression>());
    }

    public JsCallChain(IJsExpression expression) {
        this();
        chain(expression);
    }

    public JsCallChain(IJsExpression... expressions) {
        this(new ArrayList<>(Arrays.asList(expressions)));
    }

    protected JsCallChain(List<IJsExpression> expressions) {
        this.expressions = expressions;
    }

    /**
     * Alias of {@link #chain(IJsExpression)}.
     * 
     * @see #chain(IJsExpression)
     */
    public JsCallChain _(IJsExpression expression) {
        return chain(expression);
    }

    /**
     * Alias of {@link #chain(String)}.
     * 
     * @see #chain(String)
     */
    public JsCallChain _(String identifier) {
        return chain(identifier);
    }

    /**
     * Alias of {@link #call(String, Object...)}.
     * 
     * @see #call(String, Object...)
     */
    public JsCallChain _(String functionName, Object... arguments) {
        return call(functionName, arguments);
    }

    /**
     * Chains the given IJsExpression to this call chain.
     * 
     * @param expression the expression to chain
     * @return this for chaining
     */
    public JsCallChain chain(IJsExpression expression) {
        expressions.add(expression);
        return this;
    }

    /**
     * Chains the given IJsExpression objects to this call chain.
     * 
     * @param expressions the expressions to chain
     * @return this for chaining
     */
    public JsCallChain chain(IJsExpression... expressions) {
        this.expressions.addAll(Arrays.asList(expressions));
        return this;
    }

    /**
     * Wraps the given String into a JsIdentifier and chains it to this call chain.
     * 
     * @param identifier the string representation of the identifier
     * @return this for chaining
     */
    public JsCallChain chain(String identifier) {
        return chain(new JsIdentifier(identifier));
    }

    /**
     * Interprets the given String as a function identifier and uses it to create a new JsCall with no arguments and
     * chains it to this call chain.
     * 
     * @param functionName the function to call
     * @return this for chaining
     * 
     * @see JsCall#JsCall(String)
     */
    public JsCallChain call(String functionName) {
        return chain(new JsCall(functionName));
    }

    /**
     * Uses the arguments to create a new JsCall and chains it to this call chain.
     * 
     * @param functionName the function to call
     * @param arguments the function arguments
     * @return this for chaining
     * 
     * @see JsCall#JsCall(String, Object...)
     */
    public JsCallChain call(String functionName, Object... arguments) {
        return chain(new JsCall(functionName, arguments));
    }

    public List<IJsExpression> getExpressions() {
        return expressions;
    }

    @Override
    public void accept(IJsExpressionVisitor visitor) {
        visitor.accept(this);
    }

}
