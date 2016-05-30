# RehAsh
Ash's work on Rehabilitation Plans

## OR case

```
> cd or
> javac DomXml.java
> java DomXml
```
The code gets updated into file1.xml, which you can copy into any yakindu statechart sct file and it will generate the statechart automatically for you

## AND case


```
> cd and
> javac DomXml.java
> java DomXml
```
The code gets updated into file.xml, which you can copy into any yakindu statechart sct file and it will generate the statechart automatically for you

How to run both AND and OR parser on the Statechart xml file?

Run the AND parser first as it might generate more states
Run the OR parser next, on the same file

There are config variables defined on the top of both DomXml files
where you can re-define number of sensors and the event to listen to
