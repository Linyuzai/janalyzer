package com.github.linyuzai.janalyzer.markdown.analyzer;

import com.github.linyuzai.janalyzer.markdown.context.MarkdownContext;
import com.github.linyuzai.janalyzer.markdown.element.TextareaElement;

public class TextareaAnalyzer extends MarkdownAnalyzer {

    private static final TextareaAnalyzer instance = new TextareaAnalyzer();

    public static TextareaAnalyzer getInstance() {
        return instance;
    }

    TextareaAnalyzer() {
    }

    @Override
    public void registerSelfAnalyzers() {

    }

    @Override
    public TextareaElement analyze(MarkdownContext context) {
        String source = context.getSource();
        boolean loop = context.isLoop();
        int beginIndex = 0;
        int endIndex = source.length();
        TextareaElement textareaElement = (TextareaElement) super.analyze(newContext(source, loop));
        textareaElement.setContent(source);
        textareaElement.setBeginIndex(beginIndex);
        textareaElement.setEndIndex(endIndex);
        textareaElement.setText(source);
        return textareaElement;
    }

    @Override
    public TextareaElement newElement() {
        return new TextareaElement();
    }
}
