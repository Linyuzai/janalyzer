package com.github.linyuzai.janalyzer.markdown.analyzer;

import com.github.linyuzai.janalyzer.markdown.analyzer.proxy.AbstractAnalyzerProxy;
import com.github.linyuzai.janalyzer.markdown.context.MarkdownContext;
import com.github.linyuzai.janalyzer.markdown.element.TableRowElement;

public class TableRowAnalyzer extends MarkdownAnalyzer {

    public static class Proxy extends AbstractAnalyzerProxy<TableRowAnalyzer> {

        private static final Proxy instance = new Proxy(new TableRowAnalyzer());

        public static Proxy getInstance() {
            return instance;
        }

        public Proxy(TableRowAnalyzer analyzer) {
            super(analyzer);
        }
    }

    @Override
    public void registerSelfAnalyzers() {
        registerAnalyzer(TableCellAnalyzer.Proxy.getInstance());
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
        baseElement(tableRowElement, beginIndex, endIndex, content);
        tableRowElement.setFormat(trimBoth(content, '|', '-', ':', ' ', '\n').isEmpty());
        return tableRowElement;
    }

    @Override
    public TableRowElement newElement() {
        return new TableRowElement();
    }
}
