package jjbridge.api.runtime;

import jjbridge.api.inspector.JSInspector;
import jjbridge.api.value.JSObject;
import jjbridge.api.value.JSType;
import jjbridge.api.value.JSValue;

import java.util.HashSet;
import java.util.Set;

public abstract class JSBaseRuntime<R extends JSReference> implements JSRuntime
{
    private boolean closed;
    private final Set<JSInspector> attachedInspectors;
    private long scriptCounter;

    protected JSBaseRuntime()
    {
        this.closed = false;
        this.attachedInspectors = new HashSet<>();
        this.scriptCounter = 0;
    }

    protected boolean isClosed()
    {
        return this.closed;
    }

    public void close()
    {
        for (JSInspector attachedInspector : attachedInspectors)
        {
            attachedInspector.detach();
        }
        this.closed = true;
    }

    public final JSObject<?> globalObject()
    {
        if (this.isClosed())
        {
            throw new RuntimeException();
        }
        return this.getGlobalObject();
    }

    public final JSReference executeScript(String fileName, String script)
    {
        if (this.isClosed())
        {
            throw new RuntimeException();
        }
        return this.runScript(fileName, script);
    }

    public final JSReference executeScript(String script)
    {
        return executeScript("/script_" + (scriptCounter++), script);
    }

    public final JSReference newReference(JSType type)
    {
        if (this.isClosed())
        {
            throw new RuntimeException();
        }
        return this.createNewReference(type);
    }

    public final <T extends JSValue> T resolveReference(JSReference reference)
    {
        return this.resolveReference(reference, TypeResolution.Nominal);
    }

    @SuppressWarnings("unchecked")
    public final <T extends JSValue> T resolveReference(JSReference reference, TypeResolution typeResolution)
    {
        if (this.isClosed())
        {
            throw new RuntimeException();
        }

        switch (typeResolution)
        {
            case Nominal:
                return this.resolve((R) reference, reference.getNominalType());
            case Actual:
                return this.resolve((R) reference, reference.getActualType());
            default:
                throw new IllegalArgumentException("Invalid type resolution.");
        }
    }

    public void onInspectorAttached(JSInspector inspector)
    {
        if (!attachedInspectors.add(inspector))
        {
            throw new IllegalStateException("InspectorBase already attached");
        }
    }

    public void onInspectorDetached(JSInspector inspector)
    {
        attachedInspectors.remove(inspector);
    }

    protected abstract JSObject<R> getGlobalObject();

    protected abstract <T extends JSValue> T resolve(R reference, JSType asType);

    protected abstract R runScript(String filename, String script);

    protected abstract R createNewReference(JSType type);
}
