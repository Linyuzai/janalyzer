package com.github.linyuzai.janalyzer.markdown.analyzer;

import com.github.linyuzai.janalyzer.markdown.context.MarkdownContext;
import com.github.linyuzai.janalyzer.markdown.element.UnorderedListElement;

public class UnorderedListAnalyzer extends MarkdownAnalyzer {

    private static final UnorderedListAnalyzer instance = new UnorderedListAnalyzer();

    public static UnorderedListAnalyzer getInstance() {
        return instance;
    }

    UnorderedListAnalyzer() {
    }

    @Override
    public void registerSelfAnalyzers() {
        registerAnalyzer(UnorderedItemAnalyzer.getInstance());
    }

    @Override
    public UnorderedListElement analyze(MarkdownContext context) {
        String source = context.getSource();
        boolean loop = context.isLoop();
        String s = source;
        int nextEndIndex = getNextItemEndIndex(s);
        int endIndex = nextEndIndex;
        while (nextEndIndex != -1) {
            s = s.substring(nextEndIndex);
            nextEndIndex = getNextItemEndIndex(s);
            if (nextEndIndex != -1) {
                endIndex += nextEndIndex;
            }
        }
        if (endIndex == -1) {
            return null;
        }
        int beginIndex = 0;
        String content = source.substring(beginIndex, endIndex);
        UnorderedListElement unorderedListElement = (UnorderedListElement) super.analyze(newContext(content, loop));
        unorderedListElement.setContent(content);
        unorderedListElement.setBeginIndex(beginIndex);
        unorderedListElement.setEndIndex(endIndex);
        return unorderedListElement;
    }

    private int getNextItemEndIndex(String source) {
        String s = trimStart(source, ' ');
        int blankLength = source.length() - s.length();
        if (s.startsWith("- ") || s.startsWith("+ ") || s.startsWith("* ")) {
            int endIndex = getEndIndex(s, "\n");
            return endIndex + blankLength;
        }
        return -1;
    }

    @Override
    public UnorderedListElement newElement() {
        return new UnorderedListElement();
    }
}
