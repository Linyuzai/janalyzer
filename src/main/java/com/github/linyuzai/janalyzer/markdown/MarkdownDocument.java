package com.github.linyuzai.janalyzer.markdown;

import com.github.linyuzai.janalyzer.markdown.element.MarkdownElement;
import com.github.linyuzai.janalyzer.markdown.html.HtmlRenderer;
import com.github.linyuzai.janalyzer.markdown.word.DocxWriter;

import java.io.IOException;

public class MarkdownDocument {

    private MarkdownElement markdownElement;

    public MarkdownDocument(MarkdownElement markdownElement) {
        this.markdownElement = markdownElement;
    }

    public MarkdownElement getMarkdownElement() {
        return markdownElement;
    }

    public HtmlRenderer html() {
        return new HtmlRenderer(markdownElement);
    }

    public HtmlRenderer html(HtmlRenderer renderer) {
        renderer.setMarkdownElement(markdownElement);
        return renderer;
    }

    public DocxWriter docx() throws IOException {
        return new DocxWriter(markdownElement);
    }
}
