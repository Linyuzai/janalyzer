package com.github.linyuzai.janalyzer.markdown.word.handler;

import com.github.linyuzai.janalyzer.markdown.word.DocxReader;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class DocxParagraphMarkdownHandler implements BodyElementHandler {

    @Override
    public boolean support(DocxReader reader,IBodyElement element) {
        return element instanceof XWPFParagraph;
    }

    @Override
    public String handle(DocxReader reader,IBodyElement element) {
        return handleParagraph((XWPFParagraph) element);
    }

    public String handleParagraph(XWPFParagraph paragraph) {
        if (paragraph == null) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (XWPFRun run : paragraph.getRuns()) {
            builder.append(handleRun(run));
        }
        return builder.toString();
    }

    public String handleRun(XWPFRun run) {
        boolean italic = run.isItalic();
        boolean bold = run.isBold();
        String text = run.text();
        if (italic && bold) {
            return "***" + text + "***";
        } else if (italic) {
            return "*" + text + "*";
        } else if (bold) {
            return "**" + text + "**";
        } else {
            return text;
        }
    }
}
