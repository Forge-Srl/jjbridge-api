package jjbridge.api.runtime;

import jjbridge.api.inspector.JSInspector;
import jjbridge.api.value.JSArray;
import jjbridge.api.value.JSBoolean;
import jjbridge.api.value.JSDate;
import jjbridge.api.value.JSDouble;
import jjbridge.api.value.JSExternal;
import jjbridge.api.value.JSFunction;
import jjbridge.api.value.JSInteger;
import jjbridge.api.value.JSNull;
import jjbridge.api.value.JSObject;
import jjbridge.api.value.JSString;
import jjbridge.api.value.JSType;
import jjbridge.api.value.JSUndefined;
import jjbridge.api.value.JSValue;

/**
 * A runtime to execute JavaScript code.
 * <p>
 *     Use this class to run JavaScript scripts and access JavaScript objects created inside the engine.
 * </p>
 *
 * <p><strong>Implementers should extend {@link JSBaseRuntime} class instead of implementing this interface.</strong>
 * </p>
 * */
public interface JSRuntime extends AutoCloseable
{
    /**
     * Access the global object of the JavaScript runtime.
     * <p>The global object contains fields and functions which can be globally invoked inside the runtime. Notable
     * examples are the {@code setTimeout()} and {@code clearTimeout()} functions or the {@code window} and
     * {@code console} variables available when running JavaScript code in a browser.</p>
     * <p><strong>The global object of a newly created runtime is expected to not contain any predefined field or
     * function.</strong></p>
     *
     * @return the global object of the runtime.
     * */
    JSObject<?> globalObject();

    /**
     * Runs some JavaScript code inside the runtime. A debug name for the script will be automatically generated.
     * See {@link #executeScript(String, String)} for more info.
     *
     * @param script the actual code to run.
     * @return a reference to the last value computed by the script.
     * @throws CompilationException when the script contains syntax errors.
     * @throws ExecutionException when a JavaScript error is thrown while running the script. The stack trace of this
     *     exception should be a combination of both JavaScript stack trace and Java stack trace (if a Java method is
     *     indirectly called during the execution of the script).
     * */
    JSReference executeScript(String script);

    /**
     * Runs some JavaScript code inside the runtime.
     * <p>The file name will be shown in stack traces and in the inspector client.</p>
     * <p>If the script consists of a simple JavaScript expression this method will return the reference to that value.
     * If the script consists of several statements, this method will return the reference to the value of the last
     * statement.</p>
     * <table>
     *     <caption>Examples of scripts with the referenced value that this method would return</caption>
     *     <tr>
     *         <td>JavaScript code</td><td>Referenced value</td>
     *     </tr>
     *     <tr>
     *         <td>{@code null}</td><td>{@code null}</td>
     *     </tr>
     *     <tr>
     *         <td>{@code 1 + 5}</td><td>{@code 6}</td>
     *     </tr>
     *     <tr>
     *         <td>{@code "some" + " text"}</td><td>{@code "some text"}</td>
     *     </tr>
     *     <tr>
     *         <td>{@code (x, y) => x + "@" + y}</td><td>Function</td>
     *     </tr>
     *     <tr>
     *         <td><pre>{@code
     * ({
     *     key: {
     *         inner: "value"
     *     },
     *     other: true
     * })}</pre></td><td>Object</td>
     *     </tr>
     *     <tr>
     *         <td><pre>{@code
     * let x = 420;
     * x
     * }</pre></td><td>{@code 420}</td>
     *     </tr>
     *     <tr>
     *         <td><pre>{@code
     * function lg(obj) {
     *     console.log(obj)
     * };
     * lg("some text")
     * }</pre></td><td>{@code undefined}</td>
     *     </tr>
     * </table>
     *
     * @param fileName the file name associated to the script used for debug.
     * @param script the actual code to run.
     * @return a reference to the last value computed by the script.
     * @throws CompilationException when the script contains syntax errors.
     * @throws ExecutionException when a JavaScript error is thrown while running the script. The stack trace of this
     *     exception should be a combination of both JavaScript stack trace and Java stack trace (if a Java method is
     *     indirectly called during the execution of the script).
     * */
    JSReference executeScript(String fileName, String script);

