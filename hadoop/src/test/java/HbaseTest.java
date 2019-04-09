import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Put;
import org.junit.Before;
import org.junit.Test;
import per.wd.spark.example.hbase.HbaseClient;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class HbaseTest {
    //列簇1
    private String cf1="c1";
    //列簇2
    private String cf2="c2";

    HbaseClient hbaseClient=new HbaseClient();
    @Before
    public void init() throws IOException {
        hbaseClient.initHabse();
    }

    @Test
    public void createTable() throws IOException {
        hbaseClient.createTable();
    }

    @Test
    public void deleteTable() throws IOException {
        hbaseClient.deleteTable();
    }


    @Test
    public void addData() throws IOException {
//        Put putRow1 = new Put("row1".getBytes());
        Put putRow1 = new Put("row2".getBytes());
        putRow1.addColumn(cf1.getBytes(), "user_name".getBytes(), "msg这是一条测试消息".getBytes());
        putRow1.addColumn(cf1.getBytes(), "url".getBytes(), "url192.168.1.1".getBytes());
        putRow1.addColumn(cf1.getBytes(), "user_url".getBytes(), "user_url192.168.1.1".getBytes());
        putRow1.addColumn(cf2.getBytes(), "title".getBytes(), "title这是一条测试消息".getBytes());
        putRow1.addColumn(cf2.getBytes(), "channel".getBytes(), "channel测试".getBytes());
        putRow1.addColumn(cf2.getBytes(), "created_at".getBytes(), "created_at测试".getBytes());
        putRow1.addColumn(cf2.getBytes(), "title".getBytes(), "created_at测试".getBytes());
        putRow1.addColumn(cf2.getBytes(), "type".getBytes(), "type测试".getBytes());
        putRow1.addColumn(cf2.getBytes(), "category".getBytes(), "category测试".getBytes());
        hbaseClient.testInsertDate(putRow1);
    }

    @Test
    public void oneRowKeyData() throws IOException {
//        Put putRow1 = new Put("row1".getBytes());
        for (int i = 0; i < 10; i++) {
            Put putRow1 = new Put("row4".getBytes());
            putRow1=putRow1.setTimestamp(i+1);
            putRow1.addColumn(cf1.getBytes(), "user_name".getBytes(), ("row3-"+i+"user_name").getBytes());
            putRow1.addColumn(cf1.getBytes(), "url".getBytes(), ("row3-"+i+"url").getBytes());
            putRow1.addColumn(cf1.getBytes(), "user_url".getBytes(),("row3-"+i+"user_url").getBytes());
            putRow1.addColumn(cf2.getBytes(), "title".getBytes(), ("row3-"+i+"title").getBytes());
            putRow1.addColumn(cf2.getBytes(), "channel".getBytes(), ("row3-"+i+"channel").getBytes());
            putRow1.addColumn(cf2.getBytes(), "created_at".getBytes(), ("row3-"+i+"created_at").getBytes());
            putRow1.addColumn(cf2.getBytes(), "title".getBytes(), ("row3-"+i+"title").getBytes());
            putRow1.addColumn(cf2.getBytes(), "type".getBytes(), ("row3-"+i+"type").getBytes());
            putRow1.addColumn(cf2.getBytes(), "category".getBytes(),("row3-"+i+"category").getBytes());
            hbaseClient.testInsertDate(putRow1);
        }

    }


    @Test
    public void listTable() throws IOException {
        List<String> list = hbaseClient.tableList();
        list.forEach(s->{
            System.out.println(s);
        });
    }
    @Test
    public void queryRowKey() throws IOException {
//        hbaseClient.queryByRowKey("row1");
        hbaseClient.queryByRowKey("row4");
    }

    @Test
    public void queryList() throws IOException {
//        hbaseClient.queryByRowKey("row1");
        hbaseClient.queryList();
    }
}
