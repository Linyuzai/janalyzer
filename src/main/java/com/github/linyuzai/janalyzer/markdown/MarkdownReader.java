package com.github.linyuzai.janalyzer.markdown;

import com.github.linyuzai.janalyzer.markdown.analyzer.MarkdownAnalyzer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MarkdownReader {

    private String content;
    private File file;
    private int bufferSize;
    private MarkdownAnalyzer analyzer;

    public MarkdownReader content(String content) {
        this.content = content;
        this.file = null;
        return this;
    }

    public MarkdownReader file(File file) {
        this.file = file;
        this.content = null;
        return this;
    }

    public MarkdownReader file(String path) {
        return file(new File(path));
    }

    public MarkdownReader bufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
        return this;
    }

    public MarkdownReader analyzer(MarkdownAnalyzer analyzer) {
        this.analyzer = analyzer;
        return this;
    }

    public MarkdownDocument read() throws IOException {
        if (content == null) {
            if (file == null) {
                throw new RuntimeException("No content or file set");
            } else {
                FileInputStream fis = new FileInputStream(file);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                if (bufferSize <= 0) {
                    bufferSize = 1024;
                }
                byte[] buffer = new byte[bufferSize];
                int length;
                while ((length = fis.read(buffer)) != -1) {
                    bos.write(buffer, 0, length);
                }
                content = bos.toString();
                bos.close();
                fis.close();
            }
        }
        content = content
                .replaceAll("\r\n", "\n")
                .replaceAll("\r", "\n");
        if (analyzer == null) {
            analyzer = MarkdownAnalyzer.Proxy.getInstance();
        }
        analyzer.registerAnalyzers();
        return new MarkdownDocument(analyzer.analyze(analyzer.newContext(content, true)));
    }
}
