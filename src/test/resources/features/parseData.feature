Feature: Parse Data from SampleOutput.txt

@regression @Parser
Scenario: Parse and Save the data from SampleOutput.txt
Given File Exists SampleOutput.txt
Then Parse and Generate CSV SampleOutput.txt
