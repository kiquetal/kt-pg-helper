
import com.github.jasync.sql.db.RowData
import com.github.jasync.sql.db.pool.ConnectionPool
import com.github.jasync.sql.db.postgresql.PostgreSQLConnection
import com.github.jasync.sql.db.postgresql.PostgreSQLConnectionBuilder
import org.slf4j.LoggerFactory
import java.text.MessageFormat
import java.util.function.Consumer

fun createClient(): ConnectionPool<PostgreSQLConnection> {
    return PostgreSQLConnectionBuilder.createConnectionPool(
        "jdbc:postgresql://localhost:5432/music?user=pg4e&password=paraguay"
    )
}


class Connect {

   companion object {
        @JvmStatic
       fun main(args: Array<String>) {
            var logger = LoggerFactory.getLogger(Connect::class.java)

            val postgres = createClient()
            val conn =   postgres.connect()
            val result=    conn.get().sendPreparedStatement("select * from artist");
            val rows = result.get().rows;
            rows.stream().forEach(Consumer { t:RowData ->
                logger?.info(MessageFormat.format("Id={0}|Name={1}\n",t.get(0),t.get(1)))

            })
       }

   }

}
