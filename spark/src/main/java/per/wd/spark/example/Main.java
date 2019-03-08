package per.wd.spark.example;


import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class Main {
    public static void main(String[] args) {
        SparkConf conf=new SparkConf();
        conf.setAppName("spark_test");
        conf.setMaster("local");
        SparkContext sparkContext=new SparkContext(conf);
        SparkSession sparkSession=new SparkSession(sparkContext);
        Dataset<Row> df = sparkSession.read().json("C:\\Users\\Administrator\\Desktop\\1.json");
        df.printSchema();
        df.where("salary>4000").show();
    }
}
