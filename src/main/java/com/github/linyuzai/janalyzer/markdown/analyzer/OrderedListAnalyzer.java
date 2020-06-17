package com.github.linyuzai.janalyzer.markdown.analyzer;

import com.github.linyuzai.janalyzer.markdown.analyzer.holder.AbstractAnalyzerHolder;
import com.github.linyuzai.janalyzer.markdown.context.MarkdownContext;
import com.github.linyuzai.janalyzer.markdown.element.OrderedListElement;

public class OrderedListAnalyzer extends MarkdownAnalyzer {

    public static class Holder extends AbstractAnalyzerHolder<OrderedListAnalyzer> {

        private static final OrderedListAnalyzer.Holder instance = new OrderedListAnalyzer.Holder();

        public static OrderedListAnalyzer.Holder getInstance() {
            return instance;
        }

        private Holder() {
        }

        @Override
        public OrderedListAnalyzer newAnalyzer() {
            return new OrderedListAnalyzer();
        }
    }

    @Override
    public void registerSelfAnalyzers() {
        registerAnalyzer(OrderedItemAnalyzer.Holder.getInstance());
    }

    @Override
    public OrderedListElement analyze(MarkdownContext context) {
        String source = context.getSource();
        boolean loop = context.isLoop();
        String s = source;
        int nextEndIndex = getNextOrderedItemEndIndex(s);
        int endIndex = nextEndIndex;
        while (nextEndIndex != -1) {
            s = s.substring(nextEndIndex);
            nextEndIndex = getNextOrderedItemEndIndex(s);
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
        baseElement(orderedListElement, beginIndex, endIndex, content);
        return orderedListElement;
    }

    private int getNextOrderedItemEndIndex(String source) {
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
