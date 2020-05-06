package com.github.linyuzai.janalyzer.markdown.word;

import com.github.linyuzai.janalyzer.markdown.element.*;
import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.*;

public class DocxWriter {

    private MarkdownElement markdownElement;
    private String html;

    private boolean isHtml;

    private String charset = "utf-8";

    public DocxWriter(MarkdownElement markdownElement) {
        this.markdownElement = markdownElement;
    }

    public DocxWriter(String html) {
        this.html = html;
        isHtml = true;
    }

    public DocxWriter charset(String charset) {
        this.charset = charset;
        return this;
    }

    public DocxWriter write(String path) throws IOException {
        return write(new File(path));
    }

    public DocxWriter write(File file) throws IOException {
        return write(new FileOutputStream(file));
    }

    public DocxWriter write(OutputStream os) throws IOException {
        if (isHtml) {
            writeHtml(os);
        } else {
            throw new RuntimeException("Not finished");
        }
        return this;
    }

    private void writeHtml(OutputStream os) throws IOException {
        byte b[] = html.getBytes(charset);  //这里是必须要设置编码的，不然导出中文就会乱码。
        ByteArrayInputStream bis = new ByteArrayInputStream(b);//将字节数组包装到流中
        POIFSFileSystem pfs = new POIFSFileSystem();
        DirectoryEntry directory = pfs.getRoot();
        directory.createDocument("docx", bis);
        pfs.writeFilesystem(os);
        bis.close();
    }
}
