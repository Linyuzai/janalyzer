package com.github.linyuzai.janalyzer.markdown.analyzer;

import com.github.linyuzai.janalyzer.markdown.context.MarkdownContext;
import com.github.linyuzai.janalyzer.markdown.element.OrderedItemElement;

public class OrderedItemAnalyzer extends MarkdownAnalyzer {

    private static final OrderedItemAnalyzer instance = new OrderedItemAnalyzer();

    public static OrderedItemAnalyzer getInstance() {
        return instance;
    }

    OrderedItemAnalyzer() {
    }

    @Override
    public void registerSelfAnalyzers() {
        registerAnalyzer(CombinationAnalyzer.getInstance());
    }

    @Override
    public OrderedItemElement analyze(MarkdownContext context) {
        String source = context.getSource();
        boolean loop = context.isLoop();
        String s = trimStart(source, ' ');
        int blankLength = source.length() - s.length();
        int orderedListIndex = s.indexOf(". ");
        if (orderedListIndex != -1) {
            String numStr = s.substring(0, orderedListIndex);
            try {
                Double.parseDouble(numStr);
            } catch (Throwable e) {
                return null;
            }
            int beginIndex = 0;
            int endIndex = getEndIndex(s, "\n");
            String content = s.substring(beginIndex, endIndex);
            String inner = content.substring(orderedListIndex + 2);
            OrderedItemElement orderedItemElement = (OrderedItemElement) super.analyze(newContext(inner, loop));
            orderedItemElement.setContent(content);
            orderedItemElement.setBeginIndex(beginIndex);
            orderedItemElement.setEndIndex(endIndex + blankLength);
            orderedItemElement.setListLevel(blankLength);
            orderedItemElement.setSerialNumber(numStr);
            return orderedItemElement;
        }
        return null;
    }

    @Override
    public OrderedItemElement newElement() {
        return new OrderedItemElement();
    }
}
