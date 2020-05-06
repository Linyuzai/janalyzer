package com.github.linyuzai.janalyzer.markdown.analyzer;

import com.github.linyuzai.janalyzer.markdown.context.MarkdownContext;
import com.github.linyuzai.janalyzer.markdown.element.TextElement;

public class TextAnalyzer extends MarkdownAnalyzer {

    private static final TextAnalyzer instance = new TextAnalyzer();

    public static TextAnalyzer getInstance() {
        return instance;
    }

    TextAnalyzer() {
    }

    @Override
    public void registerSelfAnalyzers() {

    }

    @Override
    public TextElement analyze(MarkdownContext context) {
        String source = context.getSource();
        boolean loop = context.isLoop();
        int beginIndex = 0;
        int endIndex = getEndIndex(source, "\n");
        String content = source.substring(beginIndex, endIndex);
        TextElement textElement = (TextElement) super.analyze(newContext(content, loop));
        textElement.setContent(content);
        textElement.setBeginIndex(beginIndex);
        textElement.setEndIndex(endIndex);
        textElement.setText(trimBoth(content, '\n', ' '));
        return textElement;
    }

    @Override
    public TextElement newElement() {
        return new TextElement();
    }
}
