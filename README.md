[![Stories in Ready](https://badge.waffle.io/Chris19951225/COS-301-Longsword-Data-Streaming.svg?label=ready&title=Ready)](http://waffle.io/Chris19951225/COS-301-Longsword-Data-Streaming)

# COS-301-Longsword-Data-Streaming
Data Streaming module of the NavUP system 
# Members
Peter Boxall - <a href="https://www.linkedin.com/in/peter-boxall-7096ba141/" target="_blank"> Linkedin</a>


Claude Greeff - <a href="https://www.linkedin.com/in/claude-greeff/" target="_blank"> Linkedin</a>


Hristian Vitrychenko - <a href="https://github.com/Chris19951225" target="_blank">Github</a>


Ritesh Doolabh - <a href="https://www.linkedin.com/in/ritesh-doolabh-149b9813a/" target="_blank"> Linkedin</a>



Lucian Sargeant - <a href="https://www.linkedin.com/in/lucian-sargeant-8b2268132/" target="_blank"> Linkedin</a>


Harris Leshaba - <a href="https://www.linkedin.com/in/harris-leshaba-597a33141/" target="_blank"> Linkedin</a>
# Scope
The data module concerns itself with two primary agents: the system server and the
generic user access channel which can be 1 of 3 possibilities who all behave in the
same way/support the same behaviour. The data module concerns itself primarily
with the act of moving information between the two entities. The exact format of this
and the exact specifics are not contained in this; rather, the messages and their
formats and contents must be independent of the actual carriage medium.


![alt tag](https://s2.postimg.org/9n976t2vt/Class_Diagram.png)
# Technologies
There are few fully open source data streaming technologies available. However, Apache
Flink is a fully open source stream processing framework with a full DataStreaming API.
Furthermore, Apache Flink can support a variety of origin languages for programs like
Java,Python and can optimise the stream transfers. It also provides a high-throughput, lowlatency
streaming engine as well as support for event-time processing and state
management. Flink applications are fault-tolerant in the event of machine failure and support
exactly-once semantics. It does not provide its own data store but comes with capacity to
extend connectors into a variety of data stores.
