# Python serialization (pickle)

Serialize an object and save it to a file:
```
python save.py
```

Read the file and deserialize it:

```
python read.py
```

Print the file - it looks like text data:

```
cat object.ser
```

In `save.py`, uncomment the line with `bytearray` and serialize again

```
python save.py
```

Look at text and hex:

```
cat object.ser
xxd -g 1 object.ser
```

Can you see the byte array's data?