    /**
     * Creates a new reference of the given JavaScript type. The reference points to a default value according to the
     * given type.
     * <p>The created reference resides in the JavaScript engine memory but it is not automatically accessible from
     * JavaScript code. To access the value from the JavaScript code you must pass the reference as parameter in a
     * JavaScript function ({@link JSFunction}) or set it as a property of a JavaScript object ({@link JSObject}).</p>
     *
     * @param type the JavaScript type
     * @return the JavaScript reference to the newly created value
     * */
    JSReference newReference(JSType type);

    /**
     * Returns the JavaScript value pointed by the reference. The resolution type will be
     * {@link TypeResolution#Nominal}. See {@link #resolveReference(JSReference, TypeResolution)} for more info.
     *
     * @param reference the reference to resolve.
     * @param <T> the type of the returned value.
     * @return the value pointed by the reference.
     * */
    <T extends JSValue> T resolveReference(JSReference reference);

    /**
     * Returns the JavaScript value pointed by the reference.
     * <p>Reference resolution can be:</p>
     * <ul>
     *     <li>{@link TypeResolution#Nominal}: will return a value of the JavaScript type given by
     *     {@link JSReference#getNominalType()}. When the reference has been created via {@link #newReference(JSType)},
     *     that type parameter should be the nominal type.</li>
     *     <li>{@link TypeResolution#Actual}: will return a value of the JavaScript type given by
     *     {@link JSReference#getActualType()}. Since JavaScript is dynamically typed, a reference can, for example,
     *     first point to a number, then point to a String, then point to a Function, etc. The actual type of a
     *     reference should be the type of the last value pointed by the reference.</li>
     * </ul>
     * <p>Once the type resolution strategy has determined the type of the reference, the reference is resolved creating
     * a value of the corresponding type according to the following list:</p>
     * <table>
     *     <caption>Reference type resolutions</caption>
     *     <tr><td>{@link JSType}</td><td>Result type</td></tr>
     *     <tr><td>{@link JSType#Undefined}</td><td>{@link JSUndefined}</td></tr>
     *     <tr><td>{@link JSType#Null}</td><td>{@link JSNull}</td></tr>
     *     <tr><td>{@link JSType#Boolean}</td><td>{@link JSBoolean}</td></tr>
     *     <tr><td>{@link JSType#Integer}</td><td>{@link JSInteger}</td></tr>
     *     <tr><td>{@link JSType#Double}</td><td>{@link JSDouble}</td></tr>
     *     <tr><td>{@link JSType#String}</td><td>{@link JSString}</td></tr>
     *     <tr><td>{@link JSType#Date}</td><td>{@link JSDate}</td></tr>
     *     <tr><td>{@link JSType#Object}</td><td>{@link JSObject}</td></tr>
     *     <tr><td>{@link JSType#Function}</td><td>{@link JSFunction}</td></tr>
     *     <tr><td>{@link JSType#Array}</td><td>{@link JSArray}</td></tr>
     *     <tr><td>{@link JSType#External}</td><td>{@link JSExternal}</td></tr>
     * </table>
     *
     * @param reference the reference to resolve.
     * @param typeResolution the type resolution strategy used to choose the resulting type.
     * @param <T> the type of the returned value. The type will be determined using the conversion table above. Using
     *           the wrong type can cause an exception to be thrown.
     * @return the value pointed by the reference.
     * */
    <T extends JSValue> T resolveReference(JSReference reference, TypeResolution typeResolution);

    /**
     * This method is called to notify a runtime that an inspector is attaching to it.
     * <p>Any {@link JSInspector} instance should call this method in {@link JSInspector#attach(JSRuntime)}.</p>
     *
     * @param inspector the inspector that is attaching.
     * */
    void onInspectorAttached(JSInspector inspector);

    /**
     * This method is called to notify a runtime that an inspector is detaching from it.
     * <p>Any {@link JSInspector} instance should call this method in {@link JSInspector#detach()}.</p>
     *
     * @param inspector the inspector that is detaching.
     * */
    void onInspectorDetached(JSInspector inspector);
}
