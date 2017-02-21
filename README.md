# Field Filter for Couchbase Kafka Connector

This repository contains example of filtering class for [Couchbase Kafka Connector](github.com/couchbase/kafka-connect-couchbase).

## Quickstart

Clone this repository:

    $ git clone git://github.com/couchbaselabs/kafka-example-filter.git
    $ cd kafka-example-filter
    
Build the JAR file:

    $ mvn jar:jar
    ...
    [INFO] Building jar: /home/avsej/tmp/fieldfilter/target/field-filter-1.0-SNAPSHOT.jar
    ...
    
Put the jar into connector classpath. For example, if you have connector installed in 
`/usr/share/java/kafka-connect-couchbase/`, the easiest way to use this filter would be
to copy it into the same directory.

    $ sudo cp target/field-filter-1.0-SNAPSHOT.jar /usr/share/java/kafka-connect-couchbase/

Next step is to configure connector to use this filter instead of default
`com.couchbase.connect.kafka.filter.AllPassFilter`. To do so, you have to define following
property in connector configuration:

    event.filter.class=example.FieldFilter
    
## Implementation

The implementation of `example.FieldFilter` is simple. It takes all mutation messages
the Couchbase Server sends to connector, tries to parse message body as JSON. If the body
of the document contains string property `type` and it equals to `"airline"`, the mutation
will be forwarded to the topic, assigned to the connector. Note that for simplicity we
hardcoded `"airline"` in the class text, but it could be supplied from the different source
as well, like system properties or another Couchbase document.

# Contribute

- Documentation: http://developer.couchbase.com/documentation/server/current/connectors/kafka-3.1/kafka-intro.html
- Source Code: https://github.com/couchbase/kafka-connect-couchbase
- Issue Tracker: https://issues.couchbase.com/projects/KAFKAC
- Downloads & Release Notes: http://developer.couchbase.com/documentation/server/current/connectors/kafka-3.1/release-notes.html

# License

The project is licensed under the Apache 2 license.
