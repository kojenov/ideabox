# Serializable Java class

Compile and execute BasicSerialize:
```
javac BasicSerialize.java
java BasicSerialize
```

Use _file_ utility to check the created file:
```
file object.ser
```

Take a look at it in hex and notice the pattern "ac ed 00 05":
```
xxd -g 1 object.ser
```

See the file in Base64 and notice the pattern "rO0AB":
```
base64 object.ser
```

Download [jdeserialize](https://code.google.com/archive/p/jdeserialize/downloads) and use it to analyze the serialized data:
```
java -jar jdeserialize-1.2.jar object.ser
```

