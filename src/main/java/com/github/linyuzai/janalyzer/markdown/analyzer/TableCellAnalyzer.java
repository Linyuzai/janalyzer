package com.github.linyuzai.janalyzer.markdown.analyzer;

import com.github.linyuzai.janalyzer.markdown.analyzer.proxy.AbstractAnalyzerProxy;
import com.github.linyuzai.janalyzer.markdown.context.MarkdownContext;
import com.github.linyuzai.janalyzer.markdown.element.TableCellElement;

public class TableCellAnalyzer extends MarkdownAnalyzer {

    public static class Proxy extends AbstractAnalyzerProxy<TableCellAnalyzer> {

        private static final Proxy instance = new Proxy(new TableCellAnalyzer());

        public static Proxy getInstance() {
            return instance;
        }

        public Proxy(TableCellAnalyzer analyzer) {
            super(analyzer);
        }
    }

    @Override
    public void registerSelfAnalyzers() {
        registerAnalyzer(CombinationAnalyzer.Proxy.getInstance());
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
        baseElement(tableCellElement, beginIndex, endIndex, content);
        return tableCellElement;
    }

    @Override
    public TableCellElement newElement() {
        return new TableCellElement();
    }
}
