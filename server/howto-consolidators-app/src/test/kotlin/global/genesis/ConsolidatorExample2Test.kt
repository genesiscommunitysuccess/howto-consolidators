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
@TestScriptFile("howto-consolidators-example2-consolidator.kts")
class ConsolidatorExample2Test {
    @Inject
    lateinit var entityDb: AsyncEntityDb

    @Test
    fun `When Insert Buy and Sell Trades, Expect Min and Max to calculated`(): Unit = runBlocking {
        // Given: I have 5 trades with the lowest sell being 30 and highest 90, and lowest buy being 90 and highest being 90 all with the same instrument
        val trade1 = Trade.builder()
            .setPrice(BigDecimal.valueOf(90))
            .setQuantity(BigDecimal.valueOf(1000))
            .setTradeSide(TradeSide.BUY)
            .setInstrumentCode("IS-10")
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
            .setPrice(BigDecimal.valueOf(30))
            .setQuantity(BigDecimal.valueOf(1000))
            .setTradeSide(TradeSide.SELL)
            .setInstrumentCode("IS-10")
            .setTradeDate(DateTime.now())
            .build()
        val trade4 = Trade.builder()
            .setPrice(BigDecimal.valueOf(120))
            .setQuantity(BigDecimal.valueOf(1000))
            .setTradeSide(TradeSide.SELL)
            .setInstrumentCode("IS-10")
            .setTradeDate(DateTime.now())
            .build()
        val trade5 = Trade.builder()
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
        entityDb.insert(trade5)

        // Then: I expect the consolidator to process and have a consolidated row with the lowest sell being 30, highest being 120, and lowest buy being 90, highest being 90
        await untilAsserted {
            runBlocking {
                val record1 = entityDb.get(MaxMinSummary.byInstrumentCode("IS-10"))
                assertEquals(BigDecimal("90.00000"), record1?.buyMin)
                assertEquals(BigDecimal("90.00000"), record1?.buyMax)
                assertEquals(BigDecimal("30.00000"), record1?.sellMin)
                assertEquals(BigDecimal("120.00000"), record1?.sellMax)
            }
        }
    }
}