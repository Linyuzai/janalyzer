package com.github.linyuzai.janalyzer.markdown.word;

import com.github.linyuzai.janalyzer.markdown.element.*;
import com.github.linyuzai.janalyzer.markdown.html.HtmlRenderer;
import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.*;

public class DocWriter {

    private MarkdownElement markdownElement;
    private String html;
    private String name = "word";

    private boolean isHtml;

    private String charset = "utf-8";

    public DocWriter(MarkdownElement markdownElement) {
        this.markdownElement = markdownElement;
    }

    public DocWriter(String html) {
        this.html = html;
        isHtml = true;
    }

    public DocWriter name(String name) {
        this.name = name;
        return this;
    }

    public DocWriter charset(String charset) {
        this.charset = charset;
        return this;
    }

    public DocWriter write(String path) throws IOException {
        return write(new File(path));
    }

    public DocWriter write(File file) throws IOException {
        return write(new FileOutputStream(file));
    }

    public DocWriter write(OutputStream os) throws IOException {
        if (!isHtml) {
            html = new HtmlRenderer(markdownElement).render().getContent();
        }
        writeHtml(os);
        return this;
    }

    private void writeHtml(OutputStream os) throws IOException {
        byte[] bytes = html.getBytes(charset);
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        POIFSFileSystem pfs = new POIFSFileSystem();
        DirectoryEntry directory = pfs.getRoot();
        directory.createDocument(name, bis);
        pfs.writeFilesystem(os);
        bis.close();
    }
}
