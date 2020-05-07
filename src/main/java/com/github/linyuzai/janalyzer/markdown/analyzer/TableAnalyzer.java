package com.github.linyuzai.janalyzer.markdown.analyzer;

import com.github.linyuzai.janalyzer.markdown.context.MarkdownContext;
import com.github.linyuzai.janalyzer.markdown.element.TableElement;

public class TableAnalyzer extends MarkdownAnalyzer {

    private static final TableAnalyzer instance = new TableAnalyzer();

    public static TableAnalyzer getInstance() {
        return instance;
    }

    TableAnalyzer() {
    }

    @Override
    public void registerSelfAnalyzers() {
        registerAnalyzer(TableRowAnalyzer.getInstance());
    }

    @Override
    public TableElement analyze(MarkdownContext context) {
        String source = context.getSource();
        boolean loop = context.isLoop();
        String s = source;
        int line = 0;
        int nextEndIndex = getNextItemEndIndex(s, line);
        int endIndex = nextEndIndex;
        while (nextEndIndex != -1) {
            line++;
            s = s.substring(nextEndIndex);
            nextEndIndex = getNextItemEndIndex(s, line);
            if (line == 1 && nextEndIndex == -1) {
                return null;
            }
            if (nextEndIndex != -1) {
                endIndex += nextEndIndex;
            }
        }
        if (endIndex == -1) {
            return null;
        }
        int beginIndex = 0;
        String content = source.substring(beginIndex, endIndex);
        TableElement tableElement = (TableElement) super.analyze(newContext(content, loop));
        tableElement.setContent(content);
        tableElement.setBeginIndex(beginIndex);
        tableElement.setEndIndex(endIndex);
        return tableElement;
    }

    private int getNextItemEndIndex(String source, int line) {
        String s = trimStart(source, ' ');
        int blankLength = source.length() - s.length();
        int endIndex = getEndIndex(s, "\n");
        String content = trimEnd(s.substring(0, endIndex), '\n');
        if (content.contains("|")) {
            if (content.startsWith("|") && content.endsWith("|")) {
                if (content.length() < 2) {
                    return -1;
                }
                if (line == 1) {
                    content = content.substring(1, content.length() - 1);
                    String[] arr = content.split("\\|", -1);
                    for (String str : arr) {
                        if (!trimBoth(str, ':', '-', ' ').isEmpty()) {
                            return -1;
                        }
                    }
                }
            }
            return endIndex + blankLength;
        }
        return -1;
    }

    @Override
    public TableElement newElement() {
        return new TableElement();
    }
}
