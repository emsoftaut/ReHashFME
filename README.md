# RehAsh
A Code translator that lets you convert Parametric Statecharts to Static Statecharts, for Statecharts used to design Rehabilitation plans

We used to [Yakindu Statecharts](https://github.com/Yakindu/statecharts) to design & display Statecharts

So, After parsing, <br> 
This

![alt tag](https://raw.githubusercontent.com/emsoftaut/ReHashFME/master/case-study/PSCboxed.png)

gets converted to this 

![alt tag](https://raw.githubusercontent.com/emsoftaut/ReHashFME/master/case-study/PSCNew.png)

## OR case

```
> cd or
> javac DomXml.java
> java DomXml
```
The code gets updated into "or/source files/<file name>.xml", which you can copy into any yakindu statechart sct file and it will generate the statechart automatically for you

## AND case


```
> cd and
> javac DomXml.java
> java DomXml
```
The code gets updated into "and/source files/<file name>.xml", which you can copy into any yakindu statechart sct file and it will generate the statechart automatically for you


### How to run both AND and OR parser on the Statechart xml file?

Run the AND parser first as it might generate more states
Run the OR parser next, on the same file

There are config variables defined on the top of both DomXml files
where you can re-define number of sensors and the event to listen to

## Rules for parsing
Copy the Statechart .sct XML code into a new or existing 'source files/<newfile.xml>' file
<br>
Check the code to see if the States & Transitions in the XML file are called in the order in which they occur in the actual design and the regions <regions xmi:id="<the_id>" name="main region"> containing the code to be translated should always be named as "main region"
For example:
Under regions, the Chart should always start with the 'sgraph:Entry' vertice;
<br>


/<vertices xsi:type="sgraph:Entry" xmi:id="<the_id>">
        <outgoingTransitions xmi:id="<the_id>" target="<target>"/>
 </vertices>

and

The Chart should always exit as 
```
<vertices xsi:type="sgraph:FinalState" xmi:id="<the_id>" incomingTransitions="<incoming_transition(s)"/>
```
<br>
First run the code through the AND parser under folder and using the java compiler as shown above
<br>
Remember to specify the "<file name>.xml" "<number of sensors>" and the "<start AND transition>" and "<following to AND transition>" in the and/DomXml.java. So it should look something like

```
  String filepath = "<file name>.xml";
	....
    	...
		// Number of sensors- 
		int numberSensors = "<number of sensors>";
		String eventName = "<start AND transition>";
		String laterEventName = "<following to AND transition>";
		// Get the root element
```
<br>
Take the resulted code from "<file name>.xml" and copy it into a new or existing XML file under or/source files/<file name>.xml
<br>
Run the or/DomXml.java parser on the above file
<br>
Remember to specify the "<file name>.xml" "<number of sensors>" and the "<OR transition name>" in the or/DomXml.java. So it should look something like
<br>
```
String filepath = "<file name>.xml";
	...
	...
	
	...
	int nosens = "<number of sensors>"; // Hard coding number of sensors.. tada
	String theEvent = "<number of sensors>";
```
<br>
Take the code from <file name>.xml and run it as project in [Yakindu](https://github.com/Yakindu/statecharts) to display the resultant Statechart
<br>
Thanks for your time! and Congratulations! you have succesfully translated a Parametric Statechart to Static Statechart. You must be Amazing!
<br>
