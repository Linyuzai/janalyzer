package com.github.linyuzai.janalyzer.markdown.word.handler;

import com.github.linyuzai.janalyzer.markdown.word.DocxReader;
import org.apache.poi.xwpf.usermodel.*;

import java.util.List;

public class DocxParagraphMarkdownHandler implements ElementHandler {

    @Override
    public boolean support(DocxReader reader, Object element) {
        return element instanceof XWPFParagraph;
    }

    @Override
    public String handle(DocxReader reader, Object element) {
        return handleParagraph(reader, (XWPFParagraph) element);
    }

    public String handleParagraph(DocxReader reader, XWPFParagraph paragraph) {
        if (paragraph == null) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (XWPFRun run : paragraph.getRuns()) {
            builder.append(handleRun(reader, run));
        }
        return builder.toString();
    }

    public String handleRun(DocxReader reader, XWPFRun run) {
        if (run == null) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        boolean italic = run.isItalic();
        boolean bold = run.isBold();
        String text = run.text();
        if (text != null && !text.isEmpty()) {
            String trim = text.trim();
            if (italic && bold) {
                builder.append("***").append(trim).append("***");
            } else if (italic) {
                builder.append("*").append(trim).append("*");
            } else if (bold) {
                builder.append("**").append(trim).append("**");
            } else {
                builder.append(trim);
            }
        }
        List<XWPFPicture> pictures = run.getEmbeddedPictures();
        if (!pictures.isEmpty()) {
            builder.append(reader.read(pictures, false));
        }
        return builder.toString();
    }
}
