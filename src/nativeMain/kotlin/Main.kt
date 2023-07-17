import app.softwork.sqldelight.postgresdriver.ListenerSupport
import app.softwork.sqldelight.postgresdriver.PostgresNativeDriver
import com.yt8492.ktornative.NativePostgres
import com.yt8492.ktornative.module
import com.yt8492.ktornative.repository.TodoRepository
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

fun main() {
    val coroutineContext = Dispatchers.Default + Job()
    val coroutineScope = CoroutineScope(coroutineContext)
    val driver = PostgresNativeDriver(
        host = "localhost",
        port = 5432,
        user = "postgres",
        database = "postgres",
        password = "password",
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
            module(todoRepository)
        },
    ).start(wait = true)
}
