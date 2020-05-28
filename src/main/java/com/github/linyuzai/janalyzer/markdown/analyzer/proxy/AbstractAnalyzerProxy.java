package com.github.linyuzai.janalyzer.markdown.analyzer.proxy;

import com.github.linyuzai.janalyzer.markdown.analyzer.MarkdownAnalyzer;
import com.github.linyuzai.janalyzer.markdown.context.MarkdownContext;
import com.github.linyuzai.janalyzer.markdown.element.MarkdownElement;

import java.util.Collection;

public class AbstractAnalyzerProxy<A extends MarkdownAnalyzer> extends MarkdownAnalyzer implements AnalyzerProxy<A> {

    private A analyzer;

    public AbstractAnalyzerProxy(A analyzer) {
        this.analyzer = analyzer;
    }

    @Override
    public A getAnalyzer() {
        return this.analyzer;
    }

    @Override
    public void setAnalyzer(A analyzer) {
        this.analyzer = analyzer;
    }

    @Override
    public void registerAnalyzers() {
        this.analyzer.registerAnalyzers();
    }

    @Override
    public void registerSelfAnalyzers() {
        this.analyzer.registerSelfAnalyzers();
    }

    @Override
    public void registerChildrenAnalyzers() {
        this.analyzer.registerChildrenAnalyzers();
    }

    @Override
    public MarkdownElement analyze(MarkdownContext context) {
        return this.analyzer.analyze(context);
    }

    @Override
    public MarkdownElement newElement() {
        return this.analyzer.newElement();
    }

    @Override
    public MarkdownContext newContext(String source, boolean loop) {
        return this.analyzer.newContext(source, loop);
    }

    @Override
    public Collection<MarkdownAnalyzer> getChildren() {
        return this.analyzer.getChildren();
    }

    @Override
    public void setChildren(Collection<MarkdownAnalyzer> children) {
        this.analyzer.setChildren(children);
    }

    @Override
    public Collection<MarkdownAnalyzer> newCollection() {
        return this.analyzer.newCollection();
    }

    @Override
    public void sort(Collection<MarkdownAnalyzer> collection) {
        this.analyzer.sort(collection);
    }

    @Override
    public void registerAnalyzer(MarkdownAnalyzer analyzer) {
        this.analyzer.registerAnalyzer(analyzer);
    }

    @Override
    public void unregisterAnalyzer(MarkdownAnalyzer analyzer) {
        this.analyzer.unregisterAnalyzer(analyzer);
    }

    @Override
    public void clearAnalyzers() {
        this.analyzer.clearAnalyzers();
    }

    @Override
    public String[] processSource(String source, MarkdownElement element) {
        return this.analyzer.processSource(source, element);
    }

    @Override
    public int getEndIndex(String source, String end) {
        return this.analyzer.getEndIndex(source, end);
    }

    @Override
    public int getEndIndex(String source, String end, int fromIndex) {
        return this.analyzer.getEndIndex(source, end, fromIndex);
    }

    @Override
    public boolean containsChar(char target, char... arr) {
        return this.analyzer.containsChar(target, arr);
    }

    @Override
    public String trimStart(String source, char... c) {
        return this.analyzer.trimStart(source, c);
    }

    @Override
    public String trimEnd(String source, char... c) {
        return this.analyzer.trimEnd(source, c);
    }

    @Override
    public String trimBoth(String source, char... c) {
        return this.analyzer.trimBoth(source, c);
    }

    @Override
    public int order() {
        return this.analyzer.order();
    }

    @Override
    public int hashCode() {
        return this.analyzer.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return this.analyzer.equals(obj);
    }

    @Override
    public String toString() {
        return this.analyzer.toString();
    }
}
