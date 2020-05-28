package com.github.linyuzai.janalyzer.markdown.analyzer.proxy;

import com.github.linyuzai.janalyzer.markdown.analyzer.MarkdownAnalyzer;

public interface AnalyzerProxy<A extends MarkdownAnalyzer> {

    A getAnalyzer();

    void setAnalyzer(A analyzer);
}
