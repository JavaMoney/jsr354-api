JSR 354: Money and Currency API
=========
JSR 354 provides an API for representing, transporting, and performing comprehensive calculations with Money and Currency. 
See the home page for more details:
http://jcp.org/en/jsr/detail?id=354

The current projcet structure is as follows:

SE scope:
---------
* money-platform: contains the api/code targeting JDK SE 9.
  * api: contains the api targeting JDK SE 9.
  * ri: contains the reference implementation targeting JDK SE 9.

Standalone scope
----------------
* money-api: contains the standalone API part
  * convert: currency conversion
  * format: formatting and parsing
  * provider: accessors / SPI
  * ext: extensions
* money-impl: contains the standalone implementation artifacts
  * ri: contains the reference implementation, runnable on SE 7 or later
  * cdi: contains alternate loader implementation using standalone CDI (weld)
  * java-ee: contains alternate loader targeting EE 6 CDI container integration
* money-tck: contains the technical compatibility kit (TCK)

