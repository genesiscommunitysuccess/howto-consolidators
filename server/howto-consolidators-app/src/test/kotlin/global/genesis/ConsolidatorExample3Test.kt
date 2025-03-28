package global.genesis

import global.genesis.db.rx.entity.multi.AsyncEntityDb
import global.genesis.gen.dao.Instrument
import global.genesis.gen.dao.RegionBigCompanySummary
import global.genesis.gen.dao.Trade
import global.genesis.gen.dao.enums.howto_consolidators.instrument.Region
import global.genesis.gen.dao.enums.howto_consolidators.trade.TradeSide
import global.genesis.gen.dao.enums.howto_consolidators.trade.TradeStatus
import global.genesis.jackson.core.GenesisJacksonMapper.Companion.toJsonString
import global.genesis.testsupport.jupiter.GenesisJunit
import global.genesis.testsupport.jupiter.TestScriptFile
import kotlinx.coroutines.runBlocking
import org.awaitility.kotlin.await
import org.awaitility.kotlin.untilAsserted
import org.joda.time.DateTime
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.math.BigDecimal
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.test.assertEquals

@ExtendWith(GenesisJunit::class)
@TestScriptFile("howto-consolidators-example3-consolidator.kts")
class ConsolidatorExample3Test {
    @Inject
    lateinit var entityDb: AsyncEntityDb
//    `When Insert Buy and Sell Trades, Expect Min and Max to calculated`
    @Test
    fun `When Inserting Trades and Instruments, Expect Region Trade Completion to be calculated` (): Unit = runBlocking {
        // Given: I have two trades, one with an instrument from NA region cancelled and the other from EU traded, and both not from big companies
        val today = DateTime.now()
        val naInstrument = Instrument.builder()
            .setIsin("na-isin")
            .setRegion(Region.NA)
            .setName("company INC. aa")
            .setCompany("company1")
            .setTicker("CY1")
            .build()
        val euInstrument = Instrument.builder()
            .setIsin("eu-isin")
            .setRegion(Region.EU)
            .setName("company INC. aa")
            .setCompany("company2")
            .setTicker("CY2")
            .build()
        val trade1 = Trade.builder()
            .setPrice(BigDecimal.valueOf(90))
            .setQuantity(BigDecimal.valueOf(1000))
            .setTradeSide(TradeSide.BUY)
            .setInstrumentCode("na-isin")
            .setTradeDate(today)
            .setTradeStatus(TradeStatus.CANCELLED)
            .build()
        val trade2 = Trade.builder()
            .setPrice(BigDecimal.valueOf(90))
            .setQuantity(BigDecimal.valueOf(1000))
            .setTradeSide(TradeSide.SELL)
            .setInstrumentCode("eu-isin")
            .setTradeDate(today)
            .setTradeStatus(TradeStatus.VERIFIED)
            .build()

    // When: I insert the trades and instruments in to the database
        entityDb.insert(naInstrument)
        entityDb.insert(euInstrument)
        entityDb.insert(trade1)
        entityDb.insert(trade2)


    // Then: I expect the consolidator to process region complete field for EU to be true, NA to false and Big company traded field to false
        await.atMost(5, TimeUnit.SECONDS) untilAsserted {
            runBlocking {
                val record1 = entityDb.get(RegionBigCompanySummary.byDate(today))
                assertEquals(true, record1?.euComplete)
                assertEquals(false, record1?.naComplete, "NA SUPPOSED TO BE FALSE")
                assertEquals(false, record1?.bigCompanyTraded, "BIG COMPANY SUPPOSED TO BE FALSE")
            }
        }
    }
}