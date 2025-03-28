package global.genesis

import global.genesis.db.rx.entity.multi.AsyncEntityDb
import global.genesis.gen.dao.*
import global.genesis.gen.dao.enums.howto_consolidators.trade.TradeSide
import global.genesis.gen.dao.enums.howto_consolidators.trade.TradeStatus
import global.genesis.testsupport.jupiter.GenesisJunit
import global.genesis.testsupport.jupiter.TestScriptFile
import kotlinx.coroutines.runBlocking
import org.awaitility.kotlin.await
import org.awaitility.kotlin.untilAsserted
import org.joda.time.DateTime
import org.junit.jupiter.api.extension.ExtendWith
import java.math.BigDecimal

import javax.inject.Inject
import kotlin.test.Test
import kotlin.test.assertEquals

@ExtendWith(GenesisJunit::class)
@TestScriptFile("howto-consolidators-example1-consolidator.kts")
class ConsolidatorExample1Test {
    @Inject
    lateinit var entityDb: AsyncEntityDb

    @Test
    fun `When Insert Buy and Sell Trades, Expect Buy and Sell Counted and Summed`(): Unit = runBlocking {
        // Given: I have 5 trades with the lowest sell being 30 and highest 90, and lowest buy being 90 and highest being 90 all with the same instrument
        // Given: I have 4 trades, 1 trade with IS-11 instrument and 3 with IS-10
        val trade1 = Trade.builder()
            .setPrice(BigDecimal.valueOf(90))
            .setQuantity(BigDecimal.valueOf(1000))
            .setTradeSide(TradeSide.BUY)
            .setInstrumentCode("IS-11")
            .setTradeDate(DateTime.now())
            .build()
        val trade2 = Trade.builder()
            .setPrice(BigDecimal.valueOf(90))
            .setQuantity(BigDecimal.valueOf(1000))
            .setTradeSide(TradeSide.SELL)
            .setInstrumentCode("IS-10")
            .setTradeDate(DateTime.now())
            .build()
        val trade3 = Trade.builder()
            .setPrice(BigDecimal.valueOf(90))
            .setQuantity(BigDecimal.valueOf(1000))
            .setTradeSide(TradeSide.SELL)
            .setInstrumentCode("IS-10")
            .setTradeDate(DateTime.now())
            .build()
        val trade4 = Trade.builder()
            .setPrice(BigDecimal.valueOf(150))
            .setQuantity(BigDecimal.valueOf(1000))
            .setTradeSide(TradeSide.SELL)
            .setInstrumentCode("IS-10")
            .setTradeDate(DateTime.now())
            .setTradeStatus(TradeStatus.CANCELLED)
            .build()

        // When: I insert the trades in to the database
        entityDb.insert(trade1)
        entityDb.insert(trade2)
        entityDb.insert(trade3)
        entityDb.insert(trade4)

        // Then: I expect the consolidator to process and correctly output 2 rows with sum and count of buy and sell trades separately, grouped by the instrument Id
        await untilAsserted  {
            runBlocking {
                val record1 = entityDb.get(CountSumSummary.byInstrumentCode("IS-10"))
                assertEquals(0, record1?.buyCount)
                assertEquals(BigDecimal("0.00000"), record1?.buySum)
                assertEquals(2, record1?.sellCount)
                assertEquals(BigDecimal("2000.00000"), record1?.sellSum)


                val record2 = entityDb.get(CountSumSummary.byInstrumentCode("IS-11"))
                assertEquals(1, record2?.buyCount)
                assertEquals(BigDecimal("1000.00000"), record2?.buySum)
                assertEquals(0, record2?.sellCount)
                assertEquals(BigDecimal("0.00000"), record2?.sellSum)
            }
        }
    }
}