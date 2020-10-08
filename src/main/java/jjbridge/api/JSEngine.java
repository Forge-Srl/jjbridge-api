package jjbridge.api;

import jjbridge.api.inspector.JSInspector;
import jjbridge.api.runtime.JSRuntime;

/**
 * This interface defines the access points to a JavaScript engine.
 * <p>A JavaScript engine has two components:</p>
 * <ul>
 *     <li>A runtime ({@link JSRuntime}) which executes JavaScript code.</li>
 *     <li>An inspector ({@link JSInspector}) which allows the developer to debug the code executed by the runtime.</li>
 * </ul>
 * */
public interface JSEngine
{
    /**
     * This method provides a new runtime instance to run JavaScript code.
     * <p>
     *     Multiple invocations of this method are expected to return different instances of the runtime. Each runtime
     *     instance is isolated, meaning that <strong>data and code inside a runtime instance is not shared with other
     *     instances</strong>.
     * </p>
     * <p>The suggested usage of this method is the following:</p>
     * <pre>{@code
     * try (JSRuntime runtime = engine.newRuntime()) {
     *     // use `runtime` methods here
     * } catch (RuntimeException e) {
     *     // handle errors here
     * }
     * }</pre>
     *
     * @return a new {@link JSRuntime} instance.*/
    JSRuntime newRuntime();

    /**
     * This method provides a new inspector instance to debug {@link JSRuntime}.
     * <p>
     *     Multiple invocations of this method are expected to return different instances of the inspector.
     * </p>
     *
     * @param port the IP port to which the inspector client will connect to interact with the inspector.
     * @return a new {@link JSInspector} instance.*/
    JSInspector newInspector(int port);
}
