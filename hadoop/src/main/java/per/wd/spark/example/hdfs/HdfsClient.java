package per.wd.spark.example.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class HdfsClient {
    private FileSystem fileSystem;
    private Configuration configuration;

    public synchronized void initFileSystem() throws IOException {
        configuration=new Configuration();
        configuration.set("fs.defaultFS", "hdfs://172.16.21.180:8020");
        fileSystem=FileSystem.get(configuration);
    }

    /**
     * 将本地文件上传到hdfs,如果存在，会删除之前的文件
     * */
    public  void copyLocalToHdfs(Path localFile, Path hdfsPath) throws IOException {
        fileSystem.copyFromLocalFile(true,localFile,hdfsPath);
    }

    /**
     * 递归获取hdfs中的文件
     * @param path
     * @throws IOException
     */
    public  List<String> listFiles(Path path) throws IOException {
        List<String> stringList=new ArrayList<>();
        RemoteIterator<LocatedFileStatus> lists = fileSystem.listFiles(path, true);
        while (lists.hasNext()){
            LocatedFileStatus next = lists.next();
            Path path1 = next.getPath();
            stringList.add(path1.getName());
        }
        return stringList;
    }

    public boolean existsFile(Path path) throws IOException {
        return fileSystem.exists(path);
    }

    public void createFile(Path path) throws IOException {
        fileSystem.create(path);
    }

    public void delFile(Path path) throws IOException {
        fileSystem.delete(path,true);
    }


    public String getFileAsString(Path path) throws IOException {
        InputStream stream=null;
        ByteArrayOutputStream outputStream=null;
        if (fileSystem.exists(path)){
            try {
                FSDataInputStream open = fileSystem.open(path);
                stream = open.getWrappedStream();
                byte[] buffer = new byte[1024];
                int length;
                outputStream=new ByteArrayOutputStream();
                while ((length = stream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, length);
                }
                return outputStream.toString(StandardCharsets.UTF_8.name());
            } finally {
                stream.close();
                outputStream.close();
            }
        }else {
            throw  new IOException("file not exists in hdfs");
        }
    }
}
