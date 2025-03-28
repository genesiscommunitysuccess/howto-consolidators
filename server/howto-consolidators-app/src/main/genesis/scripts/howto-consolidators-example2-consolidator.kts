import global.genesis.gen.dao.enums.howto_consolidators.trade.TradeSide

consolidators {
    consolidator("CONSOLIDATOR_MAX_MIN_SUMMARY", TRADE, MAX_MIN_SUMMARY) {
        config {
            logLevel = INFO
            logFunctions = true
        }
        select {
            MAX_MIN_SUMMARY {
                max { price } pivotBy { tradeSide } into {
                    when(pivot) {
                        TradeSide.BUY -> BUY_MAX
                        TradeSide.SELL -> SELL_MAX
                    }
                }

                min { price } pivotBy { tradeSide } into {
                    when(pivot) {
                        TradeSide.BUY -> BUY_MIN
                        TradeSide.SELL -> SELL_MIN
                    }
                }

            }
        }
        groupBy { MaxMinSummary.ByInstrumentCode(instrumentCode) } into {
            build {
                MaxMinSummary {

                    /*
                    there is no default value set here for the fields since the output table has no non-null fields
                     */
                    instrumentCode = groupId.instrumentCode
                }
            }

            /*
            indexScan checks the input table with the groupId, it is required for certain functions like min and max.
            In example, if the max record is deleted, indexScan is triggered to find the next max record.
             */
            indexScan { Trade.byInstrumentCode(groupId.instrumentCode) }
        }
        where { tradeStatus == Trade.TradeStatus.VERIFIED }
    }
}
