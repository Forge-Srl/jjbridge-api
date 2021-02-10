package jjbridge.api.inspector;

import jjbridge.api.runtime.JSRuntime;

/**
 * This interface expose the methods to allow an inspector to attach/detach to a runtime in order to debug its
 * execution. An inspector instance can attach to multiple runtime instances, but only one at a time.
 * <p>Instances of this interface should conform to the <strong>Chrome DevTools Protocol</strong> for debugging
 * JavaScript runtimes.</p>
 *
 * <p><strong>Implementers should extend {@link JSBaseInspector} class instead of implementing this interface.</strong>
 * </p>
 *
 * @see <a href="https://chromedevtools.github.io/devtools-protocol/">Chrome DevTools specification</a>
 * */
public interface JSInspector
{
    /**
     * Use this method to attach the inspector to a runtime.
     *
     * @param runtime the runtime to which the inspector will attach
     * */
    void attach(JSRuntime runtime);

    /**
     * Use this method to detach from the runtime a currently attached inspector instance.
     * */
    void detach();
}
