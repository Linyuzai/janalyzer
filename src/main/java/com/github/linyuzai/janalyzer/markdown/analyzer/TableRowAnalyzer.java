package com.github.linyuzai.janalyzer.markdown.analyzer;

import com.github.linyuzai.janalyzer.markdown.analyzer.holder.AbstractAnalyzerHolder;
import com.github.linyuzai.janalyzer.markdown.context.MarkdownContext;
import com.github.linyuzai.janalyzer.markdown.element.TableRowElement;

public class TableRowAnalyzer extends MarkdownAnalyzer {

    public static class Holder extends AbstractAnalyzerHolder<TableRowAnalyzer> {

        private static final TableRowAnalyzer.Holder instance = new TableRowAnalyzer.Holder();

        public static TableRowAnalyzer.Holder getInstance() {
            return instance;
        }

        private Holder() {
        }

        @Override
        public TableRowAnalyzer newAnalyzer() {
            return new TableRowAnalyzer();
        }
    }

    @Override
    public void registerSelfAnalyzers() {
        registerAnalyzer(TableCellAnalyzer.Holder.getInstance());
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
