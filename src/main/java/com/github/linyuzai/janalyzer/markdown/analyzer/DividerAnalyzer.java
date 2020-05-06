package com.github.linyuzai.janalyzer.markdown.analyzer;

import com.github.linyuzai.janalyzer.markdown.context.MarkdownContext;
import com.github.linyuzai.janalyzer.markdown.element.DividerElement;

public class DividerAnalyzer extends MarkdownAnalyzer {

    private static final DividerAnalyzer instance = new DividerAnalyzer();

    public static DividerAnalyzer getInstance() {
        return instance;
    }

    DividerAnalyzer() {
    }

    @Override
    public void registerSelfAnalyzers() {
    }

    @Override
    public DividerElement analyze(MarkdownContext context) {
        String source = context.getSource();
        boolean loop = context.isLoop();
        String s = trimStart(source, ' ');
        int blankLength = source.length() - s.length();
        if (s.startsWith("-") || s.startsWith("*")) {
            int beginIndex = 0;
            int endIndex = getEndIndex(s, "\n");
            String content = s.substring(beginIndex, endIndex);
            if (content.length() >= 3) {
                String inner = trimStart(content, '-', '*', '\n');
                if (inner.isEmpty()) {
                    DividerElement dividerElement = (DividerElement) super.analyze(newContext(content, loop));
                    dividerElement.setContent(content);
                    dividerElement.setBeginIndex(beginIndex);
                    dividerElement.setEndIndex(endIndex + blankLength);
                    return dividerElement;
                }
            }
        }
        return null;
    }

    @Override
    public DividerElement newElement() {
        return new DividerElement();
    }
}
