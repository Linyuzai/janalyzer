package com.github.linyuzai.janalyzer.markdown.analyzer;

import com.github.linyuzai.janalyzer.markdown.context.MarkdownContext;
import com.github.linyuzai.janalyzer.markdown.element.OrderedListElement;

public class OrderedListAnalyzer extends MarkdownAnalyzer {

    private static final OrderedListAnalyzer instance = new OrderedListAnalyzer();

    public static OrderedListAnalyzer getInstance() {
        return instance;
    }

    OrderedListAnalyzer() {
    }

    @Override
    public void registerSelfAnalyzers() {
        registerAnalyzer(OrderedItemAnalyzer.getInstance());
    }

    @Override
    public OrderedListElement analyze(MarkdownContext context) {
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
        OrderedListElement orderedListElement = (OrderedListElement) super.analyze(newContext(content, loop));
        orderedListElement.setContent(content);
        orderedListElement.setBeginIndex(beginIndex);
        orderedListElement.setEndIndex(endIndex);
        return orderedListElement;
    }

    private int getNextItemEndIndex(String source) {
        String s = trimStart(source, ' ');
        int blankLength = source.length() - s.length();
        int orderedListIndex = s.indexOf(". ");
        if (orderedListIndex != -1) {
            String numStr = s.substring(0, orderedListIndex);
            try {
                Double.parseDouble(numStr);
            } catch (Throwable e) {
                return -1;
            }
            int endIndex = getEndIndex(s, "\n");
            return endIndex + blankLength;
        }
        return -1;
    }

    @Override
    public OrderedListElement newElement() {
        return new OrderedListElement();
    }
}
