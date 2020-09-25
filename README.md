# AutomationTasks for CBA Interview

This repository of tests given scenarios
1. Parse sampleoutput.txt and generates a csv file for each transaction
2. Opens CBA repayment calculator web using selenium and java, fills in the form data from test data in csv format, and validates the output

System Setup
============
Ensure Java & Maven latest is availabe on target machine
Chrome web browser v85.0 available
Clone the following repository:
 * git clone https://github.com/kalivallepu/AutomationTasksCBA

Execution:
==========
Open command line and go to the project root directory,run the following command: 

```mvn clean test```

Author
======

```kali.vallepu@gmail.com```