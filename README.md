Build a distribution using Gradle:

$ gradle clean distZip
$ ls javafx/build/distributions
big-image-viewer-javafx-0.1-SNAPSHOT.zip

Unzip the artifact and run with any file supported by Bio-Formats:

$ cd javafx/build/distributions
$ unzip big-image-viewer-javafx-0.1-SNAPSHOT.zip
$ cd big-image-viewer-javafx-0.1-SNAPSHOT
$ bin/big-image-viewer-javafx /path/to/file

Only loads the first plane in the first series.  No contrast adjustment is done, so this is best for 8 bit brightfield data.

Intended for development debugging purposes only and not supported by OME.
