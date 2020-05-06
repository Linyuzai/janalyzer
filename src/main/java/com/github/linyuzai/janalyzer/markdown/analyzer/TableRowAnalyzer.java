package com.github.linyuzai.janalyzer.markdown.analyzer;

import com.github.linyuzai.janalyzer.markdown.context.MarkdownContext;
import com.github.linyuzai.janalyzer.markdown.element.TableRowElement;

public class TableRowAnalyzer extends MarkdownAnalyzer {

    private static final TableRowAnalyzer instance = new TableRowAnalyzer();

    public static TableRowAnalyzer getInstance() {
        return instance;
    }

    TableRowAnalyzer() {
    }

    @Override
    public void registerSelfAnalyzers() {
        registerAnalyzer(TableCellAnalyzer.getInstance());
    }

    @Override
    public TableRowElement analyze(MarkdownContext context) {
        String source = context.getSource();
        boolean loop = context.isLoop();
        String s = source;
        int beginIndex = 0;
        int endIndex = getEndIndex(s, "\n");
        String content = source.substring(beginIndex, endIndex);
        TableRowElement tableRowElement = (TableRowElement) super.analyze(newContext(content, loop));
        tableRowElement.setContent(content);
        tableRowElement.setBeginIndex(beginIndex);
        tableRowElement.setEndIndex(endIndex);
        tableRowElement.setFormat(trimBoth(content, '|', '-', ':', '\n').isEmpty());
        return tableRowElement;
    }

    @Override
    public TableRowElement newElement() {
        return new TableRowElement();
    }
}
