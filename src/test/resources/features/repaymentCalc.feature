Feature: Verify Home Loan Repayment Calculator

@regression @homeLoan
Scenario Outline: Verify home loan repayment calculator with different data
  Given Load test data from csv <csv>
  And User navigate to Repayment Calculator page
  When User submit repayment inputs
  Then Validate repayment calculations <csv>
  Examples:
    | csv               |
    | RepaymentCalc.csv |
    | RepaymentCalc2.csv |
