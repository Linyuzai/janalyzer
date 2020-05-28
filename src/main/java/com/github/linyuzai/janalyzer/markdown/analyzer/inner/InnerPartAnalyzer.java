package com.github.linyuzai.janalyzer.markdown.analyzer.inner;

import com.github.linyuzai.janalyzer.markdown.analyzer.MarkdownAnalyzer;
import com.github.linyuzai.janalyzer.markdown.context.MarkdownContext;
import com.github.linyuzai.janalyzer.markdown.element.MarkdownElement;

import java.util.Collection;

public abstract class InnerPartAnalyzer extends MarkdownAnalyzer {

    public boolean notMatch(MarkdownContext context, String source, int index) {
        int firstBeginIndex = getFirstBeginIndex(context.getParent(), source);
        return firstBeginIndex != -1 && firstBeginIndex < index;
    }

    public int getFirstBeginIndex(MarkdownAnalyzer parent, String source) {
        int beginIndex = -1;
        if (parent == null) {
            return -1;
        }
        Collection<MarkdownAnalyzer> analyzers = parent.getChildren();
        if (analyzers == null) {
            return -1;
        }
        for (MarkdownAnalyzer analyzer : analyzers) {
            if (analyzer instanceof InnerPartAnalyzer && analyzer.getClass() != getClass()) {
                MarkdownElement me = analyzer.analyze(newContext(source, false));
                if (me == null) {
                    continue;
                }
                beginIndex = getMinBeginIndex(beginIndex, me.getBeginIndex());
            }
        }
        return beginIndex;
    }

    public int getMinBeginIndex(int current, int index) {
        if (index == -1) {
            return current;
        }
        if (current == -1) {
            return index;
        }
        return (index < current) ? index : current;
    }

    @Deprecated
    public int getBeginIndex(String source, String... maskArr) {
        int beginIndex = -1;
        int index = -1;
        for (int i = 0; i < maskArr.length; i++) {
            String mask = maskArr[i];
            if (i == 0) {
                beginIndex = source.indexOf(mask);
                index = beginIndex;
            } else {
                index = source.indexOf(mask, index + maskArr[i - 1].length());
            }
            if (index == -1) {
                return -1;
            }
        }
        return beginIndex;
    }
}
