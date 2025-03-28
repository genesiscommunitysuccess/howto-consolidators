import global.genesis.gen.dao.enums.howto_consolidators.trade.TradeSide
import java.math.BigDecimal

consolidators {
    consolidator("CONSOLIDATOR_SUM_COUNT_SUMMARY", TRADE, COUNT_SUM_SUMMARY) {

        /*
        Config contains options that affect the behaviour of the consolidator it is in.
        In this case, logging functionality is set to INFO and for functions to log
        this makes debugging easier.

        To see what we support visit: https://docs.genesis.global/docs/develop/server-capabilities/real-time-aggregation-consolidator/#config
         */
        config {
            logLevel = INFO
            logFunctions = true

        }

        /*
        Select is where functions for aggregation are called.
        A function is called and then returned into an output field
        {function} into {output field}
         */
        select {

            /*
             Adding the output table allows for concise syntax when returning into output fields.
             Otherwise, you would have to do:
             sum { input-field } into OutputTable::field
             instead of:
             Table {
                sum { input-field } into output-field
             }
             */
            COUNT_SUM_SUMMARY {

                /*
                count can either count a record (count()) or count a field has a value (count { field })
                 */
                count() pivotBy { tradeSide } into {

                    /*
                    PivotBy introduces a pivot variable that is set by its inline function to an enum value.
                    The into inline function MUST exhaust all potential options presented by the pivot variable.
                    This could mean returning into a table field or exhausting with a conditional statement like if or when shown as below.

                    Note that when a record TradeSide is switched from BUY to SELL,
                    ehe Consolidator is capable by itself to in this case, decrement the buy count and increment the sell count.
                     */
                    when(pivot) {
                        TradeSide.BUY -> BUY_COUNT
                        TradeSide.SELL -> SELL_COUNT
                    }
                }

                /*
                sum can tally the total of a field's value (sum { field })
                 */
                sum { quantity } pivotBy {tradeSide} into {
                    when(pivot) {
                        TradeSide.BUY -> BUY_SUM
                        TradeSide.SELL -> SELL_SUM
                    }
                }
            }
        }

        /*
        GroupBy is the start to determine how to group input tables into output,
        the value inside the block determines the groupId that will be used to build the consolidated table.
         */
        groupBy { CountSumSummary.ByInstrumentCode(instrumentCode) } into {
            /*
            Build is required to create output record if lookup cannot find a record with the groupId.
             */
            build {
                CountSumSummary {
                    instrumentCode = groupId.instrumentCode
                    /*
                    Any fields that are non-null needs to be instantiated such as below
                    or by using defaults in the output table's definition
                     */
                    buyCount = 0
                    buySum = BigDecimal.ZERO
                    sellCount = 0
                    sellSum = BigDecimal.ZERO
                }
            }
        }

        /*
        Where block takes in an expression to dictate if the record is to be consolidated.
        Consolidators are able to reconsolidate input records when they match the criteria, including in or excluding from
        the aggregation
         */
        where { tradeStatus == Trade.TradeStatus.VERIFIED }

    }
}
