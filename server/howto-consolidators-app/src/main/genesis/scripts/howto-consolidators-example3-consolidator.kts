import global.genesis.gen.dao.enums.howto_consolidators.instrument.Region
import global.genesis.gen.dao.enums.howto_consolidators.trade.TradeStatus

consolidators {
    consolidator("CONSOLIDATOR_REGION_BIG_COMPANY_SUMMARY", TRADE_VIEW, REGION_BIG_COMPANY_SUMMARY) {
        config {
            logLevel = INFO
            logFunctions = true
        }
        select {
            REGION_BIG_COMPANY_SUMMARY {

                /*
                onlyIf filters if a function's return is applied to the field output if the condition in the block is met.
                 */
                count() onlyIf { TradeStatus.VERIFIED == tradeStatus  && listOf("AMZ", "GOOG", "MSFT").contains(ticker) } into BIG_COMPANY_TRADED_COUNT
                count() onlyIf { TradeStatus.VERIFIED != tradeStatus  && Region.NA == region } into NA_INCOMPLETE_COUNT
                count() onlyIf { TradeStatus.VERIFIED != tradeStatus  && Region.EU == region } into EU_INCOMPLETE_COUNT
                count() onlyIf { TradeStatus.VERIFIED != tradeStatus  && Region.MEA == region } into MEA_INCOMPLETE_COUNT
                count() onlyIf { TradeStatus.VERIFIED != tradeStatus  && Region.APAC == region } into APAC_INCOMPLETE_COUNT
                count() onlyIf { TradeStatus.VERIFIED != tradeStatus  && Region.LATAM == region } into LATAM_INCOMPLETE_COUNT

            }
        }
        groupBy { RegionBigCompanySummary.ByDate(tradeDate) } into {
            build { RegionBigCompanySummary {
                date = groupId.date
                }
            }
        }

        /*
        onCommit allows for changes to be made after functions are finished running and before perform a database operation.
        you have input and output available to make changes.
         */
        onCommit {
            output.bigCompanyTraded = (output.bigCompanyTradedCount != null && output.bigCompanyTradedCount!! > 0)
            output.naComplete = (output.naIncompleteCount == null || output.naIncompleteCount!! == 0)
            output.euComplete = (output.euIncompleteCount == null || output.euIncompleteCount!! == 0)
            output.meaComplete = (output.meaIncompleteCount == null || output.meaIncompleteCount!! == 0)
            output.apacComplete = (output.apacIncompleteCount == null || output.apacIncompleteCount!! == 0)
            output.latamComplete = (output.latamIncompleteCount == null || output.latamIncompleteCount!! == 0)
        }
    }
}