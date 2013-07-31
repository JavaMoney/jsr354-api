JSR 354: Money and Currency API
=========
JSR 354 provides an API for representing, transporting, and performing comprehensive calculations with Money and Currency. 
See the home page for more details:
http://jcp.org/en/jsr/detail?id=354

The current project structure is as follows:

---------
* money-api: contains the main API/Spec
  * convert: currency conversion
  * ext: extensions
  * format: formatting and parsing
  * platform: contains core platform module targeting Java SE 7 and above or ME/CLDC where applicable.
* money-impl: contains the main implementation artifacts
  * ri: contains the reference implementation (RI), runnable on SE 7 or later
  * cdi: contains alternate loader implementation using stand-alone CDI (weld)
  * java-ee: contains alternate loader targeting EE 6 and above CDI container integration
* money-tck: contains the technical compatibility kit (TCK)
