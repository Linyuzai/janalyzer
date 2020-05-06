package com.github.linyuzai.janalyzer.markdown.analyzer;

import com.github.linyuzai.janalyzer.markdown.context.MarkdownContext;
import com.github.linyuzai.janalyzer.markdown.element.BlankLineElement;

public class BlankLineAnalyzer extends MarkdownAnalyzer {

    private static final BlankLineAnalyzer instance = new BlankLineAnalyzer();

    public static BlankLineAnalyzer getInstance() {
        return instance;
    }

    BlankLineAnalyzer() {
    }

    @Override
    public void registerSelfAnalyzers() {

    }

    @Override
    public BlankLineElement analyze(MarkdownContext context) {
        String source = context.getSource();
        boolean loop = context.isLoop();
        int beginIndex = 0;
        int endIndex = getEndIndex(source, "\n");
        String content = source.substring(beginIndex, endIndex);
        String s = content.trim();
        if (s.isEmpty()) {
            BlankLineElement blankLineElement = (BlankLineElement) super.analyze(newContext(content, loop));
            blankLineElement.setContent(content);
            blankLineElement.setBeginIndex(beginIndex);
            blankLineElement.setEndIndex(endIndex);
            return blankLineElement;
        }
        return null;
    }

    @Override
    public BlankLineElement newElement() {
        return new BlankLineElement();
    }

}
