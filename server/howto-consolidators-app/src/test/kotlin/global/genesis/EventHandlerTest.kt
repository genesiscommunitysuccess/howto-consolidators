import global.genesis.db.rx.entity.multi.AsyncEntityDb
import global.genesis.gen.dao.Trade
import global.genesis.gen.dao.enums.howto_consolidators.trade.TradeSide
import global.genesis.message.core.event.EventReply
import global.genesis.testsupport.client.eventhandler.EventClientSync
import global.genesis.testsupport.jupiter.GenesisJunit
import global.genesis.testsupport.jupiter.ScriptFile
import global.genesis.testsupport.jupiter.TestScriptFile
import global.genesis.testsupport.jupiter.assertedCast
import java.math.BigDecimal
import javax.inject.Inject
import kotlin.String
import kotlin.Unit
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.joda.time.DateTime.now
import org.joda.time.DateTime.parse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(GenesisJunit::class)
@TestScriptFile("howto-consolidators-eventhandler.kts")
class EventHandlerTest {
  @Inject
  lateinit var client: EventClientSync

  @Inject
  lateinit var entityDb: AsyncEntityDb

  private val adminUser: String = "admin"

  @Test
  fun `test insert TRADE`(): Unit = runBlocking {
    val result = client.sendEvent(
      details = Trade {
        price = BigDecimal("0.1")
        quantity = BigDecimal("0.1")
        tradeDate = now()
        tradeSide = TradeSide.BUY
        instrumentCode = "IC10"
      },
      messageType = "EVENT_TRADE_INSERT",
      userName = adminUser
    )
    result.assertedCast<EventReply.EventAck>()
    val trade = entityDb.getBulk<Trade>().toList()
    assertTrue(trade.isNotEmpty())
  }

  @Test
  fun `test modify TRADE`(): Unit = runBlocking {
    val result = entityDb.insert(
      Trade {
        price = BigDecimal("0.1")
        quantity = BigDecimal("0.1")
        tradeDate = now()
        tradeSide = TradeSide.BUY
        instrumentCode = "IC10"
      }
    )
    val tradeIdValue = result.record.tradeId
    val modifyResult = client.sendEvent(
      details = Trade {
        price = BigDecimal("0.2")
        quantity = BigDecimal("0.2")
        tradeId = tradeIdValue
        tradeDate = parse("2024-01-01T00:00:00.000Z")
        tradeSide = TradeSide.SELL
        instrumentCode = "IC10"
      },
      messageType = "EVENT_TRADE_MODIFY",
      userName = adminUser
    )
    modifyResult.assertedCast<EventReply.EventAck>()
    val modifiedRecord = entityDb.get(Trade.ById(tradeIdValue))
    assertEquals(0, BigDecimal("0.2").compareTo(modifiedRecord?.price))
    assertEquals(0, BigDecimal("0.2").compareTo(modifiedRecord?.quantity))
    assertEquals(0, parse("2024-01-01T00:00:00.000Z").compareTo(modifiedRecord?.tradeDate))
    assertEquals(TradeSide.SELL, modifiedRecord?.tradeSide)
  }

  @Test
  fun `test delete TRADE`(): Unit = runBlocking {
    val result = entityDb.insert(
      Trade {
        price = BigDecimal("0.1")
        quantity = BigDecimal("0.1")
        tradeDate = now()
        tradeSide = TradeSide.BUY
        instrumentCode = "IC10"
      }
    )
    val numRecordsBefore = entityDb.getBulk<Trade>().toList().size
    val tradeIdValue = result.record.tradeId
    val deleteResult = client.sendEvent(
      details = Trade.ById(tradeIdValue),
      messageType = "EVENT_TRADE_DELETE",
      userName = adminUser
    )
    deleteResult.assertedCast<EventReply.EventAck>()
    val numRecordsAfter = entityDb.getBulk<Trade>().toList().size
    assertEquals(numRecordsBefore - 1, numRecordsAfter)
  }
}
