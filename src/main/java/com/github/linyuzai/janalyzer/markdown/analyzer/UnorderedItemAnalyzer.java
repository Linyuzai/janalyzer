package com.github.linyuzai.janalyzer.markdown.analyzer;

import com.github.linyuzai.janalyzer.markdown.context.MarkdownContext;
import com.github.linyuzai.janalyzer.markdown.element.UnorderedItemElement;

public class UnorderedItemAnalyzer extends MarkdownAnalyzer {

    private static final UnorderedItemAnalyzer instance = new UnorderedItemAnalyzer();

    public static UnorderedItemAnalyzer getInstance() {
        return instance;
    }

    UnorderedItemAnalyzer() {
    }

    @Override
    public void registerSelfAnalyzers() {
        registerAnalyzer(CombinationAnalyzer.getInstance());
    }

    @Override
    public UnorderedItemElement analyze(MarkdownContext context) {
        String source = context.getSource();
        boolean loop = context.isLoop();
        String s = trimStart(source, ' ');
        int blankLength = source.length() - s.length();
        if (s.startsWith("- ") || s.startsWith("+ ") || s.startsWith("* ")) {
            int beginIndex = 0;
            int endIndex = getEndIndex(s, "\n");
            String content = s.substring(beginIndex, endIndex);
            String inner = content.substring(2);
            UnorderedItemElement unorderedItemElement = (UnorderedItemElement) super.analyze(newContext(inner, loop));
            unorderedItemElement.setContent(content);
            unorderedItemElement.setBeginIndex(beginIndex);
            unorderedItemElement.setEndIndex(endIndex + blankLength);
            unorderedItemElement.setListLevel(blankLength);
            return unorderedItemElement;
        }
        return null;
    }

    @Override
    public UnorderedItemElement newElement() {
        return new UnorderedItemElement();
    }
}
