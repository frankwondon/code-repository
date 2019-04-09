package per.wd.spark.example.hbase;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.io.compress.Compression;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 基于Hbase-client 对Hbase的操作
 *
 * 参考文献 https://www.cnblogs.com/wzzkaifa/p/7323279.html （基本介绍）
 * https://www.cnblogs.com/duanxz/p/4660784.html（rowkey的设计原则）
 * @author wangdong
 * @date 2019/4/3
 * @version 1.0.0
 */
@Slf4j
public class HbaseClient {
    private String test_table="test_table";
    private Admin admin;
    private  Connection connection;
    //列簇1
    private String cf1="c1";
    //列簇2
    private String cf2="c2";
    /**
     * 初始化hbase
     * @throws IOException
     */
    public void initHabse() throws IOException {
        Configuration config = HBaseConfiguration.create();
        config.set("hbase.rootdir","hdfs://172.16.21.180:8020/hbase");
        config.set("hbase.zookeeper.quorum","172.16.21.180");
        config.set("hbase.zookeeper.property.clientPort","2181");
//        config.addResource(new Path("D:\\work\\project\\workspace\\code-repository\\spark\\src\\main\\resources", "hbase-site.xml"));
//        config.addResource(new Path("D:\\work\\project\\workspace\\code-repository\\spark\\src\\main\\resources", "core-site.xml"));
        connection = ConnectionFactory.createConnection(config);
        admin=connection.getAdmin();
    }

    /**
     * 创建Hbase表
     * 注意： .setMaxVersions(100) 这个如果不设置到查询的时候是不能查出多个Timetamp的数据的
     * @throws IOException
     */
    public void createTable() throws IOException {
        if (!admin.tableExists(TableName.valueOf(test_table))){
            TableDescriptor build = TableDescriptorBuilder.newBuilder(TableName.valueOf(test_table))
                    .setColumnFamily(ColumnFamilyDescriptorBuilder.newBuilder(ColumnFamilyDescriptorBuilder.of(cf1))
                            .setMaxVersions(100).build())
                    .setColumnFamily(ColumnFamilyDescriptorBuilder.newBuilder(ColumnFamilyDescriptorBuilder.of(cf2))
                            .setMaxVersions(100).build())
                    .build();
            admin.createTable(build);
        }else{
            log.info("the table:{} exists in hbase");
        }
    }


    public void deleteTable() throws IOException {
        if (admin.tableExists(TableName.valueOf(test_table))){
            admin.disableTable(TableName.valueOf(test_table));
            admin.deleteTable(TableName.valueOf(test_table));
        }else{
            log.info("the table:{} not exists in hbase");
        }
    }




    public void testInsertDate(Put put) throws IOException {
        if (!admin.tableExists(TableName.valueOf(test_table))){
            log.warn("不存在该表");
            return;
        }
        Table table1 = connection.getTable(TableName.valueOf(test_table));
        table1.put(put);
    }

    public List<String> tableList() throws IOException {
        TableName[] tableNames = admin.listTableNames();
        if (tableNames!=null&& tableNames.length>0){
            List<String> list=new ArrayList<>();
            for (TableName tableName : tableNames) {
                list.add(tableName.getNameAsString());
            }
            return list;
        }
        return Collections.EMPTY_LIST;
    }

    public void queryByRowKey(String rowKey) throws IOException {
        Table table1 = connection.getTable(TableName.valueOf(test_table));
        Get get=new Get(Bytes.toBytes(rowKey));
        //该函数设置返回版本的数目  如果不传参数  则设置为Int.MaxValue  传参数则返回对应参数的数目的数据
        //前面提到setTimeRange的作用，单独使用这个方法，得到的是在这个是在区域内最新的一条数据
        //但是如果setTimeRange和setMaxVersions配合起来使用，则可以达到取出一段时间戳内的数据。
        //如上面的例子，时间范围设置为[1,50),setMaxVersions设置为40，则会取出[1,50)最新的40条数据，即10-49
        //如果时间范围设置为[1,10)，setMaxVersions设置为40，这个时候范围内的数不足40，则会将范围内的数全部取出
        get = get.readVersions(10);
        get = get.setTimeRange(0,10);
        Result result = table1.get(get);
        List<Cell> cells = result.listCells();
        System.out.println("rowsize:"+    result.size());
        for (Cell cell : cells) {
//            System.out.println(cell.toString());
            System.out.println("RowName:" + new String(CellUtil.cloneRow(cell)) + " ");
            System.out.print("Timetamp:" + cell.getTimestamp() + " ");
            System.out.print("column Family:" + new String(CellUtil.cloneFamily(cell)) + " ");
            System.out.print("row Name:" + new String(CellUtil.cloneQualifier(cell)) + " ");
            System.out.print("value:" + new String(CellUtil.cloneValue(cell)) + " ");
        }

    }


    public void  queryList() throws IOException {
        Table table = connection.getTable(TableName.valueOf(test_table));
        Scan s = new Scan();
        ResultScanner ss = table.getScanner(s);
        for(Result r:ss){
            formatRow(r);
        }
    }

    private void formatRow(Result r){
        System.out.println( r.listCells().toString());
    }

}
