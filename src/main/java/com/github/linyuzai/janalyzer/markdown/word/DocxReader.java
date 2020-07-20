package com.github.linyuzai.janalyzer.markdown.word;

import com.github.linyuzai.janalyzer.markdown.word.handler.BodyElementHandler;
import org.apache.poi.xwpf.usermodel.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DocxReader {

    private XWPFDocument document;
    private List<BodyElementHandler> handlers = new ArrayList<>();
    private String content;

    public DocxReader(InputStream is) throws IOException {
        this(new XWPFDocument(is));
    }

    public DocxReader(XWPFDocument document) throws IOException {
        this.document = document;
    }

    public DocxReader addHandler(BodyElementHandler handler) {
        handlers.add(handler);
        return this;
    }

    public DocxReader read() {
        content = read(document.getBodyElements(), true);
        return this;
    }

    public String read(List<IBodyElement> bodyElements, boolean newline) {
        StringBuilder builder = new StringBuilder();
        for (IBodyElement bodyElement : bodyElements) {
            BodyElementHandler handler = getHandler(bodyElement);
            if (handler == null) {
                continue;
            }
            builder.append(handler.handle(this, bodyElement));
            if (newline) {
                builder.append("\n");
            }
        }
        return builder.toString();
    }

    public String getContent() {
        return content;
    }

    private BodyElementHandler getHandler(IBodyElement element) {
        for (BodyElementHandler handler : handlers) {
            if (handler.support(this, element)) {
                return handler;
            }
        }
        return null;
    }
}
