// Command to run:
// scala -cp .\mssql-jdbc-12.4.1.jre8.jar .\get_path_item.scala

import java.sql.{Connection, DriverManager, ResultSet}

object ItemPathRetriever {
  def main(args: Array[String]): Unit = {
    val server = "LAPTOP-0KDACI8U"
    val database = "localizer"
    val windowsAuth = true
    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver")

    //val url = s"jdbc:sqlserver://$server;databaseName=$database;integratedSecurity=$windowsAuth"
    val url = s"jdbc:sqlserver://$server;databaseName=$database;integratedSecurity=$windowsAuth;encrypt=false;"

    try {
      // Establish a database connection
      val connection = DriverManager.getConnection(url)

      // Define your SQL query here
      val query =
        """
WITH ItemPath AS (
    SELECT
        id,
        name,
		parent_id,
        CAST(name AS VARCHAR(MAX)) AS path,
		ROW_NUMBER() OVER (ORDER BY id) as row
    FROM
        items
    WHERE
        name = 'Hammer'  -- Replace 'Hammer' with the item you're searching for
    UNION ALL
    SELECT
        i.id,
        i.name,
		i.parent_id,
        CONCAT(i.name, ' -> ', p.path),
		p.row + 1 as row
    FROM
        items i
    JOIN
        ItemPath p ON i.id = p.parent_id
	Where p.parent_id is not null
)
SELECT TOP 1
    path
FROM
    ItemPath
ORDER BY row DESC;
        """

      val statement = connection.createStatement()
      val resultSet: ResultSet = statement.executeQuery(query)

      if (resultSet.next()) {
        val itemPath = resultSet.getString("path")
        println(s"ItemPath: $itemPath")
      }

      connection.close()
    } catch {
      case e: Exception =>
        e.printStackTrace()
    }
  }
}
