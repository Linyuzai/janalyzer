package com.github.linyuzai.janalyzer.markdown.analyzer.holder;

import com.github.linyuzai.janalyzer.markdown.analyzer.MarkdownAnalyzer;

public interface AnalyzerHolder<A extends MarkdownAnalyzer> {

    A getAnalyzer();

    void setAnalyzer(A analyzer);
}
