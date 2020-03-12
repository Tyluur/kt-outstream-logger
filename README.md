# kt-outstream-logger
This logger provides information when writing to the standard out print stream.

#### Installation
```System.setOut(OutLogger(stream = System.out))```

Prior to any `System.out` usage

#### Example
Console output from a method call of `System.out.println("Example usage")`:

```[LoggerTest:9#main][03.12.2020 01:21:48.244]  Example usage```

Enjoy