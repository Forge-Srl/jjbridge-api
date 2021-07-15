# JJBridge Api

[![javadoc](https://javadoc.io/badge2/srl.forge/jjbridge-api/javadoc.svg)](https://javadoc.io/doc/srl.forge/jjbridge-api)

![Build JJBridge Api](https://github.com/Forge-Srl/jjbridge-api/workflows/Build%20JJBridge%20Api/badge.svg?branch=main)

JJBridge is a multi-library project which brings JavaScript execution capabilities to Java.

JJBridge Api defines a standard set of interface for accessing a JavaScript engine from Java.

## Contents

- [Installation](#installation)
- [Usage](#usage)
  - [Quickstart](#quickstart)
  - [How it works?](#how-it-works)
  - [Interacting with the runtime](#interacting-with-the-runtime)
    - [Primitive types](#primitive-types)
    - [Objects](#objects)
    - [Arrays](#arrays)
    - [Functions](#functions)
    - [Externals](#externals)
    - [Scripts and other things](#scripts-and-other-things)
  - [Debugging with the inspector](#debugging-with-the-inspector)
- [License](#license)

## Installation

Add this to your pom.xml:
```xml
<dependency>
  <groupId>srl.forge</groupId>
  <artifactId>jjbridge-api</artifactId>
</dependency>
```

**In order to actually execute JavaScript you should also add a JJBridge Engine library.**

Here is a list of the engines currently available for JJBridge:
- [JJBridge V8 Engine](https://github.com/Forge-Srl/jjbridge-engine-v8) (Available for Linux, Windows, macOS and Android)

## Usage
The full javadoc is available at <https://www.javadoc.io/doc/srl.forge/jjbridge-api/latest/index.html>.

### Quickstart
All JavaScript code must run inside a `JSRuntime` instance. Here is an example:
```java
class Example {
    private static JSEngine engine = new V8Engine(); // or other available engine
    
    public static void main(String[] args) {
        try (JSRuntime runtime = engine.newRuntime()) {
            // Do JavaScript things here...
            String script =
                "const foo = '25'\n" +
                "const bar = 33\n" +
                "const mult = (a, b) => `${a} x ${b} = ${a * b}`\n" +
                "mult(foo, bar)";
    
            JSReference resultJSRef = runtime.executeScript(script);
            JSString resultJSString = runtime.resolveReference(resultJSRef);
            String result = resultJSString.getValue();
            System.out.println(result); // Prints "25 x 33 = 825"
        } catch (RuntimeException e) {
            // handle errors here
        }
    }
}
```

### How it works?
A `JSRuntime` is an instance of a JavaScript engine context. It is the single point from which you can execute scripts 
and access JavaScript objects.

All interactions between JavaScript and Java exploit instances of `JSReference`. A `JSReference` is a lightweight 
pointer to a JavaScript object which can be passed in and out of the runtime when needed. References are created as a 
result of a script execution, as a result of a JavaScript function invocation, accessing a JavaScript object field or
even directly from the runtime using the `newReference(JSType type)` method.

If you need to access the actual value pointed by a reference, you must resolve it using the 
`resolveReference(JSReference reference)` method of the runtime. When resolving a reference, you must specify the 
expected `JSValue` sub-type. `JSValue` sub-types are the following:
- `JSUndefined`: maps JavaScript `undefined`.
- `JSNull`: maps JavaScript `null`.
- `JSBoolean`: maps a JavaScript boolean; allows get/set of the value.
- `JSInteger`: maps a JavaScript integer to a Java long; allows get/set of the value.
- `JSFloat`: maps a JavaScript number to a Java double; allows get/set of the value.
- `JSString`: maps a JavaScript string; allows get/set of the value.
- `JSObject`: maps a JavaScript object; allows get/set of properties/methods.
- `JSDate`: maps a JavaScript date; allows get/set of the value and properties/methods.
- `JSArray`: maps a JavaScript array; allows get/set of properties/methods and elements.
- `JSFunction`: maps a JavaScript function; allows get/set of properties/methods, invocation (both as normal function
  and as constructor) and change of the actual code to execute.
- `JSExternal`: this is a special type which allows the runtime to store references to Java objects in JavaScript
  objects.
  
**Using the wrong type may throw an exception.**

### Interacting with the runtime
#### Primitive types
Working with primitive types (booleans, numbers, strings) is pretty straightforward. You can only get and set the value.
*Dates are objects (because you can access fields and methods), but they also allow getting and setting the value just 
like primitive types.*

Here is an example with booleans:
```java
JSReference ref = runtime.newReference(JSType.Boolean);
JSBoolean jsBool = runtime.resolveReference(ref);

jsBool.setValue(true);
Boolean b = jsBool.getValue(); // b is true

jsBool.setValue(false);
b = jsBool.getValue(); // b is false
```

And now an example with integers:
```java
JSReference ref = runtime.newReference(JSType.Integer);
JSInteger jsInt = runtime.resolveReference(ref);

jsInt.setValue(420);
Integer i = jsInt.getValue(); // i is 420

jsInt.setValue(-10000009);
i = jsInt.getValue(); // i is -10000009
```

#### Objects
Working with objects is all about getting and setting properties. Properties can be fields of any type but also methods 
(which are nothing but functions).

Here is an example:
```java
JSReference ref = runtime.newReference(JSType.Object);
JSObject<?> jsObj = runtime.resolveReference(ref);

JSReference field1ref = runtime.newReference(JSType.String);
((JSString) runtime.resolveReference(field1ref)).setValue("foo bar baz");
jsObj.set("field1", field1ref);
// now jsObj is {field1: "foo bar baz"}

jsObj.set("field2", runtime.newReference(JSType.Null));
// now jsObj is {field1: "foo bar baz", field2: null}

jsObj.set("field3", runtime.executeScript("({inner: 8})"));
// now jsObj is {field1: "foo bar baz", field2: null, field3: {inner: 8}}

JSReference field3ref = jsObj.get("field3");
JSObject<?> field3 = runtime.resolveReference(field3ref);
JSInteger inner = runtime.resolveReference(field3.get("inner"));
Integer i = inner.getValue() // i is 8
```

#### Arrays
Arrays are much like objects, but instead of accessing a property by name, you can access an item by index. *Array are 
also objects, which means you can access all array properties.*

Here is an example:
```java
JSReference ref = runtime.newReference(JSType.Array);
JSArray<?> jsArray = runtime.resolveReference(ref);
int size = jsArray.size(); // size is 0

jsArray.set(0, runtime.newReference(JSType.Null));
jsArray.set(1, runtime.newReference(JSType.Null));
jsArray.set(2, runtime.executeScript("11**4"));
size = jsArray.size(); // size is 3

JSInteger inner = runtime.resolveReference(jsArray.get(2));
Integer i = inner.getValue() // i is 14641
```

#### Functions
You can get a function either from a script or accessing an object property. Depending on the function, you can call it
normally or as a constructor. *Functions are also objects, which means you can access all function properties.*

Here is an example:
```java
JSReference ref = runtime.executeScript("(a, b) => a + b");
JSFunction<?> jsFunction = runtime.resolveReference(ref);

JSReference stringRef = runtime.newReference(JSType.String);
((JSString) runtime.resolveReference(stringRef)).setValue("Toc");
JSReference resultRef = jsFunction.invoke(ref, stringRef, stringRef);
String resultString = ((JSString) runtime.resolveReference(resultRef)).getValue(); // resultString is TocToc

JSReference intRef = runtime.newReference(JSType.Integer);
((JSInteger) runtime.resolveReference(intRef)).setValue(12);
resultRef = jsFunction.invoke(ref, intRef, intRef);
Integer resultInt = ((JSInteger) runtime.resolveReference(resultRef)).getValue(); // resultInt is 24
```

You can also create a JavaScript function which will execute Java code when invoked:
```java
JSReference ref = runtime.newReference(JSType.Function);
JSFunction<?> jsFunction = runtime.resolveReference(ref);

jsFunction.setFunction((JSReference... args) -> {
    String arg0 = ((JSString) runtime.resolveReference(args[0])).getValue();
    String arg1 = ((JSString) runtime.resolveReference(args[1])).getValue();

    String result = someObject.someFunction(arg0, arg1); // Do something with the args

    JSReference resultRef = runtime.newReference(JSType.String);
    ((JSString) runtime.resolveReference(resultRef)).setValue(result);
    return resultRef; // TIP: if the function is supposed to be void then just return undefined.
});
```

#### Externals
The external type is a special type useful to store native Java data inside a JavaScript object for later retrieval.
From a JavaScript point of view, external values are indistinguishable from objects, but from a Java point of view they 
store additional data.

The usage is pretty much like a primitive type:
```java
JSReference ref = runtime.newReference(JSType.External);
JSExternal<HashMap<String, int[]>> jsExternal = runtime.resolveReference(ref);

HashMap<String, int[]> hashMap = new HashMap<>();
hashMap.put("asd", new int[] {1, 2});
jsExternal.setValue(hashMap);
hashMap = null;
// A reference to the hash map is still present inside the external object, thus we can retrieve it
hashMap = jsExternal.getValue();
int[] array = hashMap.get("asd"); // array is [1, 2]
```

#### Scripts and other things
As you have seen from the examples above, to run a script you just need to use `runtime.executeScript(script)` passing 
the JavaScript code as a `String`. There is a useful variant of this method which also allows to assign a file name to 
the script; this is useful if you have to debug many scripts in the inspector!

The result of a script execution is the last expression of the script. For example if the script is:
```js
const defaultName = 'world'
const greet = function(name = defaultName) {
    return {greeting: `Hello ${name}!`}
}
greet('Alice')
```
The result will be the value of the last expression `greet('Alice')` which is the resulting object 
`{greeting: "Hello Alice!"}`.

**When you run multiple scripts keep in mind they are isolated, which means a variable defined in one script is not 
available in a different script.** If you want to share values between scripts you should expose them as references 
which will be passed from Java.

The only exception are the **global variables** of the runtime. A global variable is just a property of the global 
object which is made accessible to the scripts as a predefined variable. To get the global object use 
`runtime.globalObject()`, then add each variable as a property to this object. Remember to define your global variables 
before running a script which use them!

Here is an example:
```java
JSReference globRef = runtime.newReference(JSType.Integer);
((JSInteger) runtime.resolveReference(globRef)).setValue(21);
runtime.globalObject().set("myGlobVar", globRef);

JSReference resultRef = runtime.executeScript("27 + (myGlobVar * 2)");
Integer result = ((JSInteger) runtime.resolveReference(resultRef)).getValue(); // result is 69
```

It is also possible to use different runtime instances at once. **Runtime instances are completely isolated** which 
means they don't share memory (even global variables are different). **Never pass/resolve a reference coming from a 
runtime instance with a different one!**; this could lead to an undefined behaviour, most likely an exception to be 
thrown.

### Debugging with the inspector
JJBridge allows you to debug JavaScript code via the Chrome DevTools Inspector protocol.
To enable debugging via the inspector you have to follow these steps:
1. Create an inspector and attach it to a runtime:
   ```java
   // ...
   JSInspector inspector = engine.newInspector(9090); // use a free port on your machine
   try (JSRuntime runtime = engine.newRuntime()) {
       inspector.attach(runtime);
       
       // Do JavaScript things here...
   }
   // ...
   ```
2. Run the app.
3. Connect to the inspector at `chrome-devtools://devtools/bundled/inspector.html?v8only=true&ws=IP:PORT` where:
   - `IP` is the IP address of the machine running the code;
   - `PORT` is the port passed to the inspector (*9090* in the example up here).
4. Debug :tada:

**Before releasing your app it is recommended to remove all inspector-related code to avoid undesired access to the 
JavaScript code shipped with it.**

## License

See the [LICENSE](LICENSE.md) file for license rights and limitations (MIT).
