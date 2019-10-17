package jjbridge.common.value;

import jjbridge.common.runtime.JSReference;
import jjbridge.common.value.strategy.ArrayDataGetter;
import jjbridge.common.value.strategy.ArrayDataSetter;
import jjbridge.common.value.strategy.ObjectPropertyGetter;
import jjbridge.common.value.strategy.ObjectPropertySetter;

public class JSArray<R extends JSReference> extends JSObject<R> {
    private ArrayDataGetter<R> arrayDataGetter;
    private ArrayDataSetter<R> arrayDataSetter;

    public JSArray(ObjectPropertyGetter<R> propertyGetter, ObjectPropertySetter<R> propertySetter,
                   ArrayDataGetter<R> arrayDataGetter, ArrayDataSetter<R> arrayDataSetter) {
        super(propertyGetter, propertySetter);
        this.arrayDataGetter = arrayDataGetter;
        this.arrayDataSetter = arrayDataSetter;
    }

    public int size() {
        return this.arrayDataGetter.getSize();
    }

    public JSReference get(int position) {
        return this.arrayDataGetter.getItemByPosition(position);
    }

    @SuppressWarnings("unchecked")
    public void set(int position, JSReference value) {
        this.arrayDataSetter.setItemByPosition(position, (R) value);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof JSArray)) return false;
        JSArray other = (JSArray) obj;
        return super.equals(other)
                && this.arrayDataGetter.equals(other.arrayDataGetter)
                && this.arrayDataSetter.equals(other.arrayDataSetter);
    }
}
