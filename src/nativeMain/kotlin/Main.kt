import app.softwork.sqldelight.postgresdriver.ListenerSupport
import app.softwork.sqldelight.postgresdriver.PostgresNativeDriver
import com.yt8492.ktornative.NativePostgres
import com.yt8492.ktornative.module
import com.yt8492.ktornative.repository.TodoRepository
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.cinterop.toKString
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import platform.posix.getenv

fun main() {
  val coroutineContext = Dispatchers.Default + Job()
  val coroutineScope = CoroutineScope(coroutineContext)
  val dbHost = getenv("POSTGRES_HOST")?.toKString()
    ?: throw IllegalArgumentException("POSTGRES_HOST must not be null")
  val dbPort = getenv("POSTGRES_PORT")?.toKString()?.toInt()
    ?: throw IllegalArgumentException("POSTGRES_PORT must not be null")
  val dbUser = getenv("POSTGRES_USER")?.toKString()
    ?: throw IllegalArgumentException("POSTGRES_USER must not be null")
  val dbName = getenv("POSTGRES_DB")?.toKString()
    ?: throw IllegalArgumentException("POSTGRES_DB must not be null")
  val dbPassword = getenv("POSTGRES_PASSWORD")?.toKString()
    ?: throw IllegalArgumentException("POSTGRES_PASSWORD must not be null")
  val driver = PostgresNativeDriver(
    host = dbHost,
    port = dbPort,
    user = dbUser,
    database = dbName,
    password = dbPassword,
    options = null,
    listenerSupport = ListenerSupport.Remote(coroutineScope),
  )
  val database = NativePostgres(driver)
  val todoRepository = TodoRepository(database.todoQueries)
  embeddedServer(
    factory = CIO,
    port = 8080,
    host = "0.0.0.0",
    module = {
      install(ContentNegotiation) {
        json()
      }
      module(todoRepository)
    },
  ).start(wait = true)
}
