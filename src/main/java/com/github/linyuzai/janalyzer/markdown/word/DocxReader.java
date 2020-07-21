package com.github.linyuzai.janalyzer.markdown.word;

import com.github.linyuzai.janalyzer.markdown.MarkdownDocument;
import com.github.linyuzai.janalyzer.markdown.MarkdownReader;
import com.github.linyuzai.janalyzer.markdown.word.handler.ElementHandler;
import org.apache.poi.xwpf.usermodel.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DocxReader {

    private XWPFDocument document;
    private List<ElementHandler> handlers = new ArrayList<>();
    private String content;

    public DocxReader(InputStream is) throws IOException {
        this(new XWPFDocument(is));
    }

    public DocxReader(XWPFDocument document) throws IOException {
        this.document = document;
    }

    public DocxReader addHandler(ElementHandler handler) {
        handlers.add(handler);
        return this;
    }

    public DocxReader read() {
        content = read(document.getBodyElements(), true).trim();
        return this;
    }

    public MarkdownDocument markdown() throws IOException {
        return markdown(new MarkdownReader());
    }

    public MarkdownDocument markdown(MarkdownReader reader) throws IOException {
        return reader.content(content).read();
    }

    public String read(List<?> elements, boolean newline) {
        StringBuilder builder = new StringBuilder();
        for (Object element : elements) {
            ElementHandler handler = getHandler(element);
            if (handler == null) {
                continue;
            }
            String h = handler.handle(this, element);
            if (h == null || h.isEmpty()) {
                continue;
            }
            builder.append(h);
            if (newline) {
                newline(h, builder);
            }
        }
        return builder.toString();
    }

    public void newline(String s, StringBuilder builder) {
        int lineCount = 0;
        int i = 1;
        while (s.charAt(s.length() - i) == '\n') {
            lineCount++;
            if (lineCount > 2) {
                builder.deleteCharAt(builder.length() - i);
            }
            i++;
        }
        if (lineCount == 0) {
            builder.append("\n\n");
        } else if (lineCount == 1) {
            builder.append("\n");
        }
    }

    public String getContent() {
        return content;
    }

    private ElementHandler getHandler(Object element) {
        for (ElementHandler handler : handlers) {
            if (handler.support(this, element)) {
                return handler;
            }
        }
        return null;
    }
}
