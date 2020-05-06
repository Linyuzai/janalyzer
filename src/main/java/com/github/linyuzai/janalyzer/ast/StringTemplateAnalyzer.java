package com.github.linyuzai.janalyzer.ast;

import java.util.*;

public abstract class StringTemplateAnalyzer<C extends StringTemplateContext, A extends StringTemplateAnalyzer<C, A, E>, E extends StringTemplateElement<E>> implements TemplateAnalyzer<C, E> {

    private Collection<A> children;

    public Collection<A> getChildren() {
        return children;
    }

    public void setChildren(Collection<A> children) {
        this.children = children;
    }

    public Collection<A> newCollection() {
        return new ArrayList<>();
    }

    @SuppressWarnings("unchecked")
    public void sort(Collection<A> collection) {
        ((List<A>) collection).sort(Comparator.comparingInt(StringTemplateAnalyzer::order));
    }

    @SuppressWarnings("unchecked")
    public void registerAnalyzer(A analyzer) {
        if (children == null) {
            children = newCollection();
        }
        children.add(analyzer);
        sort(children);
    }

    public void unregisterAnalyzer(A analyzer) {
        if (children == null) {
            return;
        }
        children.remove(analyzer);
    }

    public void clearAnalyzers() {
        if (children == null) {
            return;
        }
        children.clear();
    }

    @SuppressWarnings("unchecked")
    @Override
    public E analyze(C context) {
        E element = newElement();
        if (!context.isLoop()) {
            return element;
        }
        String source = context.getSource();
        if (source == null || source.isEmpty()) {
            return element;
        }
        Collection<A> analyzers = getChildren();
        if (analyzers == null) {
            return element;
        }
        analyze0(context, analyzers, element);
        return element;
    }

    private void analyze0(C context, Collection<A> analyzers, E element) {
        String source = context.getSource();
        String s = source;
        boolean loop = context.isLoop();
        while (!s.isEmpty()) {
            for (A analyzer : analyzers) {
                E me = analyzer.analyze(newContext(s, loop));
                if (me == null) {
                    continue;
                }
                String[] sa = processSource(s, me);
                analyze0(newContext(sa[0], loop), analyzers, element);
                s = sa[1];
                if (me.getContent() != null) {
                    element.addChildren(me);
                }
                break;
            }
            if (s.length() == source.length()) {
                return;
            }
        }
    }

    public abstract E newElement();

    public abstract C newContext(String source, boolean loop);

    public String[] processSource(String source, E element) {
        String s1 = "";
        int beginIndex = element.getBeginIndex();
        if (beginIndex != 0) {
            s1 = source.substring(0, beginIndex);
        }
        return new String[]{s1, source.substring(element.getEndIndex())};
    }

    public int getEndIndex(String source, String end) {
        return getEndIndex(source, end, 0);
    }

    public int getEndIndex(String source, String end, int fromIndex) {
        int endIndex = source.indexOf(end, fromIndex);
        if (endIndex == -1) {
            endIndex = source.length();
        } else {
            if (end.length() > 0) {
                endIndex += end.length();
            }
        }
        return endIndex;
    }

    public boolean containsChar(char target, char... arr) {
        for (char c : arr) {
            if (c == target) {
                return true;
            }
        }
        return false;
    }

    public String trimStart(String source, char... c) {
        String s = source;
        int len = s.length();
        int st = 0;
        char[] val = s.toCharArray();
        while ((st < len) && (containsChar(val[st], c))) {
            st++;
        }
        return (st > 0) ? s.substring(st, len) : s;
    }

    public String trimEnd(String source, char... c) {
        String s = source;
        int len = s.length();
        int st = 0;
        char[] val = s.toCharArray();
        while ((st < len) && (containsChar(val[len - 1], c))) {
            len--;
        }
        return (len < s.length()) ? s.substring(st, len) : s;
    }

    public String trimBoth(String source, char... c) {
        String s = source;
        int len = s.length();
        int st = 0;
        char[] val = s.toCharArray();
        while ((st < len) && (containsChar(val[st], c))) {
            st++;
        }
        while ((st < len) && (containsChar(val[len - 1], c))) {
            len--;
        }
        return ((st > 0) || (len < s.length())) ? s.substring(st, len) : s;
    }

    @Override
    public int order() {
        return 0;
    }
}
