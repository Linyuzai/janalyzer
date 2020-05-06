package com.github.linyuzai.janalyzer.markdown.analyzer;

import com.github.linyuzai.janalyzer.markdown.context.MarkdownContext;
import com.github.linyuzai.janalyzer.markdown.element.TableCellElement;

public class TableCellAnalyzer extends MarkdownAnalyzer {

    private static final TableCellAnalyzer instance = new TableCellAnalyzer();

    public static TableCellAnalyzer getInstance() {
        return instance;
    }

    TableCellAnalyzer() {
    }

    @Override
    public void registerSelfAnalyzers() {
        registerAnalyzer(CombinationAnalyzer.getInstance());
    }

    @Override
    public TableCellElement analyze(MarkdownContext context) {
        String source = context.getSource();
        boolean loop = context.isLoop();
        String s = trimBoth(source, ' ', '\n');
        int beginIndex = 0;
        int endIndex;
        String content = null;
        if (s.isEmpty() || "|".equals(s)) {
            endIndex = source.length();
        } else {
            if (s.startsWith("|") && s.endsWith("|")) {
                endIndex = s.indexOf("|", 1);
            } else {
                endIndex = getEndIndex(s, "|");
            }
            content = trimBoth(s.substring(beginIndex, endIndex), '|');
        }
        TableCellElement tableCellElement = (TableCellElement) super.analyze(newContext(content, loop));
        tableCellElement.setContent(content);
        tableCellElement.setBeginIndex(beginIndex);
        tableCellElement.setEndIndex(endIndex);
        return tableCellElement;
    }

    @Override
    public TableCellElement newElement() {
        return new TableCellElement();
    }
}
