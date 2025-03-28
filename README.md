# Howto-Consolidators

## Introduction
This application is designed to showcase how to create your own consolidators for various use cases. It focuses solely on consolidators and demonstrates their features through three examples.
The calculated values produced by the consolidators are presented on the frontend, with each example having their own page.

After bootstrapping this application, the back end will load 25 trades and 5 instruments in the database. This will populate the tables used in a view that is the input for the consolidators.
The initial data can be viewed on their page or querying the dataserver.

More trades can be added through the home page to see the consolidators in effect.

To view the main documentation of Consolidators [click here](https://docs.genesis.global/docs/develop/server-capabilities/real-time-aggregation-consolidator/)

## Tests
Tests for each example can be found in the test directory of the howto-consolidators-app module.

Tests are created to make sure the consolidators are functioning as expected. Further details on what is being tested is written in a TDD approach with Given..., When..., Then... for clarity

They are located in the howto-consolidators-app/src/main/test/kotlin/global/genesis/.
You can run them by pressing on 'run tests...' in the right-click context menu of howto-consolidators-app.

## Examples

### Example 1 - Summing and Counting
This example demonstrates summing the quantities, and counting the buy and sell trades that have been verified for each individual instrument.

- **Consolidator**: `CONSOLIDATOR_SUM_COUNT_SUMMARY` in [howto-consolidators-example1-consolidator.kts](server/howto-consolidators-app/src/main/genesis/scripts/howto-consolidators-example1-consolidator.kts)
- **Tables Involved**:
  - `TRADE` (input)
  - `COUNT_SUM_SUMMARY` (output)
- **Dataservers for Consolidated Results**: `ALL_SUMMARY`
- **UI Page**: `Example_1`

#### Features Shown:
- `Sum`: Aggregates the total for quantity and price.
- `Count`: Aggregates the number of trades.
- `PivotBy`: Helps sum and count to update the correct field by using the trade side to "pivot" the results.

For example:
- **Sum**:
  - BUY TRADE: Goes to `BUY_SUM` field
  - SELL TRADE: Goes to `SELL_SUM` field

### Example 2 - Min and Max
This example demonstrates getting the minimum and maximum price of the buy and sell trades that have been verified for each individual instrument.

- **Consolidator**: `CONSOLIDATOR_MAX_MIN_SUMMARY` in [howto-consolidators-example2-consolidator.kts](server/howto-consolidators-app/src/main/genesis/scripts/howto-consolidators-example2-consolidator.kts)
- **Tables Involved**:
  - `TRADE` (input)
  - `MAX_MIN_SUMMARY` (output)
- **Dataservers for Consolidated Results**: `ALL_MAX_MIN_SUMMARY`
- **UI Page**: `Example_2`

#### Features Shown:
- `Min`: Finds the minimum value.
- `Max`: Finds the maximum value.
- `PivotBy`: Helps sum and count to update the correct field by using the trade side to "pivot" the results.
- `IndexScan`: Required for certain functions like min and max. Check the [documentation](https://docs.genesis.global/docs/develop/server-capabilities/real-time-aggregation-consolidator/#indexscan) for details.

### Example 3 - LogicalSum AND and OR
This example demonstrates getting information such as whether each region has completed their trades today and if a trade has been made to a big company.

- **Consolidator**: `CONSOLIDATOR_REGION_BIG_COMPANY_SUMMARY` in [howto-consolidators-example3-consolidator.kts](server/howto-consolidators-app/src/main/genesis/scripts/howto-consolidators-example3-consolidator.kts)
- **Tables Involved**:
  - `TRADE_VIEW` (input)
  - `INSTRUMENT` (input)
  - `REGION_BIG_COMPANY_SUMMARY` (output)
- **Dataservers for Consolidated Results**: `ALL_REGION_BIG_COMPANY_TRADE_SUMMARY`
- **UI Page**: `Example_3`

#### Features Shown:
- **Custom Function**:
- `count`: Aggregates the number of trades
- `onlyIf`: An alternative solution to PivotBy. Takes a condition and, if true, aggregates the value to the designated output field.
- `onCommit`: Optional step to modify the output fields after the functions being called.

## License
This is free and unencumbered software released into the public domain. For full terms, see [LICENSE](./LICENSE).

**NOTE**: This project uses licensed components listed in the next section, thus licenses for those components are required during development.

### Licensed Components
- Genesis application platform# Test results
BDD test results can be found [here](https://genesiscommunitysuccess.github.io/howto-consolidators/test-results)
