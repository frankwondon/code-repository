import org.apache.hadoop.fs.Path;
import org.junit.Before;
import org.junit.Test;
import per.wd.spark.example.hdfs.HdfsClient;

import java.io.IOException;
import java.util.List;

public class HdfsTest {
    HdfsClient hdfsClient=new HdfsClient();
    @Before
    public void init() throws IOException {
        //设置hadoop操作用户，不然会报org.apache.hadoop.security.AccessControlException: Permission denied: user=Administrator, access=WRITE, inode="/":hdfs:supergroup:drwxr-xr-x
        //参考这两篇文章https://blog.csdn.net/diqijiederizi/article/details/82753573
        //https://blog.csdn.net/u010885548/article/details/80624154
        System.setProperty("HADOOP_USER_NAME","hdfs");
        hdfsClient.initFileSystem();
    }
    @Test
    public void createFile() throws IOException {
        Path pathHdfs=new Path("/hdfs/test/");
        if (!hdfsClient.existsFile(pathHdfs)){
            hdfsClient.createFile(pathHdfs);
        }
    }

    @Test
    public void delHdfs() throws IOException {
        Path pathHdfs=new Path("/hdfs/test");
        hdfsClient.delFile(pathHdfs);
    }


    @Test
    public void copyFileToHdfs() throws IOException {
//        Path pathLocal=new Path("E:\\hadoop\\hdfs测试文件\\user.txt");
//        Path pathHdfs=new Path("/hdfs/test/user.txt");
        Path pathLocal=new Path("E:\\hadoop\\hdfs测试文件\\image.jpg");
        Path pathHdfs=new Path("/hdfs/test/image.jpg");
        hdfsClient.copyLocalToHdfs(pathLocal,pathHdfs);
    }

    @Test
    public void getStringFile() throws IOException {
        Path pathHdfs=new Path("/hdfs/test/user.txt");
        String fileAsString = hdfsClient.getFileAsString(pathHdfs);
        System.out.println("=========获取hdfs文件内容==========");
        System.out.println(fileAsString);
        System.out.println("===================================");
    }


    @Test
    public void listHdfsFile() throws IOException {
        Path pathHdfs=new Path("/hdfs/");
        List<String> strings = hdfsClient.listFiles(pathHdfs);
        System.out.println("=========获取hdfs文件内容==========");
        strings.forEach(s -> {
            System.out.println(s);
        });
        System.out.println("===================================");
    }
}
